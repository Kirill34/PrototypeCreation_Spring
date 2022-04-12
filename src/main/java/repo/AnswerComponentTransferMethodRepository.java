package repo;

import model.AnswerComponentTransferMethod;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.criteria.CriteriaBuilder;

public interface AnswerComponentTransferMethodRepository extends CrudRepository<AnswerComponentTransferMethod, Integer> {
}
