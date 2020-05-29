package sondage.entity.services;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import sondage.entity.model.Respondent;

import java.util.Collection;

public interface IRespondentDAO extends CrudRepository<Respondent, Long> {

    Respondent findById(long id);

    Respondent findByEmailAndSurvey_Id(String email, long id);

    Collection<Respondent> findAllBySurvey_Id(long id);

    Respondent findByToken(String token);

    @Modifying
    @Query("update Respondent r set " +
            "r.token = :token, " +
            "r.isExpired = :isExpired " +
            "where r.id = :id")
    @Transactional
    void updateAccessById(long id, String token, boolean isExpired);

    Respondent save(Respondent r);

    void deleteAllBySurvey_Id(long id);
}
