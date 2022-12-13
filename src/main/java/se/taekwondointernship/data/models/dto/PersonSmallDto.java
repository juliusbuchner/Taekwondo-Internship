package se.taekwondointernship.data.models.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class PersonSmallDto {
    private Integer personId;
    private String firstName;
    private String lastName;
    private Integer passCount;
    private boolean locked;


}
