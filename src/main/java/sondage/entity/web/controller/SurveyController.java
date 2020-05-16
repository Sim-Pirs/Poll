package sondage.entity.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import sondage.entity.model.Survey;
import sondage.entity.model.SurveyItem;
import sondage.entity.model.User;
import sondage.entity.web.IDirectoryManager;
import sondage.entity.web.validator.SurveyItemValidator;
import sondage.entity.web.validator.SurveyValidator;

import javax.validation.Valid;
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


    @Autowired
    SurveyItemValidator surveyItemValidator;


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

        int nbOptions = 2;
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

            mv = new ModelAndView("redirect:/sondage/edit?id=" + survey.getId());
        }  else {
            System.err.println("Erreur dans le formulaire du sondage.");
            mv = new ModelAndView("redirect:/sondage/nouveau");
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
        for(SurveyItem item : survey.getItems())
            surveyItemValidator.validate(item, result);

        if(result.hasErrors()){
            ModelAndView mv = new ModelAndView("edit_survey");
            mv.addObject("survey", survey);
            return mv;
        }

        for (SurveyItem item : survey.getItems()) {
            item.setParent(survey);
        }

        survey.setPollster(s.getPollster());

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

    @RequestMapping(value = "/suppritem", method = RequestMethod.GET)
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


    @ModelAttribute
    public Survey survey(){
        return new Survey();
    }

    @ModelAttribute("user")
    public User user() {
        return user;
    }
}