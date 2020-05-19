package sondage.security;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import sondage.entity.model.User;
import sondage.entity.services.IPollsterDAO;

public class UserService implements UserDetailsService {
	
    private final IPollsterDAO pollsterRepository;
    
    @Autowired
    public UserService(IPollsterDAO pollsterRepository) {
        this.pollsterRepository = pollsterRepository;
    }
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Objects.requireNonNull(email);
        User user = new User();
        user.setPollster(pollsterRepository.findByEmail(email));
        /*voir une exception si l'adresse mail n'existe pas*/
        return user;
    }
}
