package sondage.entity.services;

import org.springframework.data.repository.CrudRepository;
import sondage.entity.model.Survey;
import java.util.Collection;


public interface ISurveyDAO extends CrudRepository<Survey, Long> {

    Survey findById(long id);

    Survey save(Survey s);

    Collection<Survey> findByPollster_Id(long id);
}
