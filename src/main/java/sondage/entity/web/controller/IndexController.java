package sondage.entity.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import sondage.entity.model.Pollster;
import sondage.entity.model.User;
import sondage.entity.web.IDirectoryManager;

import java.util.Locale;


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

    @RequestMapping(value = "/connexion", method = RequestMethod.GET)
    public ModelAndView showLoginPage(@RequestParam(value = "error", required = false) boolean error) {
        ModelAndView mv = new ModelAndView("login");
        mv.addObject("error", error);
        return mv;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView login(@RequestParam(value = "email", required = true) String email,
                              @RequestParam(value = "password", required = true) String pass) {
        if(!manager.login(user, email, pass)){
            return new ModelAndView("redirect:/connexion?error=true");
        }

        return new ModelAndView("redirect:/");
    }

    @RequestMapping("/logout")
    public ModelAndView logout() {
        manager.logout(user);

        return new ModelAndView("redirect:/");
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
