package sondage.services;

import org.springframework.data.repository.CrudRepository;
import sondage.model.Pollster;

public interface IPollsterDAO extends CrudRepository<Pollster, Long> {

    Pollster findById(long id);

    Pollster save(Pollster s);
}
