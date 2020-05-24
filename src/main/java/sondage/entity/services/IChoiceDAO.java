package sondage.entity.services;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import sondage.entity.model.Choice;

@Transactional
public interface IChoiceDAO extends CrudRepository<Choice, Long> {

    Choice findById(long id);

    Choice findByRespondent_IdAndItem_Id(long idResp, long idItem);

    Choice save(Choice c);

    void deleteByRespondent_IdAndItem_Id(long idResp, long idItem);
}
