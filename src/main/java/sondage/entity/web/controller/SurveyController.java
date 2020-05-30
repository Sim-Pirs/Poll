package sondage.entity.web.controller;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import sondage.entity.model.*;
import sondage.entity.web.ISurveyManager;
import sondage.entity.web.validator.SurveyValidator;

import javax.validation.Valid;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;


@Controller()
@RequestMapping("/sondage")
public class SurveyController {

    @Autowired
    ISurveyManager manager;

    @Autowired
    User user;

    @Autowired
    SurveyValidator surveyValidator;


    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

    /**
     * Méthode réalisant le mapping de l'adresse "/sondage/repondre". A cette
     * adresse on peux répondre à un sondage.
     * @param token Token représentant le sondé.
     * @param error Indique si la réponse au sondage possède une erreur.
     * @return Renvoi la page de réponse à un sondage.
     */
    @RequestMapping(value = "/repondre", method = RequestMethod.GET)
    public ModelAndView showSurvey(@RequestParam(value = "token") String token,
                                   @RequestParam(value = "error", required = false) boolean error){
        Respondent respondent = manager.findRespondentByToken(token);
        if(respondent == null) return new ModelAndView("redirect:/error");

        if(respondent.isExpired()) {
            ModelAndView mv =  new ModelAndView("send_access");
            mv.addObject("respondent_id", respondent.getId());
            return mv;
        }
        if(respondent.getSurvey().getEndDate().compareTo(new Date()) <= 0) return new ModelAndView("redirect:/error");

        Survey tmpSurvey = new Survey();
        tmpSurvey.setId(respondent.getSurvey().getId());
        tmpSurvey.setName(respondent.getSurvey().getName());
        tmpSurvey.setDescription(respondent.getSurvey().getDescription());
        tmpSurvey.setEndDate(respondent.getSurvey().getEndDate());

        List<Integer> scores = new ArrayList<>();
        for(SurveyItem item : respondent.getSurvey().getItems()){
            for(String tag : respondent.getTags()) {
                if (!item.getTags().contains(tag)) continue;
                SurveyItem tmpItem = new SurveyItem();
                tmpItem.setId(item.getId());
                tmpItem.setDescription(item.getDescription());
                tmpSurvey.addItem(tmpItem);

                Choice c = manager.findChoiceByRespondentIdAndItemId(respondent.getId(), tmpItem.getId());
                if(c == null){
                    scores.add(0);
                } else {
                    scores.add(c.getScore());
                }
            }
        }

        ModelAndView mv = new ModelAndView("show_survey");
        mv.addObject("respondent", respondent);
        mv.addObject("survey", tmpSurvey);
        mv.addObject("scores", scores);
        mv.addObject("error", error);

        return mv;
    }

    /**
     * Méthode qui vérifie et créer et stock les différents choix d'un
     * sondé à un sondage.
     * @param idRespondentString Identifiant du sondé sous forme de string.
     * @param idItemsArray Tableau des différentes options du sondage auquel le sondé a attribué un score.
     * @param scores Tableau des scores associés aux options.
     * @return Si aucunes erreur, renvoi sur une page indiquant que les choix on bien été sauvegardés.
     */
    @RequestMapping(value = "/repondre", method = RequestMethod.POST)
    public ModelAndView saveChoice(@RequestParam(value = "repondent_id") String idRespondentString,
                                   @RequestParam(value = "items_id") String[] idItemsArray,
                                   @RequestParam(value = "scores") String[] scores){
        if(idItemsArray.length != scores.length) return new ModelAndView("redirect:/error");

        long idRespondent = getLongFromString(idRespondentString);
        if(idRespondent == -1) return new ModelAndView("redirect:/error");

        Respondent respondent = manager.findRespondentById(idRespondent);
        if(respondent == null) return new ModelAndView("redirect:/error");

        List<Long> idItemsList = new ArrayList<>();
        List<Integer> scoresList = new ArrayList<>();
        for(int i = 0; i < idItemsArray.length; ++i){
            long idItem = getLongFromString(idItemsArray[i]);
            if(idItem == -1) return new ModelAndView("redirect:/error");

            int score = getIntFromString(scores[i]);
            if(score < 0) return new ModelAndView("redirect:/sondage/repondre?token=" + respondent.getToken() + "&error=true");

            if(scoresList.contains(score)) return new ModelAndView("redirect:/sondage/repondre?token=" + respondent.getToken() + "&error=true");

            idItemsList.add(idItem);
            scoresList.add(score);
        }

        manager.updateRespondentAccessById(respondent.getId(), respondent.getToken(), true);

        Collection<Choice> choices = new HashSet<>();
        for(int i = 0; i < idItemsArray.length; ++i){
            SurveyItem item = manager.findSurveyItemById(idItemsList.get(i));
            if(item == null) return new ModelAndView("redirect:/error");

            Choice choice = new Choice();
            choice.setRespondent(respondent);
            choice.setItem(item);
            choice.setScore(scoresList.get(i));

            manager.deleteChoiceByRespondentIdAndItemId(idRespondent, idItemsList.get(i));
            manager.saveChoice(choice); //TODO peu etre juste update

            choices.add(choice);
        }

        manager.sendRecapMail(respondent.getEmail(), choices);
        return new ModelAndView("choices_saved");
    }

