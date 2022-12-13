package se.taekwondointernship.data.service;

import org.json.simple.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.taekwondointernship.data.models.entity.Message;
import se.taekwondointernship.data.models.form.MessageForm;
import se.taekwondointernship.data.repository.MessageRepository;

import java.io.FileWriter;
import java.io.IOException;

@Service
public class MessageServiceImpl implements MessageService{
    MessageRepository repository;
    ModelMapper modelMapper;
    @Autowired
    public MessageServiceImpl(MessageRepository repository, ModelMapper modelMapper){
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public Message create(MessageForm form, String url){
        Message message = repository.save(modelMapper.map(form, Message.class));
        return extractMessage(message, url);
    }

    @Override
    @Transactional
    public Message edit(MessageForm form, String url){
        Message message = modelMapper.map(form, Message.class);
        message.setMessageId(1);
        return extractMessage(message, url);
    }

    private Message extractMessage(Message message, String url){
        JSONObject jsonMessageDetails = new JSONObject();
        jsonMessageDetails.put("messageId", message.getMessageId());
        jsonMessageDetails.put("messageContent", message.getMessageContent());
        JSONObject jsonMessage = new JSONObject();
        jsonMessage.put("message", jsonMessageDetails);
        try (FileWriter file = new FileWriter(url)){
            file.write(jsonMessage.toJSONString());
            file.flush();
        } catch (IOException e){
            e.printStackTrace();
        }
        return message;
    }
}
