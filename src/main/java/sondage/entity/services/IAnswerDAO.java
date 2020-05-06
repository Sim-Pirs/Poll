package sondage.entity.services;

import org.springframework.data.repository.CrudRepository;
import sondage.entity.model.Answer;

public interface IAnswerDAO extends CrudRepository<Answer, Long> {

    Answer findById(long id);

    Answer save(Answer a);
}