    /**
     * Renouvelle l'accès d'un sondé à un sondage.
     * @param idRespondentString Identifiant du sondé.
     * @return Renvoi sur l'index.
     */
    @RequestMapping(value = "/renouvelerAcces", method = RequestMethod.GET)
    public ModelAndView sendNewAccess(@RequestParam(value = "id") String idRespondentString){
        long idRespondent = getLongFromString(idRespondentString);
        if(idRespondent == -1) return new ModelAndView("redirect:/error");

        String token = RandomStringUtils.randomAlphanumeric(100);
        manager.updateRespondentAccessById(idRespondent, token, false);

        Respondent respondent = manager.findRespondentById(idRespondent);
        manager.sendAccessSurveyMail(respondent.getEmail(), respondent.getToken(), respondent.getSurvey().getName());

        return new ModelAndView("redirect:/");
    }

    /**
     * Méthode réalisant le mapping de l'adresse "/sondage/liste". A cette
     * adresse on y trouve la liste de nos sondages.
     * @return Renvoi la page contenant la liste.
     */
    @RequestMapping("/liste")
    public ModelAndView showList() {
        if(!user.isConnected()) return new ModelAndView("redirect:/");

        Collection<Survey> surveys = manager.findSurveyByPollsterId(user.getPollster().getId());

        ModelAndView mv = new ModelAndView("list_survey");
        mv.addObject("mySurveys", surveys);

        return mv;
    }

    /**
     * Méthode réalisant le mapping de l'adresse "/sondage/nouveau". A cette
     * adresse on peux créer un sondage.
     * @return Renvoi la page de création de sondage.
     */
    @RequestMapping(value = "/nouveau", method = RequestMethod.GET)
    public ModelAndView showCreateSurveyForm(){
        if(!user.isConnected()) return new ModelAndView("redirect:/");

        return new ModelAndView("new_survey");
    }

    /**
     * Méthode réalisant la création d'un sondage.
     * @param survey Objet correspondant au nouveau sondage.
     * @param result Objets représentant les potentielles erreurs du nouveau sondage.
     * @param nbOptionsString Nombre d'options du nouveau sondage.
     * @return Renvoi à la page d'etidition du sondage en question.
     */
    @RequestMapping(value = "/nouveau", method = RequestMethod.POST)
    public ModelAndView createSurvey(@ModelAttribute @Valid Survey survey, BindingResult result,
                                     @RequestParam(value = "nbOptions") String nbOptionsString){
        if(survey == null) return new ModelAndView("redirect:/");
        if(!user.isConnected()) return new ModelAndView("redirect:/");

        int nbOptions = 0;
        try{
            nbOptions = Integer.parseInt(nbOptionsString);
        } catch (NumberFormatException e){
            System.err.println("Le nombre d'option n'est pas valide.");
        }

        survey.setItems(null);
        for(int i = 0; i < nbOptions; ++i){
            SurveyItem item = new SurveyItem();
            item.setNbPersMin(1);
            item.setNbPersMax(1);
            survey.addItem(item);
        }

        surveyValidator.validate(survey, result);

        ModelAndView mv;
        if (!result.hasErrors()) {
            survey.setPollster(user.getPollster());
            manager.saveSurvey(survey);

            if(nbOptions == 0){
                mv = new ModelAndView("redirect:/sondage/liste");
            } else {
                mv = new ModelAndView("redirect:/sondage/editer?id=" + survey.getId());
            }
        }  else {
            mv = new ModelAndView("new_survey");
        }

        return mv;
    }

