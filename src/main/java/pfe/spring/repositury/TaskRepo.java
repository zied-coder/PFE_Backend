package pfe.spring.repositury;

import pfe.spring.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface TaskRepo extends JpaRepository<Task,Long> {
}
