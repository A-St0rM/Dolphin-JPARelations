package app.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "person")
@Entity
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int noteId;
    private String text;
    private LocalDate creationDate;
    private String createdBy;

    @ManyToOne
    private Person person;

    public Note(String text, String createdBy){
        this.text = text;
        this.createdBy = createdBy;
    }

    @PrePersist
    public void createDate(){
        this.creationDate = LocalDate.now();
    }
}
