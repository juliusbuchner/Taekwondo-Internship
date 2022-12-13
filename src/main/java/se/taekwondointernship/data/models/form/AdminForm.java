package se.taekwondointernship.data.models.form;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AdminForm {
    private String username;
    private String password;
}
