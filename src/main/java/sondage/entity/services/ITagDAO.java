package sondage.entity.services;

import org.springframework.data.repository.CrudRepository;
import sondage.entity.model.Tag;

public interface ITagDAO extends CrudRepository<Tag, Long> {

    Tag findById(long id);

    Tag save(Tag t);
}
