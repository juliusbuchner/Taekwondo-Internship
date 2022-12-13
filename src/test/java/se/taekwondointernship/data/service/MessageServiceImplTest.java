package se.taekwondointernship.data.service;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import se.taekwondointernship.data.models.entity.Message;
class MessageServiceImplTest {

    @Test
    void create() {
        try {
            final Message message = new Message(1, "Alla barn sover.");
            JSONObject jsonMessage = new JSONObject();
            jsonMessage.put("messageId", message.getMessageId());
            jsonMessage.put("messageContent", message.getMessageContent());
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
            final Message messageStart = new Message(1, "Nu ska alla sova.");
            JSONObject jsonMessage = new JSONObject();
            jsonMessage.put("messageId", messageStart.getMessageId());
            jsonMessage.put("messageContent", messageStart.getMessageContent());
            JSONAssert.assertEquals("{messageId:1}", String.valueOf(jsonMessage), false);
            JSONAssert.assertEquals("{messageContent:\"Nu ska alla sova.\"}", String.valueOf(jsonMessage), false);
            JSONAssert.assertEquals("{messageId:1, messageContent:\"Nu ska alla sova.\"}", String.valueOf(jsonMessage), true);

            final Message messageEnd = new Message(1, "Alla barn sover.");
            jsonMessage.replace("messageContent", messageEnd.getMessageContent());
            JSONAssert.assertEquals("{messageId:1}", String.valueOf(jsonMessage), false);
            JSONAssert.assertEquals("{messageContent:\"Alla barn sover.\"}", String.valueOf(jsonMessage), false);
            JSONAssert.assertEquals("{messageId:1, messageContent:\"Alla barn sover.\"}", String.valueOf(jsonMessage), true);
        } catch (JSONException e){
            throw new RuntimeException(e);
        }
    }
}