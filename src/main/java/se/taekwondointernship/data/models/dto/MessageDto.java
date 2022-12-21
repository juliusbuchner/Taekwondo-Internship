package se.taekwondointernship.data.models.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MessageDto {
    private Integer messageId;
    private String messageContent;
}
