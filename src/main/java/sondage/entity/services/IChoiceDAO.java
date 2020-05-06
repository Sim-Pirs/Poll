package sondage.entity.services;

import org.springframework.data.repository.CrudRepository;
import sondage.entity.model.Choice;

public interface IChoiceDAO extends CrudRepository<Choice, Long> {

    Choice findById(long id);

    Choice save(Choice c);
}
