package se.taekwondointernship.data.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class PassDto {
    private int id;
    private int personId;
    private String firstName;
    private String lastName;
    private String parentPhoneNumber;
    private String parentName;
    private String className;
  //  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="dd-mm-yyyy")
    private LocalDate date=LocalDate.now();
    private String age;

}
