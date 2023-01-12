package se.taekwondointernship.data.service;

import se.taekwondointernship.data.models.dto.MessageDto;
import se.taekwondointernship.data.models.form.MessageForm;

public interface MessageService {
    MessageDto create(MessageForm form, String type);
    MessageDto edit(String type, MessageForm form);
    MessageDto findMessage(String type);
}