    /**
     * Méthode réalisant le mapping de l'adresse "/sondage/editer". A cette
     * adresse on peux modifier un sondage.
     * @param idSurveyString Identifiant du sondage à modifier.
     * @return Renvoi la page de modofication du sondage.
     */
    @RequestMapping(value = "/editer", method = RequestMethod.GET)
    public ModelAndView showEditSurveyForm(@RequestParam(name = "id") String idSurveyString){
        if(!user.isConnected())return new ModelAndView("redirect:/");

        long idSurvey = getLongFromString(idSurveyString);
        if(idSurvey == -1) return new ModelAndView("redirect:/");

        Survey s = manager.findSurveyById(idSurvey);
        if(s == null) return new ModelAndView("redirect:/");
        if(s.getPollster().getId() != user.getPollster().getId()) return new ModelAndView("redirect:/");

        ModelAndView mv = new ModelAndView("edit_survey");
        mv.addObject("survey", s);
        return mv;
    }

    /**
     * Méthode réalisant la modification d'un sondage.
     * @param survey Sondage modifié.
     * @param result Objets représentant les potentielles erreurs du sondage modifié.
     * @return Renvoi sur la page listant les sondages.
     */
    @RequestMapping(value = "/editer", method = RequestMethod.POST)
    public ModelAndView updateSurvey(@ModelAttribute @Valid Survey survey, BindingResult result){
        if(!user.isConnected()) return new ModelAndView("redirect:/");

        Survey s = manager.findSurveyById(survey.getId());
        if(s == null) return new ModelAndView("redirect:/");
        if(s.getPollster().getId() != user.getPollster().getId()) return new ModelAndView("redirect:/");

        surveyValidator.validate(survey, result);
        if(result.hasErrors()){
            ModelAndView mv = new ModelAndView("edit_survey");
            mv.addObject("survey", survey);

            return mv;
        }

        survey.setPollster(s.getPollster());
        if(survey.getItems() != null) {
            for (SurveyItem item : survey.getItems()) {
                item.setParent(survey);
            }
        }

        for(Respondent r : s.getRespondents()) {
            System.out.println(r.getTags()); //ne pas enlever sinon bug avec les tags des items
            survey.addRespondent(r);
        }

        manager.saveSurvey(survey);

        return new ModelAndView("redirect:/sondage/liste");
    }

    /**
     * Méthode réalisant la suppression d'un sondage.
     * @param idSurveyString Identifiant du sondage à supprimer.
     * @return Renvoi sur la page listant les sondages.
     */
    @RequestMapping(value = "/supprimer", method = RequestMethod.GET)
    public ModelAndView deleteSurvey(@RequestParam(value = "id") String idSurveyString){
        if(!user.isConnected()) return new ModelAndView("redirect:/");

        long idSurvey = getLongFromString(idSurveyString);
        if(idSurvey == -1) return new ModelAndView("redirect:/");

        Survey s = manager.findSurveyById(idSurvey);
        if(s == null) return new ModelAndView("redirect:/");
        if(s.getPollster().getId() != user.getPollster().getId()) return new ModelAndView("redirect:/");

        manager.deleteSurveyById(idSurvey);

        return new ModelAndView("redirect:/sondage/liste");
    }

