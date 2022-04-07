package repo;

import model.*;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface DataComponentRepository extends CrudRepository<DataComponent, Integer>{
    public List<DataComponent> findAllByImplementation(DataElementImplementation implementation);
}
