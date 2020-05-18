package sondage.entity.services;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import sondage.entity.model.Respondent;
import sondage.entity.model.Survey;

import javax.persistence.CollectionTable;
import java.util.Collection;


public interface ISurveyDAO extends CrudRepository<Survey, Long> {

    Survey findById(long id);

    Survey save(Survey s);

    Collection<Survey> findByPollster_Id(long id);

    @Modifying
    @Query("update Survey s set " +
            "s.respondents = ?2 " +
            "where s.id = ?1")
    @Transactional
    @CollectionTable
    int updateRespondentsById(long id, Collection<Respondent> respondents);
}
