package se.taekwondointernship.data.models.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
public class MessageEntity {
    @Id
    private Integer messageId;
    private String messageType;
    private String messageContent;
}
