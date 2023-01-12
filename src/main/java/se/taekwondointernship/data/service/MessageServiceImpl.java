package se.taekwondointernship.data.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.taekwondointernship.data.models.dto.MessageDto;
import se.taekwondointernship.data.models.entity.MessageEntity;
import se.taekwondointernship.data.models.form.MessageForm;
import se.taekwondointernship.data.repository.MessageRepository;
import se.taekwondointernship.data.storage.Firebase;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService{
    MessageRepository repository;
    ModelMapper modelMapper;
    Firebase firebase = new Firebase();
    @Autowired
    public MessageServiceImpl(MessageRepository repository, ModelMapper modelMapper){
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public MessageDto create(MessageForm form, String type){
        MessageEntity messageEntity = modelMapper.map(form, MessageEntity.class);
        firebase.uploadMessage(messageEntity, type);
        repository.save(messageEntity);
        return modelMapper.map(messageEntity, MessageDto.class);
    }

    @Override
    @Transactional
    public MessageDto edit(String type, MessageForm form){
        MessageEntity message = modelMapper.map(form, MessageEntity.class);
        firebase.editMessage(form, findExistingMessage(type).get(0));
        return modelMapper.map(message, MessageDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public MessageDto findMessage(String type) {
        return modelMapper.map(findExistingMessage(type).get(0), MessageDto.class);
    }

    private List<MessageEntity> findExistingMessage(String type) {
        return firebase.findMessage(type);
    }
}
