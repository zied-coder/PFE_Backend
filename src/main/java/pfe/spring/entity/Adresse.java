package pfe.spring.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Adresse implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idAdresse;
    private String rue;
    private String pays;
    private String numero;




    @ManyToOne
    Contribuable contribuable;
}
