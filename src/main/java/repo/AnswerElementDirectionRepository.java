package repo;

import model.AnswerElementDirection;
import model.DataElement;
import model.Phrase;
import model.Problem;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.criteria.CriteriaBuilder;

public interface AnswerElementDirectionRepository extends CrudRepository<AnswerElementDirection, Integer>{
}
