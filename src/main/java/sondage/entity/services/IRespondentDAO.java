package sondage.entity.services;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import sondage.entity.model.Respondent;

import java.util.Collection;

public interface IRespondentDAO extends CrudRepository<Respondent, Long> {

    @Query("SELECT r FROM Respondent r JOIN FETCH r.tags WHERE r.id = ?1")
    Respondent findById(long id);

    Respondent findByEmailAndSurvey_Id(String email, long id);

    Collection<Respondent> findAllBySurvey_Id(long id);

    Respondent save(Respondent r);

    void deleteAllBySurvey_Id(long id);
}
