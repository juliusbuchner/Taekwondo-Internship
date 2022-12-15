package se.taekwondointernship.data.models.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;


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
    private Integer personId;
    private Integer createPassId;
  //  private String firstName;
    //private String lastName;
    //private String parentPhoneNumber;
   // private String parentName;
   // private String className;
   // private String age;

  //  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="dd-mm-yyyy")
    private LocalDate date=LocalDate.now();



    public Pass(int personId, int createPassId, LocalDate date){
        this.personId=personId;
        this.createPassId=createPassId;
        this.date=LocalDate.now();
    }


}
