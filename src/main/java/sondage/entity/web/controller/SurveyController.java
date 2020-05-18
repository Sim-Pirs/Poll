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
import sondage.entity.model.Respondent;
import sondage.entity.model.Survey;
import sondage.entity.model.SurveyItem;
import sondage.entity.model.User;
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


    @RequestMapping("/liste")
    public ModelAndView list() {
        if(!user.isConnected()){
            return new ModelAndView("redirect:/");
        }

        Collection<Survey> surveys = manager.findSurveyByPollsterId(user.getPollster().getId());

        ModelAndView mv = new ModelAndView("list_survey");
        mv.addObject("mySurveys", surveys);

        return mv;
    }


    @RequestMapping("/nouveau")
    public ModelAndView showCreateSurveyPage(){
        if(!user.isConnected()){
            return new ModelAndView("redirect:/");
        }

        return new ModelAndView("new_survey");
    }


    @RequestMapping(value = "/creer", method = RequestMethod.POST)
    public ModelAndView createSurvey(@ModelAttribute @Valid Survey survey, BindingResult result,
                                     @RequestParam(value = "nbOptions", required = true) String nbOptionsString){
        if(survey == null){
            return new ModelAndView("redirect:/");
        }

        if(!user.isConnected()){
            return new ModelAndView("redirect:/");
        }

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
                mv = new ModelAndView("redirect:/sondage/edit?id=" + survey.getId());
            }
        }  else {
            System.err.println("Erreur dans le formulaire du sondage.");
            //mv = new ModelAndView("redirect:/sondage/nouveau");
            mv = new ModelAndView("new_survey");
        }

        return mv;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView editSurvey(@RequestParam(name = "id", required = true) String idSurveyString){
        if(!user.isConnected()){
            return new ModelAndView("redirect:/");
        }

        long idSurvey = -1;
        try{
            idSurvey = Long.parseLong(idSurveyString);
        } catch (NumberFormatException e){
            System.err.println("L'id n'est pas valide.");
            return new ModelAndView("redirect:/");
        }

        Survey s = manager.findSurveyById(idSurvey);

        if(s == null){
            return new ModelAndView("redirect:/");
        }

        if(s.getPollster().getId() != user.getPollster().getId()){
            return new ModelAndView("redirect:/");
        }

        ModelAndView mv = new ModelAndView("edit_survey");
        mv.addObject("survey", s);
        return mv;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView update(@ModelAttribute @Valid Survey survey, BindingResult result){
        if(!user.isConnected()){
            return new ModelAndView("redirect:/");
        }

        Survey s = manager.findSurveyById(survey.getId());

        if(s == null){
            return new ModelAndView("redirect:/");
        }

        if(s.getPollster().getId() != user.getPollster().getId()){
            return new ModelAndView("redirect:/");
        }
        surveyValidator.validate(survey, result);

        if(result.hasErrors()){
            ModelAndView mv = new ModelAndView("edit_survey");
            mv.addObject("survey", survey);

            return mv;
        }

        survey.setPollster(s.getPollster());
        if(survey.getItems() != null) {
            for (SurveyItem item : survey.getItems()) {
                survey.addItem(item);
            }
        }

        manager.removeSurveyById(survey.getId());
        manager.saveSurvey(survey);

        return new ModelAndView("redirect:/sondage/liste");
    }

    @RequestMapping(value = "/supprimer", method = RequestMethod.GET)
    public ModelAndView deleteSurvey(@RequestParam(value = "id") String idSurvey){
        long id = -1;
        try{
            id = Long.parseLong(idSurvey);
        } catch (NumberFormatException e){
            System.err.println("L'identifiant n'est pas valide.");
        }

        Survey survey = manager.findSurveyById(id);

        if(survey == null){
            return new ModelAndView("redirect:/");
        }

        if(!user.isConnected() || survey.getPollster().getId() != user.getPollster().getId()){
            return new ModelAndView("redirect:/");
        }

        manager.removeSurveyById(id);

        return new ModelAndView("redirect:/sondage/liste");
    }

    @RequestMapping(value = "/items/supprimer", method = RequestMethod.GET)
    public ModelAndView deleteItem(@RequestParam(value = "id", required = true) String idItemString){
        if(!user.isConnected()){
            return new ModelAndView("redirect:/");
        }

        long idItem = -1;
        try{
            idItem = Long.parseLong(idItemString);
        } catch (NumberFormatException e){
            System.err.println("L'id n'est pas valide.");
            return new ModelAndView("redirect:/");
        }

        SurveyItem item = manager.findSurveyItemById(idItem);

        if(item == null){
            return new ModelAndView("redirect:/");
        }

        if(item.getParent().getPollster().getId() != user.getPollster().getId()){
            return new ModelAndView("redirect:/");
        }

        manager.deleteSurveyItemById(idItem);
        Survey survey = manager.findSurveyById(item.getParent().getId());

        ModelAndView mv = new ModelAndView("edit_survey");
        mv.addObject("survey", survey);

        return mv;

    }

    @RequestMapping(value = "/items/ajouter", method = RequestMethod.GET)
    public ModelAndView addItem(@RequestParam(value = "id", required = true) String idSurveyString){
        if(!user.isConnected()) return new ModelAndView("redirect:/");

        long idSurvey = -1;
        try{
            idSurvey = Long.parseLong(idSurveyString);
        } catch (NumberFormatException e){
            System.err.println("L'id n'est pas valide.");
            return new ModelAndView("redirect:/");
        }

        Survey survey = manager.findSurveyById(idSurvey);

        if(survey == null) return new ModelAndView("redirect:/");
        if(survey.getPollster().getId() != user.getPollster().getId()) return new ModelAndView("redirect:/");

        SurveyItem item = new SurveyItem();
        item.setNbPersMin(1);
        item.setNbPersMax(1);
        survey.addItem(item);

        manager.removeSurveyById(survey.getId());
        Survey s = manager.saveSurvey(survey);

        ModelAndView mv = new ModelAndView("redirect:/sondage/edit?id=" + s.getId());

        return mv;
    }

    @RequestMapping(value = "/sondes/edit", method = RequestMethod.GET)
    public ModelAndView setupFormRespondents(@RequestParam(value = "id") String idSurveyString){
        if(!user.isConnected()){
            return new ModelAndView("redirect:/");
        }

        long idSurvey = -1;
        try{
            idSurvey = Long.parseLong(idSurveyString);
        } catch (NumberFormatException e){
            System.err.println("L'id n'est pas valide.");
            return new ModelAndView("redirect:/");
        }

        Survey survey = manager.findSurveyById(idSurvey);

        if(survey == null) return new ModelAndView("redirect:/");

        Collection<Respondent> respondents = manager.findAllRespondentsBySurveyId(idSurvey);

        ModelAndView mv = new ModelAndView("edit_survey_respondents");
        mv.addObject("id_survey", idSurvey);

        if(respondents == null){
            return mv;
        }

        StringBuilder respondentsStringBuilder= new StringBuilder();

        for(Respondent r : respondents){
            respondentsStringBuilder.append(r.getEmail());
            for(String tag : r.getTags()){
                respondentsStringBuilder.append(";").append(tag);
            }
            respondentsStringBuilder.append("\n");
        }


        mv.addObject("respondents", respondentsStringBuilder.toString());
        return mv;
    }

    @RequestMapping(value = "/sondes/save", method = RequestMethod.POST)
    public ModelAndView saveSurveyRespondents(@RequestParam(value = "id_survey") String idSurveyString,
                                              @RequestParam(value = "respondents_string") String respondentsString){
        if(!user.isConnected()){
            return new ModelAndView("redirect:/");
        }

        long idSurvey = -1;
        try{
            idSurvey = Long.parseLong(idSurveyString);
        } catch (NumberFormatException e){
            System.err.println("L'id n'est pas valide.");
            return new ModelAndView("redirect:/");
        }

        Survey survey = manager.findSurveyById(idSurvey);

        if(survey == null) return new ModelAndView("redirect:/");

        if(survey.getPollster().getId() != user.getPollster().getId()){
            return new ModelAndView("redirect:/");
        }

        manager.removeSurveyById(idSurvey);

        Collection<Respondent> respondents = getRespondentsFromCsvStringFormat(respondentsString);
        for(Respondent r : respondents) {
            survey.addRespondent(r);
        }
        survey.setRespondents(respondents);

        Survey s = manager.saveSurvey(survey);//TODO mieux sauvegarder car change l'id en suppr/add

        return new ModelAndView("redirect:/sondage/sondes/edit?id=" + s.getId());
    }


    @ModelAttribute
    public Survey survey(){
        return new Survey();
    }

    @ModelAttribute("user")
    public User user() {
        return user;
    }

    private Collection<Respondent> getRespondentsFromCsvStringFormat(String respondentsString){
        /* TODO trouver une meilleur manière de gérer les sondés */
        Collection<Respondent> respondents = new HashSet<>();
        try(CSVParser parser = CSVParser.parse(respondentsString, CSVFormat.newFormat(';'))) {
            for (CSVRecord csvRecord : parser) {
                if(csvRecord.get(0) == null) continue;
                if(!EmailValidator.getInstance().isValid(csvRecord.get(0))) continue;

                Respondent r = new Respondent();
                r.setEmail(csvRecord.get(0));
                for(int i = 1; i < csvRecord.size(); ++i) {
                    if(csvRecord.get(i).equals("")) continue;
                    r.addTag(csvRecord.get(i));
                }
                r.setToken(RandomStringUtils.randomAlphanumeric(100));
                respondents.add(r);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        /* ***************************************************** */

        return respondents;
    }
}