package se.taekwondointernship.data.models.entity;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Entity

public class Pass {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer passId;
    private String firstName;
    private String lastName;
    private String parentPhoneNumber;

    private String parentName;
    private String className;
    private LocalDate date=LocalDate.now();

    public Pass(String firstName, String lastName,String parentPhoneNumber, String parentName, String className){

        this.firstName=firstName;
        this.lastName=lastName;
        this.parentName=parentName;
        this.parentPhoneNumber=parentPhoneNumber;
        this.className=className;
    }
}
