package se.taekwondointernship.data.models.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class PassDto {
    private String firstName;
    private String lastName;
    private String parentPhoneNumber;
    private String parentName;
    private String className;

}
