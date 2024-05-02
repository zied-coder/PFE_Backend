package pfe.spring.repositury;

import pfe.spring.entity.Contribuable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ContribuableRepo extends JpaRepository<Contribuable, Long> {
    List<Contribuable> findByNomIn(List<String>names);
    Contribuable findByNom(String nom);

    List<Contribuable> findByNomContainingIgnoreCase(String nomQuery);
}
