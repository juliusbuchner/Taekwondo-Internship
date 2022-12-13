package se.taekwondointernship.data.models.form;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MessageForm {
    @NotBlank(message = "Meddelandet för nyregistrerade kan inte vara blankt.")
    private String messageContent;
}
