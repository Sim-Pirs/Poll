package sondage.services;

import org.springframework.data.repository.CrudRepository;
import sondage.model.Answer;

public interface IAnswerDAO extends CrudRepository<Answer, Long> {

    Answer findById(long id);

    Answer save(Answer s);
}