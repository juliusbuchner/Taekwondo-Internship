package se.taekwondointernship.data.models.form;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AdminForm {
    @NotBlank(message = "Användarnamnet kan inte vara tomt.")
    private String username;
    @NotBlank(message = "Lösenordet kan inte vara tomt.")
    private String password;
}
