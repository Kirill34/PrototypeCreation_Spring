package repo;

import model.AnswerComponentTransferMethod;
import model.AnswerDatatype;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.criteria.CriteriaBuilder;
public interface AnswerDatatypeRepository extends CrudRepository<AnswerDatatype, Integer> {

}
