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

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;


@Controller()
@RequestMapping("/sondeur")
public class PollsterController {

    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;
    private static final String SALT = "711nndsihNPVFL9s6eo8c5UqTG0PziZ340KwXEdGIoNWsvsrD09AMuwcp03MWKO9ymYKe82MokltQiCXUAOvSct5EJpEJoBrMPfK";

    @Autowired
    IDirectoryManager manager;

    @Autowired
    User user;

    @Autowired
    PollsterValidator pollsterValidator;

    @RequestMapping(value = "/connexion", method = RequestMethod.POST)
    public ModelAndView login(@RequestParam(value = "email") String email,
                              @RequestParam(value = "password") String pass) {
        pass = generateSecurePassword(pass, SALT);
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

    @RequestMapping(value = "/nouveau", method = RequestMethod.POST)
    public ModelAndView createPollster(@ModelAttribute @Valid Pollster pollster, BindingResult result){
        if(!user.isConnected()) return new ModelAndView("redirect:/");

        pollsterValidator.validate(pollster, result);
        if (result.hasErrors()) return new ModelAndView("new_pollster");

        pollster.setPassword(generateSecurePassword(pollster.getPassword(), SALT));
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


    public static String generateSecurePassword(String password, String salt) {
        String returnValue = null;
        byte[] securePassword = hash(password.toCharArray(), salt.getBytes());

        returnValue = Base64.getEncoder().encodeToString(securePassword);

        return returnValue;
    }

    private static byte[] hash(char[] password, byte[] salt) {
        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
        Arrays.fill(password, Character.MIN_VALUE);
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        } finally {
            spec.clearPassword();
        }
    }
}