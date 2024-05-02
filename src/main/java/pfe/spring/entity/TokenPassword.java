package pfe.spring.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity

public class TokenPassword {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idToken;

    private String token;

    private LocalDateTime expirationDate;



    @OneToOne
    User user;





}
