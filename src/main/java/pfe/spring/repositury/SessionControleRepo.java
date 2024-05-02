package pfe.spring.repositury;

import pfe.spring.entity.SessionControle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SessionControleRepo extends JpaRepository<SessionControle, Long> {

    List<SessionControle> findByCodeContainingIgnoreCase(String query);
}
