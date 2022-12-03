package se.taekwondointernship.data.models.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer passId;
    private String firstName;
    private String lastName;
    private String parentPhoneNumber;
    private String parentName;
    private String className;
    private String age;

  //  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="dd-mm-yyyy")
    private LocalDate date=LocalDate.now();

    public Pass(String firstName, String lastName,String parentPhoneNumber, String parentName, String className, LocalDate date, String age){

        this.firstName=firstName;
        this.lastName=lastName;
        this.parentName=parentName;
        this.parentPhoneNumber=parentPhoneNumber;
        this.className=className;
        this.date=LocalDate.now();
        this.age=age;
    }
}
