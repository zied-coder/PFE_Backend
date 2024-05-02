package pfe.spring.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SessionControle implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idSession;
    private String code;
    private Date date_Debut;
    private Date date_Fin;
    private String objet;
    private int progress;
    private LocalDateTime date_Validation;
    @Enumerated(EnumType.STRING)
    private Etat etat;






    @OneToMany(mappedBy = "session",cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<SessionContribuable> contribuablesSession;


    @ManyToOne(cascade=CascadeType.ALL)
    private User admin;




    @ManyToMany(fetch = FetchType.EAGER)
    private List<User> controlleurs;

}
