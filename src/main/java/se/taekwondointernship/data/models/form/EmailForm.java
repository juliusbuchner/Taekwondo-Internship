package se.taekwondointernship.data.models.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmailForm {
    @NotBlank(message = "Sändaren kan inte var tom.")
    private String sender;
    @NotBlank(message = "Lösenordet kan inte vara tomt.")
    private String password;
    private String senderName;
    private String subject;
    private String attachURL;
}
