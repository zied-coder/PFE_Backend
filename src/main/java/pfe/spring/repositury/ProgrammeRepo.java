package pfe.spring.repositury;

import pfe.spring.entity.Programme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProgrammeRepo extends JpaRepository<Programme, Long> {

    @Query("SELECT p FROM Programme p WHERE p.sessionContribuable.session.idSession = :sessionId")
    List<Programme> getProgrammesBySession(@Param("sessionId") Long sessionId);


    @Query("SELECT p FROM Programme p WHERE p.controlleur.username = :username")
    List<Programme> getProgrammesByControlleur(@Param("username") String username);


}
