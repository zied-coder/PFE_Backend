package pfe.spring.repositury;

import pfe.spring.entity.Impot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ImpotRepo extends JpaRepository<Impot, Long> {
}
