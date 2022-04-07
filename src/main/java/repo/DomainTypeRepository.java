package repo;

import model.DomainType;
import model.Problem;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.criteria.CriteriaBuilder;
public interface DomainTypeRepository extends CrudRepository<DomainType, Integer>{
    DomainType findById(Long id);
}
