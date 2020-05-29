package sondage.entity.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import sondage.entity.model.User;
import sondage.entity.web.ISurveyManager;


@Controller
@RequestMapping("")
public class IndexController {

    @Autowired
    ISurveyManager manager;

    @Autowired
    User user;

    @RequestMapping(value = "")
    public ModelAndView index(@RequestParam(value = "error", required = false) boolean error){
        ModelAndView mv = new ModelAndView("index");
        mv.addObject("error", error);
        return mv;
    }
    
    @RequestMapping("/apropos")
    public ModelAndView about(){
    		return new  ModelAndView("about");
    }

    @ModelAttribute("user")
    public User user() {
        return user;
    }
}
