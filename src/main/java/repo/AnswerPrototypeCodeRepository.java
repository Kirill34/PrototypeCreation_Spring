package repo;

import model.AnswerComponentTransferMethod;
import model.AnswerDatatype;
import model.AnswerPrototypeCode;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.criteria.CriteriaBuilder;
public interface AnswerPrototypeCodeRepository extends CrudRepository<AnswerPrototypeCode,Integer> {
}
