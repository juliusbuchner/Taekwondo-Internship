package se.taekwondointernship.data.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.taekwondointernship.data.models.dto.MessageDto;
import se.taekwondointernship.data.models.entity.MessageEntity;
import se.taekwondointernship.data.models.form.MessageForm;
import se.taekwondointernship.data.repository.MessageRepository;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    public MessageDto create(MessageForm form, String url){
        MessageEntity messageEntity = repository.save(modelMapper.map(form, MessageEntity.class));
        return extractMessage(messageEntity, url);
    }

    @Override
    @Transactional
    public MessageDto edit(MessageForm form, String url){
        MessageEntity messageEntity = modelMapper.map(form, MessageEntity.class);
        messageEntity.setMessageId(1);
        return extractMessage(messageEntity, url);
    }

    private MessageDto extractMessage(MessageEntity messageEntity, String url){
        JSONObject jsonMessageDetails = new JSONObject();
        jsonMessageDetails.put("messageId", messageEntity.getMessageId());
        jsonMessageDetails.put("messageContent", messageEntity.getMessageContent());
        try (FileWriter file = new FileWriter(url)){
            file.write(jsonMessageDetails.toJSONString());
            file.flush();
        } catch (IOException e){
            e.printStackTrace();
        }
        return modelMapper.map(messageEntity, MessageDto.class);
    }

    public MessageDto findMessage(String url) {
        try {
            return modelMapper.map(getFromExistingMessageJSON(url).get(0), MessageDto.class);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private List<MessageEntity> getFromExistingMessageJSON(String url) throws IOException, ParseException {
        List<MessageEntity> messageEntityList = new ArrayList<>();
        JSONParser jsonParser = new JSONParser();
        FileReader reader = new FileReader(url);
        Object obj = jsonParser.parse(reader);
        JSONArray jsonMessageList = new JSONArray();
        jsonMessageList.add(obj);
        jsonMessageList.forEach(mbr -> messageEntityList.add(parseJsonMessage((JSONObject) mbr)));
        return messageEntityList;
    }

    private MessageEntity parseJsonMessage(JSONObject objectMessage) {
        Integer messageId = Integer.parseInt(String.valueOf(objectMessage.get("messageId")));
        String messageContent = (String) objectMessage.get("messageContent");
        return new MessageEntity(messageId, messageContent);
    }
}
