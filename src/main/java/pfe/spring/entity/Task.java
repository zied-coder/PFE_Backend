package pfe.spring.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Document(collection = "task")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Task  implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String goal;

    private String description;

    @Enumerated(EnumType.STRING)
    private Avancement avancement;

    private int progress;

    private LocalDate date;




    @ManyToOne
    @JoinColumn(name = "programme_id")
    private Programme programme;




}