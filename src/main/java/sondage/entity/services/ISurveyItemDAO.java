package sondage.entity.services;

import org.springframework.data.repository.CrudRepository;
import sondage.entity.model.SurveyItem;

public interface ISurveyItemDAO extends CrudRepository<SurveyItem, Long> {

    SurveyItem findById(long id);

    SurveyItem save(SurveyItem a);

    void deleteById(long id);
}
