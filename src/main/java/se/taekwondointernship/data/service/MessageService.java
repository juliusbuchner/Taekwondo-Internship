package se.taekwondointernship.data.service;

import se.taekwondointernship.data.models.dto.MessageDto;
import se.taekwondointernship.data.models.form.MessageForm;

public interface MessageService {
    MessageDto create(MessageForm form, String url);
    MessageDto edit(MessageForm form, String url);
    MessageDto findMessage(String url);
}
