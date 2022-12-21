package se.taekwondointernship.data.service;

import org.springframework.transaction.annotation.Transactional;
import se.taekwondointernship.data.models.dto.EmailDto;
import se.taekwondointernship.data.models.dto.MessageDto;
import se.taekwondointernship.data.models.form.EmailForm;

public interface EmailService {
    EmailDto create(EmailForm form);
    EmailDto editSender(String sender);
    EmailDto editPassword(String password);
    EmailDto editSenderName(String name);
    EmailDto editSubject(String subject);
    void sending(String recipient);

    @Transactional
    EmailDto editAttachURL(String attachURL);

    String getPassword();
    MessageDto getEmailMessage();
}
