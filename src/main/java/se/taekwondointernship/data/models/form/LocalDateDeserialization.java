package se.taekwondointernship.data.models.form;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateDeserialization extends JsonDeserializer<LocalDate> {
    @Override
    public LocalDate deserialize(JsonParser p, DeserializationContext context) throws IOException, JsonParseException {
        /*if (p.getText()=="" || p.getText().equalsIgnoreCase(null)) {
            return null;
        }*/
        DateTimeFormatter formatter= DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate=null;
        localDate=LocalDate.parse(p.getText(),formatter);
        return localDate;
    }

}
