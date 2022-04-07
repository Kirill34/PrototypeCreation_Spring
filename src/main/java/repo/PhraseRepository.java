package repo;

import model.DataElement;
import model.Phrase;
import model.Problem;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.criteria.CriteriaBuilder;

public interface PhraseRepository extends CrudRepository<Phrase,Integer>{
    Phrase findByDataElement(DataElement dataElement);
}
