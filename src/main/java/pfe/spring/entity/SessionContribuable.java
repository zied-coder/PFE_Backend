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
public class SessionContribuable implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public SessionContribuable(String description, Contribuable contribuable) {
        this.description = description;
        this.contribuable = contribuable;
    }

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JsonIgnore
    @JoinColumn(name = "session_id")
    private SessionControle session;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "contribuable_id")
    private Contribuable contribuable;


    @JsonIgnore
    @OneToMany(cascade = CascadeType.PERSIST,mappedBy = "sessionContribuable")
    private List<Programme> programmes;



    @Column(name = "description")
    private String description;

}



