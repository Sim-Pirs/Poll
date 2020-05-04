package sondage.services;

import org.springframework.data.repository.CrudRepository;
import sondage.model.Respondent;

public interface IRespondentDAO extends CrudRepository<Respondent, Long> {

    Respondent findById(long id);

    Respondent save(Respondent s);
}
