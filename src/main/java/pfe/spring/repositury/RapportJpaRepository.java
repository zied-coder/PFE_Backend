package pfe.spring.repositury;

import pfe.spring.entity.Rapport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository("RapportJpaRepository")
public interface RapportJpaRepository extends JpaRepository<Rapport,Long> {
}
