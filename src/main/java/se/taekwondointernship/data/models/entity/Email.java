package se.taekwondointernship.data.models.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
public class Email {
    @Id
    private Integer id;
    private String sender;
    private String senderName;
    private String subject;
    private String password;
    private String attachURL;

    @Override
    public String toString(){
        return "[Sändare: " + sender + '\'' +
                ", Namn: " + senderName + '\'' +
                ", Ämne: " + subject +
                "]";
    }
}
