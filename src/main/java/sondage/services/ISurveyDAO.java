package sondage.services;

import org.springframework.data.repository.CrudRepository;
import sondage.model.Survey;

public interface ISurveyDAO extends CrudRepository<Survey, Long> {

    Survey findById(long id);

    Survey save(Survey s);
}
