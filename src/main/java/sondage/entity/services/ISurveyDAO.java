package sondage.entity.services;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import sondage.entity.model.Pollster;
import sondage.entity.model.Respondent;
import sondage.entity.model.Survey;
import sondage.entity.model.SurveyItem;

import java.util.Collection;
import java.util.Date;
import java.util.List;


public interface ISurveyDAO extends CrudRepository<Survey, Long> {

    Survey findById(long id);

    Survey save(Survey s);

    Collection<Survey> findByPollster_Id(long id);

    @Modifying
    @Query("update Survey s set " +
            "s.name = :name, " +
            "s.description = :description, " +
            "s.endDate = :endDate, " +
            "s.pollster = :pollster, " +
            "s.items = :items, " +
            "s.respondents = :respondents " +
            "where s.id = :id")
    @Transactional
    int updateById(long id, String name, String description, Date endDate, Pollster pollster, List<SurveyItem> items, Collection<Respondent> respondents);
}
