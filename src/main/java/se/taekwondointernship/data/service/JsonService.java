package se.taekwondointernship.data.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import se.taekwondointernship.data.models.dto.PassDto;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.Locale;


public class JsonService {
    private ObjectMapper objectMapper;
    public JsonService() {
        SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
        DateTimeFormatter dtf=  DateTimeFormatter.ofPattern("HH:mm");

       ObjectMapper objectMapper= new ObjectMapper().registerModule(new JavaTimeModule());
       objectMapper.setDateFormat(sdf);
       //objectMapper.setTimeZone(dtf);
           this.objectMapper = objectMapper;
   }

    public void saveJson(Object object, String PATH){
        //Get data
        //Write into JSON file

        try {

            objectMapper.writeValue(new File(PATH), object);

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public JSONArray getJson(String PATH){
        try {
            return objectMapper.readValue(new File(PATH), JSONArray.class);
        } catch (FileNotFoundException e){
            return new JSONArray();
        }
        catch (IOException e) {
            return new JSONArray();
        }
    }


}
