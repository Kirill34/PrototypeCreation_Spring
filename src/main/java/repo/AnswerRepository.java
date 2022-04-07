package repo;

import model.Answer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@org.springframework.stereotype.Repository
public interface AnswerRepository extends CrudRepository<Answer, Integer> {

}
