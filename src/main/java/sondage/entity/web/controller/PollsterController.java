package sondage.entity.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import sondage.entity.model.Pollster;
import sondage.entity.model.User;
import sondage.entity.web.IDirectoryManager;
import sondage.entity.web.validator.PollsterValidator;

import javax.validation.Valid;


@Controller()
@RequestMapping("/sondeur")
public class PollsterController {

    @Autowired
    IDirectoryManager manager;

    @Autowired
    User user;

    @Autowired
    PollsterValidator pollsterValidator;

    @RequestMapping(value = "/connexion", method = RequestMethod.POST)
    public ModelAndView login(@RequestParam(value = "email", required = true) String email,
                              @RequestParam(value = "password", required = true) String pass) {
        manager.login(user, email, pass);

        return new ModelAndView("redirect:/");
    }

    @RequestMapping("/deconnexion")
    public ModelAndView logout() {
        manager.logout(user);

        return new ModelAndView("redirect:/");
    }

    @RequestMapping("/profile")
    public ModelAndView showProfil(){
        if(!user.isConnected()){
            return new ModelAndView("redirect:/");
        }

        return new ModelAndView("profil");
    }

    @RequestMapping("/nouveau")
    public ModelAndView showCreatePollster(){
        if(!user.isConnected()){
            return new ModelAndView("redirect:/");
        }

        return new ModelAndView("new_pollster");
    }

    @RequestMapping("/creer")
    public ModelAndView createPollster(@ModelAttribute @Valid Pollster pollster, BindingResult result){
        if(!user.isConnected()){
            return new ModelAndView("redirect:/");
        }

        pollsterValidator.validate(pollster, result);

        if (!result.hasErrors()) {
            Pollster p = manager.findPollsterByEmail(pollster.getEmail());
            if(p == null) {
                manager.savePollster(pollster);
                System.err.println("Nouveau sondeur créé: " + pollster);
            }
        }

        return new ModelAndView("redirect:/sondeur/nouveau");
    }

    @ModelAttribute("user")
    public User user() {
        return user;
    }

    @ModelAttribute
    public Pollster pollster(@RequestParam(value = "firstName", required = false) String firstName,
                             @RequestParam(value = "lastName", required = false) String lastName,
                             @RequestParam(value = "email", required = false) String email,
                             @RequestParam(value = "password", required = false) String password){
        Pollster pollster = new Pollster();
        pollster.setFirstName(firstName);
        pollster.setLastName(lastName);
        pollster.setEmail(email);
        pollster.setPassword(password);

        return pollster;
    }
}