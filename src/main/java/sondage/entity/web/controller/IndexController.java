package sondage.entity.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import sondage.entity.model.Pollster;
import sondage.entity.model.User;
import sondage.entity.web.IDirectoryManager;


@Controller
@RequestMapping("")
public class IndexController {

    private boolean aBoolean = false;

    @Autowired
    IDirectoryManager manager;

    @Autowired
    User user;

    @RequestMapping(value = "")
    public ModelAndView index(){
        if(!aBoolean) {
            Pollster pollster = new Pollster();
            pollster.setFirstName("Aa");
            pollster.setLastName("Aa");
            pollster.setEmail("a@a.a");
            pollster.setPassword("a");

            manager.savePollster(pollster);
            aBoolean = true;
        }

        return new ModelAndView("index");
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
