package se.taekwondointernship.data.models.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PersonDto {
    private Integer personId;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String socialSecurityNumber;

    private String age;

    private String parentName;

    private String parentNumber;

    private boolean permissionPhoto;

}
