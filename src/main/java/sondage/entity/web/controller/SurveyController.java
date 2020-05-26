package sondage.entity.web.controller;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import sondage.entity.model.*;
import sondage.entity.web.IDirectoryManager;
import sondage.entity.web.validator.SurveyValidator;

import javax.validation.Valid;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;


@Controller()
@RequestMapping("/sondage")
public class SurveyController {

    @Autowired
    IDirectoryManager manager;

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

    @RequestMapping("/liste")
    public ModelAndView showList() {
        if(!user.isConnected()) return new ModelAndView("redirect:/");

        Collection<Survey> surveys = manager.findSurveyByPollsterId(user.getPollster().getId());

        ModelAndView mv = new ModelAndView("list_survey");
        mv.addObject("mySurveys", surveys);

        return mv;
    }

    @RequestMapping(value = "/nouveau", method = RequestMethod.GET)
    public ModelAndView showCreateSurveyForm(){
        if(!user.isConnected()) return new ModelAndView("redirect:/");

        return new ModelAndView("new_survey");
    }

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

    @RequestMapping(value = "/sondes/editer", method = RequestMethod.POST)
    public ModelAndView updateSurveyRespondents(@RequestParam(value = "id_survey") String idSurveyString,
                                                @RequestParam(value = "respondents_string") String respondentsString){
        if(!user.isConnected()) return new ModelAndView("redirect:/");

        long idSurvey = getLongFromString(idSurveyString);
        if(idSurvey == -1) return new ModelAndView("redirect:/");

        Survey s = manager.findSurveyById(idSurvey);
        if(s == null) return new ModelAndView("redirect:/");
        if(s.getPollster().getId() != user.getPollster().getId()) return new ModelAndView("redirect:/");

        Collection<Respondent> respondents = getRespondentsFromCsvStringFormat(respondentsString);
        for(Respondent r : respondents) {
            Respondent tmp = manager.findRespondentByEmailAndSurveyId(r.getEmail(), s.getId());
            if(tmp != null) continue;

            r.setToken(RandomStringUtils.randomAlphanumeric(100));
            r.setExpired(false);
            s.addRespondent(r);
        }
        s.setRespondents(respondents);

        manager.deleteRespondentsBySurveyId(s.getId());
        manager.saveSurvey(s);

        return new ModelAndView("redirect:/sondage/sondes/editer?id=" + s.getId());
    }

    @RequestMapping(value = "/sondes/notifier", method = RequestMethod.GET)
    public ModelAndView notifyAllRespondents(@RequestParam(value = "id") String idSurveyString){
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

    @RequestMapping(value = "/resultats", method = RequestMethod.POST)
    public ModelAndView getResult(@RequestParam(value = "id") String idSurveyString){
        if(!user.isConnected()) return new ModelAndView("redirect:/");

        long idSurvey = getLongFromString(idSurveyString);
        if(idSurvey == -1) return new ModelAndView("redirect:/");

        Survey survey = manager.findSurveyById(idSurvey);
        if(survey == null) return new ModelAndView("redirect:/");

        //TODO appeler l'algo ici

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


    private long getLongFromString(String longString){
        long number = -1;
        try{
            number = Long.parseLong(longString);
        } catch (NumberFormatException e){
            System.err.println("Le nombre n'est pas valide.");
        }

        return number;
    }

    private int getIntFromString(String intString){
        int number = -1;
        try{
            number = Integer.parseInt(intString);
        } catch (NumberFormatException e){
            System.err.println("Le nombre n'est pas valide.");
        }

        return number;
    }

    private Collection<Respondent> getRespondentsFromCsvStringFormat(String respondentsString){
        /* TODO trouver une meilleur manière de gérer les sondés */
        Collection<Respondent> respondents = new HashSet<>();
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