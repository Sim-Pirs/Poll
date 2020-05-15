package sondage.entity.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import sondage.entity.model.Pollster;
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
        if(!user.isConnected()){
            return new ModelAndView("redirect:/");
        }

        int nbOptions = 2;
        try{
            nbOptions = Integer.parseInt(nbOptionsString);
        } catch (NumberFormatException e){
            System.err.println("Le nombre d'option n'est pas valide.");
        }

        surveyValidator.validate(survey, result);

        ModelAndView mv;
        if (!result.hasErrors()) {
            survey.setPollster(user.getPollster());

            mv = new ModelAndView("edit_survey");
            mv.addObject("survey", survey);
            mv.addObject("nbOptions", nbOptions);

            manager.saveSurvey(survey);
        }  else {
            System.err.println("Erreur dans le formulaire du sondage.");
            mv = new ModelAndView("redirect:/sondage/nouveau");
        }

        return mv;
    }

    @RequestMapping(value = "/completer", method = RequestMethod.POST)
    public ModelAndView finishCreateSurvey(@ModelAttribute @Valid Survey survey, BindingResult result){
        if(!user.isConnected()){
            return new ModelAndView("redirect:/");
        }

        Survey s = manager.findSurveyById(survey.getId());

        System.err.println(s);
        //System.err.println(s.getPollster().getId());
        if(s.getPollster().getId() != user.getPollster().getId()){
            return new ModelAndView("redirect:/");
        }

        surveyValidator.validate(survey, result);

        if(result.hasErrors()){
            manager.removeSurveyById(survey.getId());
            ModelAndView mv = new ModelAndView("edit_survey");
            mv.addObject("survey", survey);
            mv.addObject("nbOptions", survey.getItems().size());
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


    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ModelAndView editSurvey(@RequestParam(name = "id_survey", required = true) String idSurveyString ){
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

        if(s.getPollster().getId() != user.getPollster().getId()){
            return new ModelAndView("redirect:/");
        }

        ModelAndView mv = new ModelAndView("edit_survey");
        mv.addObject("survey", s);
        mv.addObject("nbOptions", s.getItems().size());

        return mv;
    }


    @RequestMapping("/supprimer")
    public ModelAndView deleteSurvey(@RequestParam(value = "id_survey") String idSurvey){
        long id = -1;
        try{
            id = Long.parseLong(idSurvey);
        } catch (NumberFormatException e){
            System.err.println("L'identifiant n'est pas valide.");
        }



        Survey survey = manager.findSurveyById(id);

        if(!user.isConnected() || survey.getPollster().getId() != user.getPollster().getId()){
            ModelAndView mv = new ModelAndView("redirect:/");
            mv.addObject("user", user);
            return mv;
        }

        manager.removeSurveyById(id);

        return new ModelAndView("redirect:/sondage/liste");
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