    /**
     * Méthode réalisant l'ajout d'une option à un sondage.
     * @param idSurveyString Identifiant du sondage auquel l'option soit être rajouté.
     * @return Renvoi sur la page d'étition du sondage.
     */
    @RequestMapping(value = "/items/ajouter", method = RequestMethod.GET)
    public ModelAndView addItem(@RequestParam(value = "id") String idSurveyString){
        if(!user.isConnected()) return new ModelAndView("redirect:/");

        long idSurvey = getLongFromString(idSurveyString);
        if(idSurvey == -1) return new ModelAndView("redirect:/");

        Survey s = manager.findSurveyById(idSurvey);
        if(s == null) return new ModelAndView("redirect:/");
        if(s.getPollster().getId() != user.getPollster().getId()) return new ModelAndView("redirect:/");

        SurveyItem item = new SurveyItem();
        item.setNbPersMin(1);
        item.setNbPersMax(1);

        s.addItem(item);
        manager.saveSurvey(s);

        return new ModelAndView("redirect:/sondage/editer?id=" + s.getId());
    }

    /**
     * Méthode réalisant la suppression d'une option d'un sondage.
     * @param idItemString identifiant de l'option à supprimer.
     * @return Renvoi sur la page d'étition du sondage.
     */
    @RequestMapping(value = "/items/supprimer", method = RequestMethod.GET)
    public ModelAndView deleteItem(@RequestParam(value = "id") String idItemString){
        if(!user.isConnected()) return new ModelAndView("redirect:/");

        long idItem = getLongFromString(idItemString);
        if(idItem == -1) return new ModelAndView("redirect:/");

        SurveyItem item = manager.findSurveyItemById(idItem);
        if(item == null) return new ModelAndView("redirect:/");
        if(item.getParent().getPollster().getId() != user.getPollster().getId()) return new ModelAndView("redirect:/");

        manager.deleteSurveyItemById(idItem);
        Survey s = manager.findSurveyById(item.getParent().getId());

        ModelAndView mv = new ModelAndView("edit_survey");
        mv.addObject("survey", s);

        return mv;

    }

    /**
     * Méthode réalisant le mapping de l'adresse "/sondage/sondes/editer". A cette
     * adresse on peux modifier les sondés d'un sondage.
     * @param idSurveyString Identifiant du sondage.
     * @param error Indique si la liste des sondés possède un erreur.
     * @param success Indique si les sondés ont été correctement notifiés.
     * @return Renvoi la page de modification des sondés.
     */
    @RequestMapping(value = "/sondes/editer", method = RequestMethod.GET)
    public ModelAndView showSurveyRespondentsForm(@RequestParam(value = "id") String idSurveyString,
                                                  @RequestParam(value = "error", required = false) boolean error,
                                                  @RequestParam(value = "success", required = false) boolean success){
        if(!user.isConnected()) return new ModelAndView("redirect:/");

        long idSurvey = getLongFromString(idSurveyString);
        if(idSurvey == -1) return new ModelAndView("redirect:/");

        Survey s = manager.findSurveyById(idSurvey);
        if(s == null) return new ModelAndView("redirect:/");

        Collection<Respondent> respondents = manager.findAllRespondentsBySurveyId(idSurvey);

        ModelAndView mv = new ModelAndView("edit_survey_respondents");
        mv.addObject("id_survey", idSurvey);

        if(respondents == null) return mv;

        StringBuilder respondentsStringBuilder= new StringBuilder();
        for(Respondent r : respondents){
            respondentsStringBuilder.append(r.getEmail());
            for(String tag : r.getTags()){
                respondentsStringBuilder.append(";").append(tag);
            }
            respondentsStringBuilder.append("\n");
        }

        mv.addObject("respondents", respondentsStringBuilder.toString());
        mv.addObject("success", success);
        mv.addObject("error", error);
        return mv;
    }

