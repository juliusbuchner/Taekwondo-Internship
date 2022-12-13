package se.taekwondointernship.data.models.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AdminDto {
    private Integer id;

    private String username;

    private String password;
}
