package repo;

import model.DataElement;
import model.Problem;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DataElementRepositiory extends CrudRepository<DataElement,Integer> {
    List<DataElement> findAllByProblem(Problem problem);
    DataElement findByProblemAndAndName(Problem problem, String name);
}
