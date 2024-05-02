package pfe.spring.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Contrib")
public class Contribuable implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idContribuable;
    @Column(unique = true)
    private String nom;
    @Enumerated(EnumType.STRING)
    private SecteurActivite secteurActivite;
    @Enumerated(EnumType.STRING)
    private SituationJuridique situationJuridique;
    @Enumerated(EnumType.STRING)
    private FormeJuridique formeJuridique;


    public Contribuable(String nom) {
        this.nom = nom;
    }


    @JsonIgnore
    @OneToMany(mappedBy = "contribuable",cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<SessionContribuable> contribuablesSession;

    @OneToMany(cascade=CascadeType.ALL, mappedBy="contribuable")
    private List<Adresse> adresses;


    @ManyToMany(cascade=CascadeType.ALL)
    private List<Impot> impotsContribuable;



}
