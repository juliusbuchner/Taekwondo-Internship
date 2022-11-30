package se.taekwondointernship.data.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import se.taekwondointernship.data.models.dto.PassDto;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class JsonService {
   private static final String PATH ="C:\\JSON\\pass.json";
   private ObjectMapper objectMapper = new ObjectMapper();

   public JsonService() {

   }

    public void saveJson(Object object){
        //Get data
        //Write into JSON file

        try {

            objectMapper.writeValue(new File(PATH), object);

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public JSONArray getJson(){
        try {
            return objectMapper.readValue(new File(PATH), JSONArray.class);
        } catch (FileNotFoundException e){
            return new JSONArray();
        }
        catch (IOException e) {
            return new JSONArray();
        }
    }

 /*   public JSONObject getJson(){
        try {
            return objectMapper.readValue(new File(PATH), JSONArray.class);
        } catch (FileNotFoundException e){
            return new JSONArray();
        }
        catch (IOException e) {
            return new JSONArray();
        }
    }*/

}
