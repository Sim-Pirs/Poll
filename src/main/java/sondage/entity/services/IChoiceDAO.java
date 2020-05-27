package sondage.entity.services;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import sondage.entity.model.Choice;

import java.util.Collection;
import java.util.List;

@Transactional
public interface IChoiceDAO extends CrudRepository<Choice, Long> {

    Choice findById(long id);

    Choice findByRespondent_IdAndItem_Id(long idResp, long idItem);

    List<Choice> findAllByItem_Parent_Id(long id);

    Choice save(Choice c);

    void deleteByRespondent_IdAndItem_Id(long idResp, long idItem);
}
