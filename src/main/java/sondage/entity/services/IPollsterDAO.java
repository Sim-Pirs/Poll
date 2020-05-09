package sondage.entity.services;

import org.springframework.data.repository.CrudRepository;
import sondage.entity.model.Pollster;

public interface IPollsterDAO extends CrudRepository<Pollster, Long> {

    Pollster findById(long id);

    Pollster findByEmailAndPassword(String email, String password);

    Pollster save(Pollster p);


}
