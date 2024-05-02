package pfe.spring.repositury;

import pfe.spring.entity.SessionContribuable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionContribuableRepo extends JpaRepository<SessionContribuable, Long> {
}
