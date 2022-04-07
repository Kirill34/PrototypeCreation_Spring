package repo;

import model.Problem;
import model.Student;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

@org.springframework.stereotype.Repository
public interface StudentRepository extends CrudRepository<Student, Integer> {
    public Optional<Student> findById(Long id);
}
