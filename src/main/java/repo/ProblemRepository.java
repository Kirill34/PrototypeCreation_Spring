package repo;

import model.Problem;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface ProblemRepository extends CrudRepository<Problem, Integer> {
    Problem findById(Long id);
    List<Problem> findAll();
}
