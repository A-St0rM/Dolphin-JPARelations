package app.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class PersonDetail {

    @Id
    private int id;
    private String address;
    private int zip;
    private String city;
    private int age;

    // Relation 1:1
    @MapsId
    @OneToOne
    private Person person;

    public PersonDetail(String address, int zip, String city, int age) {
        this.address = address;
        this.zip = zip;
        this.city = city;
        this.age = age;

    }
}
