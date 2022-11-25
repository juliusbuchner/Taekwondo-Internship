package se.taekwondointernship.data.models.form;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode

public class PassForm {

    private String firstName;
    private String lastName;
    private String parentPhoneNumber;
    private String parentName;
}
