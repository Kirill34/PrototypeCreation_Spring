package repo;

import model.DomainType;
import model.EntityField;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EntityFieldRepository extends CrudRepository<EntityField, Integer> {
    public List<EntityField> findAllByDomainType(DomainType domainType);
}
