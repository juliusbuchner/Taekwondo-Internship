package se.taekwondointernship.data.service;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import se.taekwondointernship.data.models.entity.MessageEntity;
class MessageEntityServiceImplTest {

    @Test
    void create() {
        try {
            final MessageEntity messageEntity = new MessageEntity(1, "Alla barn sover.");
            JSONObject jsonMessage = new JSONObject();
            jsonMessage.put("messageId", messageEntity.getMessageId());
            jsonMessage.put("messageContent", messageEntity.getMessageContent());
            JSONAssert.assertEquals("{messageId:1}", String.valueOf(jsonMessage), false);
            JSONAssert.assertEquals("{messageContent:\"Alla barn sover.\"}", String.valueOf(jsonMessage), false);
            JSONAssert.assertEquals("{messageId:1, messageContent:\"Alla barn sover.\"}", String.valueOf(jsonMessage), true);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void edit() {
        try {
            final MessageEntity messageEntityStart = new MessageEntity(1, "Nu ska alla sova.");
            JSONObject jsonMessage = new JSONObject();
            jsonMessage.put("messageId", messageEntityStart.getMessageId());
            jsonMessage.put("messageContent", messageEntityStart.getMessageContent());
            JSONAssert.assertEquals("{messageId:1}", String.valueOf(jsonMessage), false);
            JSONAssert.assertEquals("{messageContent:\"Nu ska alla sova.\"}", String.valueOf(jsonMessage), false);
            JSONAssert.assertEquals("{messageId:1, messageContent:\"Nu ska alla sova.\"}", String.valueOf(jsonMessage), true);

            final MessageEntity messageEntityEnd = new MessageEntity(1, "Alla barn sover.");
            jsonMessage.replace("messageContent", messageEntityEnd.getMessageContent());
            JSONAssert.assertEquals("{messageId:1}", String.valueOf(jsonMessage), false);
            JSONAssert.assertEquals("{messageContent:\"Alla barn sover.\"}", String.valueOf(jsonMessage), false);
            JSONAssert.assertEquals("{messageId:1, messageContent:\"Alla barn sover.\"}", String.valueOf(jsonMessage), true);
        } catch (JSONException e){
            throw new RuntimeException(e);
        }
    }
}