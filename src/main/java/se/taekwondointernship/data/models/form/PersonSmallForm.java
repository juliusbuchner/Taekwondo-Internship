package se.taekwondointernship.data.models.form;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class PersonSmallForm {
    private Integer personId;
    private String firstName;
    private String lastName;
    private Integer passCount;
    private boolean locked ;
}
