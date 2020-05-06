package sondage.entity.services;

import org.springframework.data.repository.CrudRepository;
import sondage.entity.model.Respondent;

public interface IRespondentDAO extends CrudRepository<Respondent, Long> {

    Respondent findById(long id);

    Respondent save(Respondent r);
}
