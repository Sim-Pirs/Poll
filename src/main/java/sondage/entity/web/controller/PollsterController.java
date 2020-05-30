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
import sondage.entity.web.ISurveyManager;
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
    ISurveyManager manager;

    @Autowired
    User user;

    @Autowired
    PollsterValidator pollsterValidator;

    /**
     * Méthode réalisant la connexion d'un utilisateur.
     * @param email Email de l'utilsateur.
     * @param pass Mot de passe de l'utilisateur.
     * @return Renvoi le vue correspondante à la page "index".
     */
    @RequestMapping(value = "/connexion", method = RequestMethod.POST)
    public ModelAndView login(@RequestParam(value = "email") String email,
                              @RequestParam(value = "password") String pass) {
        pass = generateSecurePassword(pass, SALT);
        if(!manager.login(user, email, pass)){
            return new ModelAndView("redirect:/?error=true");
        }

        return new ModelAndView("redirect:/");
    }

    /**
     * Méthode réalisant la déconnexion d'un utilisateur.
     * @return Renvoi le vue correspondante à la page "index".
     */
    @RequestMapping("/deconnexion")
    public ModelAndView logout() {
        manager.logout(user);

        return new ModelAndView("redirect:/");
    }

    /**
     * Méthode réalisant le mapping de l'adresse "/sondeur/profile". A cette
     * adresse on peux consulter son profile.
     * @return Renvoi le vue correspondante à la page "profile".
     */
    @RequestMapping("/profile")
    public ModelAndView showProfil(){
        if(!user.isConnected()) return new ModelAndView("redirect:/");

        return new ModelAndView("profil");
    }

    /**
     * Méthode réalisant le mapping de l'adresse "/sondeur/nouveau". A cette
     * adresse on peux créer un nouveau sondeur.
     * @param success Indique si la création du sondeur s'est bien effectué ou non.
     * @return Renvoi le vue permettant la création d'un nouveau sondeur.
     */
    @RequestMapping(value = "/nouveau", method = RequestMethod.GET)
    public ModelAndView showCreatePollster(@RequestParam(value = "success", required = false) boolean success ){
        if(!user.isConnected()) return new ModelAndView("redirect:/");

        ModelAndView mv = new ModelAndView("new_pollster");
        mv.addObject("success", success);

        return mv;
    }

    /**
     * Méthode réalisant la création d'un nouveau sondeur.
     * @param pollster Objet correspondant au nouveau sondeur.
     * @param result Objet contenant les potentielles erreurs du nouveau sondeur.
     * @return Renvoi la page de création de nouveaux sondeurs.
     */
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

    /**
     * Hash le mot de passe passé en paramètre.
     * @param password Mot de passe à hasher.
     * @param salt Chaine de caractère nécessaire au hashage.
     * @return Mot de passe hashé.
     */
    public static String generateSecurePassword(String password, String salt) {
        String returnValue = null;
        byte[] securePassword = hash(password.toCharArray(), salt.getBytes());

        returnValue = Base64.getEncoder().encodeToString(securePassword);

        return returnValue;
    }

    /**
     * Hash le mot de passe passé en paramètre avec une instance de PBKDF2WithHmacSHA1.
     * @param password Mot de passe à hasher sous forme de tableau d'octets.
     * @param salt Chaine de caractère nécessaire au hashage sous forme de tableau d'octets.
     * @return Tableau d'octets correspondant au mot de passe hashé.
     */
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