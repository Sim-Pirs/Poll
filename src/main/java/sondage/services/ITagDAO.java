package sondage.services;

import org.springframework.data.repository.CrudRepository;
import sondage.model.Tag;

public interface ITagDAO extends CrudRepository<Tag, Long> {

    Tag findById(long id);

    Tag save(Tag s);
}
