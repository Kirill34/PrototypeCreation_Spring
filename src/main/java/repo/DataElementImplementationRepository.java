package repo;

import model.*;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface DataElementImplementationRepository extends CrudRepository<DataElementImplementation, Integer>{
    public List<DataElementImplementation> findAllByDataElement(DataElement dataElement);
    public DataElementImplementation findByDataElementAndName(DataElement dataElement, String name);
}
