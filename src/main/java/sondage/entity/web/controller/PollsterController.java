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

import javax.servlet.http.HttpSession;

@Controller()
@RequestMapping("/sondeur")
public class PollsterController {

    @Autowired
    IDirectoryManager manager;

    @RequestMapping(value = "/connexion", method = RequestMethod.POST)
    public ModelAndView login(HttpSession session,
                              @RequestParam(value = "email", required = true) String email,
                              @RequestParam(value = "password", required = true) String pass) {
        User user = getUser(session);
        manager.login(user, email, pass);

        ModelAndView mv = new ModelAndView("redirect:/");
        mv.addObject("user", user);

        return mv;
    }

    @RequestMapping("/deconnexion")
    public ModelAndView logout(HttpSession session) {
        User user = getUser(session);
        manager.logout(user);

        ModelAndView mv = new ModelAndView("redirect:/");
        mv.addObject("user", user);

        return mv;
    }

    @RequestMapping("/profile")
    public ModelAndView showProfil(HttpSession session){
        User user = getUser(session);
        if(!user.isConnected()){
            return new ModelAndView("redirect:/");
        }

        ModelAndView mv = new ModelAndView("profil");
        mv.addObject("user", user);

        return mv;
    }

    @RequestMapping("/nouveau")
    public ModelAndView showCreatePollster(HttpSession session){
        User user = getUser(session);
        if(!user.isConnected()){
            return new ModelAndView("redirect:/");
        }

        ModelAndView mv = new ModelAndView("new_pollster");
        mv.addObject("user", user);

        return mv;
    }

    @RequestMapping("/creer")
    public ModelAndView createPollster(HttpSession session, @ModelAttribute Pollster pollster, BindingResult result){
        User user = getUser(session);
        if(!user.isConnected()){
            return new ModelAndView("redirect:/");
        }

        ModelAndView mv = new ModelAndView("redirect:/sondeur/new");
        mv.addObject("user", user);

        //pollsterValidator.validate(pollster, result);

        //if (!result.hasErrors())
            manager.savePollster(pollster);

        return mv;
    }

    @ModelAttribute
    public Pollster createPollster(@RequestParam(value = "firstName", required = false) String firstName,
                                   @RequestParam(value = "lastName", required = false) String lastName,
                                   @RequestParam(value = "email", required = false) String email,
                                   @RequestParam(value = "password", required = false) String password){
        System.err.println("----------------------------");
        Pollster pollster = new Pollster();
        pollster.setFirstName(firstName);
        pollster.setLastName(lastName);
        pollster.setEmail(email);
        pollster.setPassword(password);
        System.out.println(pollster);
        System.err.println("----------------------------");

        return pollster;
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