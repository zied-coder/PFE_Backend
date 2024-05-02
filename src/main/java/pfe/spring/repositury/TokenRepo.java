package pfe.spring.repositury;


import pfe.spring.entity.TokenPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepo extends JpaRepository<TokenPassword, Long> {

    Optional<TokenPassword> findByToken(String token);
}
