package sondage.entity.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import sondage.entity.model.Pollster;
import sondage.entity.model.User;
import sondage.entity.web.IDirectoryManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("")
public class IndexController {

    private boolean aBoolean = false;

    @Autowired
    IDirectoryManager manager;

    @RequestMapping(value = "")
    public ModelAndView index(HttpSession session){
        if(!aBoolean) {
            Pollster pollster = new Pollster();
            pollster.setFirstName("a");
            pollster.setLastName("a");
            pollster.setEmail("a@a.a");
            pollster.setPassword("a");

            manager.savePollster(pollster);
            aBoolean = true;
        }

        System.err.println("----------------------------------------");
        User user = getUser(session);

        ModelAndView mv = new ModelAndView("index");
        mv.addObject("user", user);

        return mv;
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