    /**
     * Méthode réalisant la modification des sondés d'un sondage.
     * @param idSurveyString Identifiant du sondage.
     * @param respondentsString Chaine de caractère représentant l'ensemble des sondés.
     * @return Renvoi sur la page de modification des sondés.
     */
    @Transactional
    @RequestMapping(value = "/sondes/editer", method = RequestMethod.POST)
    public ModelAndView updateSurveyRespondents(@RequestParam(value = "id_survey") String idSurveyString,
                                                @RequestParam(value = "respondents_string") String respondentsString){
        if(!user.isConnected()) return new ModelAndView("redirect:/");

        long idSurvey = getLongFromString(idSurveyString);
        if(idSurvey == -1) return new ModelAndView("redirect:/");

        Survey s = manager.findSurveyById(idSurvey);
        if(s == null) return new ModelAndView("redirect:/");
        if(s.getPollster().getId() != user.getPollster().getId()) return new ModelAndView("redirect:/");

        List<Respondent> respondents = getRespondentsFromCsvStringFormat(respondentsString);
        List<Respondent> finalRespondents = new ArrayList<>();
        //if(respondents.size() < 1) return new ModelAndView("redirect:/");
        for(int i = 0; i < respondents.size(); ++i) {
            Respondent tmp = manager.findRespondentByEmailAndSurveyId(respondents.get(i).getEmail(), s.getId());
            if(tmp != null) {
                //si les tags ont changé
                if(!tmp.getTags().containsAll(respondents.get(i).getTags()) || !respondents.get(i).getTags().containsAll(tmp.getTags())){
                    tmp.setTags(respondents.get(i).getTags());
                }
                finalRespondents.add(tmp);
            } else {
                respondents.get(i).setToken(RandomStringUtils.randomAlphanumeric(100));
                respondents.get(i).setExpired(false);
                finalRespondents.add(respondents.get(i));
            }
        }

        Collection<Respondent> allRespondents = s.getRespondents();
        allRespondents.size();

        s.setRespondents(null);
        for(Respondent r : finalRespondents){
            s.addRespondent(r);
        }
        manager.saveSurvey(s);

        for(Respondent r : allRespondents){
            if(s.getRespondents() == null) {
                manager.deleteRespondentsBySurveyId(s.getId());
                break;
            }
            if(s.getRespondents().contains(r)) continue;
            manager.deleteRespondentById(r.getId());
        }

        return new ModelAndView("redirect:/sondage/sondes/editer?id=" + s.getId());
    }

    /**
     * Méthode permettant d'envoyer un mail contenant le lien d'accès à un sondage.
     * @param idSurveyString Identifiant du sondage.
     * @return Renvoi sur la page de modification des sondés.
     */
    @RequestMapping(value = "/sondes/notifierAcces", method = RequestMethod.GET)
    public ModelAndView notifyAllRespondentsForAccess(@RequestParam(value = "id") String idSurveyString){
        if(!user.isConnected()) return new ModelAndView("redirect:/");

        long idSurvey = getLongFromString(idSurveyString);
        if(idSurvey == -1) return new ModelAndView("redirect:/");

        Collection<Respondent> respondents = manager.findAllRespondentsBySurveyId(idSurvey);
        if(respondents == null || respondents.size() < 1) return new ModelAndView("redirect:/sondage/sondes/editer?id=" + idSurvey + "&error=" + true);

        Survey survey = manager.findSurveyById(idSurvey);
        if(survey == null) return new ModelAndView("redirect:/sondage/sondes/editer?id=" + idSurvey + "&error=" + true);

        for(Respondent r : respondents) manager.sendAccessSurveyMail(r.getEmail(), r.getToken(), survey.getName());

        return new ModelAndView("redirect:/sondage/sondes/editer?id=" + idSurvey + "&success=" + true);
    }

    /**
     * Méthode permettant d'envoyer un mail contenant l'affectation final d'un sondé.
     * @param idSurveyString Identifiant du sondage.
     * @return Renvoi sur la page des résultats d'un sondage.
     */
    @RequestMapping(value = "/sondes/notifierAffectation", method = RequestMethod.GET)
    public ModelAndView notifyAllRespondentsForFinalAffect(@RequestParam(value = "id") String idSurveyString){
        if(!user.isConnected()) return new ModelAndView("redirect:/");

        long idSurvey = getLongFromString(idSurveyString);
        if(idSurvey == -1) return new ModelAndView("redirect:/");

        Collection<Respondent> respondents = manager.findAllRespondentsBySurveyId(idSurvey);
        if(respondents == null || respondents.size() < 1) return new ModelAndView("redirect:/sondage/sondes/editer?id=" + idSurvey + "&error=" + true);

        Survey survey = manager.findSurveyById(idSurvey);

        if(survey == null) return new ModelAndView("redirect:/sondage/sondes/editer?id=" + idSurvey + "&error=" + true);

        for(Respondent r : respondents) manager.sendFinalAffectation(r);

        return new ModelAndView("redirect:/sondage/resultats?id=" + idSurvey + "&success=" + true);
    }

