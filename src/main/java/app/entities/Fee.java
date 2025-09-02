package app.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
public class Fee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int amount;
    private LocalDate payDate;

    @ManyToOne
    private Person person;

    public Fee(LocalDate payDate, int amount) {
        this.payDate = payDate;
        this.amount = amount;
    }
}
