package sondage.entity.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("")
public class IndexController {

    @RequestMapping(value = "")
    public ModelAndView index(){
        ModelAndView mv = new ModelAndView("index");
        mv.addObject("phrase", "Envoyé par le controlleur");

        return mv;
    }
}
