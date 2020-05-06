package sondage.entity.services;

import org.springframework.data.repository.CrudRepository;
import sondage.entity.model.Result;


public interface IResultDAO extends CrudRepository<Result, Long> {

    Result findById(long id);

    Result save(Result r);
}
