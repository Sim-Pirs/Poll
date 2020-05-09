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
import sondage.entity.web.PollsterValidator;

import javax.servlet.http.HttpSession;

@Controller()
@RequestMapping("/sondeur")
public class PollsterController {

    @Autowired
    IDirectoryManager manager;

    @Autowired
    PollsterValidator pollsterValidator;

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

    @RequestMapping("/new")
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
        Pollster pollster = new Pollster();
        pollster.setFirstName(firstName);
        pollster.setLastName(lastName);
        pollster.setEmail(email);
        pollster.setPassword(password);

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

    /*
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ModelAndView editProfil(HttpSession session,
                                   @RequestParam(value = "firstName", required = false) String firstName,
                                   @RequestParam(value = "lastName", required = false) String lastName,
                                   @RequestParam(value = "email", required = false) String email,
                                   @RequestParam(value = "website", required = false) String website,
                                   @RequestParam(value = "birthday", required = false) String birthday,
                                   @RequestParam(value = "password", required = false) String password){
        if(session.getAttribute("user") == null) {
            return new ModelAndView("logout");
        }

        User user = (User) session.getAttribute("user");

        if(!firstName.equals("")) {
            user.getPerson().setFirstName(firstName);
        }
        if(!lastName.equals("")) {
            user.getPerson().setLastName(lastName);
        }
        if(!email.equals("")) {
            if(EmailValidator.getInstance().isValid(email))
                user.getPerson().setEmail(email);
        }
        if(!website.equals("")) {
            try {
                URL url = new URL(website);
                user.getPerson().setWebsite(website);
            } catch (MalformedURLException e) {
                System.err.println("Mauvaise url.");
            }

        }
        if(!birthday.equals("")) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date parsed = format.parse(birthday);
                user.getPerson().setBirthday(parsed);
            } catch (ParseException e) {
                System.err.println("Mauvaise date de naissance.");
            }
        }
        if(!password.equals("")) {
            user.getPerson().setPassword(password);
        }


        manager.savePerson(user.getPerson());
        session.setAttribute("user", user);

        ModelAndView mv = new ModelAndView("edit");

        return mv;
    }
    */
}