    /**
     * Méthode réalisant le mapping de l'adresse "/sondage/resultats". A cette
     * adresse on y trouve les résultats d'un sondage.
     * @return Renvoi la page des résultats d'un sondage.
     */
    @RequestMapping(value = "/resultats", method = RequestMethod.GET)
    public ModelAndView showResult(@RequestParam(value = "id") String idSurveyString){
        if(!user.isConnected()) return new ModelAndView("redirect:/");

        long idSurvey = getLongFromString(idSurveyString);
        if(idSurvey == -1) return new ModelAndView("redirect:/");

        Survey survey = manager.findSurveyById(idSurvey);
        if(survey == null) return new ModelAndView("redirect:/");

        ModelAndView mv = new ModelAndView("result");
        mv.addObject("survey", survey);

        return mv;
    }

    /**
     * Méthode réalisant l'affectation des sondés au options d'un sondage. Cette
     * méthode fais appel à l'algorythme de répartition.
     * @param idSurveyString Identifiant du sondage.
     * @return Renvoi la page des résultats d'un sondage.
     */
    @RequestMapping(value = "/resultats", method = RequestMethod.POST)
    public ModelAndView getResult(@RequestParam(value = "id") String idSurveyString){
        if(!user.isConnected()) return new ModelAndView("redirect:/");

        long idSurvey = getLongFromString(idSurveyString);
        if(idSurvey == -1) return new ModelAndView("redirect:/");

        Survey survey = manager.findSurveyById(idSurvey);
        if(survey == null) return new ModelAndView("redirect:/");

        manager.makeAffectation(survey);

        ModelAndView mv = new ModelAndView("result");
        mv.addObject("survey", survey);

        return mv;
    }


    @ModelAttribute("survey")
    public Survey survey(){
        return new Survey();
    }

    @ModelAttribute("user")
    public User user() {
        return user;
    }


    /**
     * Converti un String en long si possible.
     * @param longString String à convertir.
     * @return Long correspondant au string.
     */
    private long getLongFromString(String longString){
        long number = -1;
        try{
            number = Long.parseLong(longString);
        } catch (NumberFormatException e){
            System.err.println("Le nombre n'est pas valide.");
        }

        return number;
    }

    /**
     * Converti un String en int si possible.
     * @param intString String à convertir.
     * @return Int correspondant au string.
     */
    private int getIntFromString(String intString){
        int number = -1;
        try{
            number = Integer.parseInt(intString);
        } catch (NumberFormatException e){
            System.err.println("Le nombre n'est pas valide.");
        }

        return number;
    }

    /**
     * Converti une chaine de caractères correspondant à des sondés en objets Respondent
     * @param respondentsString Chaine de caractères correspondant à des sondés
     * @return List de Respondent correspondente.
     */
    private List<Respondent> getRespondentsFromCsvStringFormat(String respondentsString){
        /* TODO trouver une meilleur manière de gérer les sondés */
        List<Respondent> respondents = new ArrayList<>();
        try(CSVParser parser = CSVParser.parse(respondentsString, CSVFormat.newFormat(';'))) {
            for (CSVRecord csvRecord : parser) {
                if(csvRecord.get(0) == null) continue;
                if(!EmailValidator.getInstance().isValid(csvRecord.get(0))) continue;
                if(!csvRecord.get(0).matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+")) continue;

                Respondent r = new Respondent();
                r.setEmail(csvRecord.get(0));
                for(int i = 1; i < csvRecord.size(); ++i) {
                    if(csvRecord.get(i).equals("")) continue;
                    r.addTag(csvRecord.get(i));
                }
                respondents.add(r);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        /* ***************************************************** */

        return respondents;
    }
}