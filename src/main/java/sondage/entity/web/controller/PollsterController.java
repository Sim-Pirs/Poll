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
        if(!user.isConnected()) return new ModelAndView("redirect:/");

        return new ModelAndView("profil");
    }

    @RequestMapping(value = "/nouveau", method = RequestMethod.GET)
    public ModelAndView showCreatePollster(@RequestParam(value = "success", required = false) boolean success ){
        if(!user.isConnected()) return new ModelAndView("redirect:/");

        ModelAndView mv = new ModelAndView("new_pollster");
        mv.addObject("success", success);

        return mv;
    }

    @RequestMapping(value = "/creer", method = RequestMethod.POST)
    public ModelAndView createPollster(@ModelAttribute @Valid Pollster pollster, BindingResult result){
        if(!user.isConnected()) return new ModelAndView("redirect:/");

        pollsterValidator.validate(pollster, result);
        //TODO faire marcher pour avoir les erreurs lors des redirections
        //attr.addFlashAttribute("org.springframework.validation.BindingResult.pollster", result);
        //attr.addFlashAttribute("pollster", pollster);
        if (result.hasErrors()) return new ModelAndView("new_pollster");

        manager.savePollster(pollster);

        return new ModelAndView("redirect:/sondeur/nouveau?success=true");
    }

    @ModelAttribute("user")
    public User user() {
        return user;
    }

    @ModelAttribute("pollster")
    public Pollster pollster(){
        return new Pollster();
    }
}