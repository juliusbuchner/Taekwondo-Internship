package se.taekwondointernship.data.service;

import se.taekwondointernship.data.models.entity.Message;
import se.taekwondointernship.data.models.form.MessageForm;

public interface MessageService {
    Message create(MessageForm form, String url);
    Message edit(MessageForm form, String url);
}
