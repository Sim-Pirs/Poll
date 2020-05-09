package sondage.entity.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import sondage.entity.model.Survey;
import sondage.entity.model.User;
import sondage.entity.web.IDirectoryManager;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;

@Controller()
@RequestMapping("/sondage")
public class SurveyController {

    @Autowired
    IDirectoryManager manager;


    @RequestMapping("/liste")
    public ModelAndView logout(HttpSession session) {
        User user = getUser(session);

        ModelAndView mv = new ModelAndView("list_survey");
        mv.addObject("user", user);

        return mv;
    }


    @RequestMapping("/nouveau")
    public ModelAndView showCreateSurvey(HttpSession session){
        User user = getUser(session);
        if(!user.isConnected()){
            return new ModelAndView("redirect:/");
        }

        ModelAndView mv = new ModelAndView("new_survey");
        mv.addObject("user", user);

        return mv;
    }


    @RequestMapping("/creer")
    public ModelAndView createSurvey(HttpSession session, @ModelAttribute Survey survey, BindingResult result){
        User user = getUser(session);
        if(!user.isConnected()){
            ModelAndView mv = new ModelAndView("redirect:/");
            mv.addObject("user", user);
            return mv;
        }

        ModelAndView mv = new ModelAndView("redirect:/sondage/nouveau");
        mv.addObject("user", user);

        //pollsterValidator.validate(pollster, result);

        //if (!result.hasErrors())
        manager.saveSurvey(survey);

        return mv;
    }



    @ModelAttribute
    public Survey createSurvey(HttpSession session,
                               @RequestParam(value = "name", required = false) String name,
                               @RequestParam(value = "description", required = false) String description,
                               @RequestParam(value = "endDate", required = false) String endDate){
        if(name == null && description == null && endDate == null)
            return new Survey();

        System.err.println(endDate);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Survey survey = new Survey();
        survey.setName(name);
        survey.setDescription(description);
        try {
            survey.setEndDate(format.parse(endDate));
            survey.setPollster(getUser(session).getPollster());
        } catch (ParseException e) {
            System.err.println("Format de la date incorrecte.");
            survey.setEndDate(null);
        }

        return survey;
    }

    @ModelAttribute("surveys")
    Collection<Survey> products() {
        return manager.findAllSurvey();
    }



    private User getUser(HttpSession session){
        User user;
        if(session.getAttribute("user") == null){
            user = manager.newUser();
            session.setAttribute("user", user);
        } else {
            user = (User) session.getAttribute("user");
        }

        return user;
    }
}