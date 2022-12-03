package se.taekwondointernship.data.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import se.taekwondointernship.data.models.dto.PassDto;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class JsonService {
    LocalDate date=LocalDate.now();
    int weekOfYear=date.get(WeekFields.of(Locale.getDefault()).weekOfYear());

    private  String fileName="pass_"+date+".json";
    private String directoryName="C:\\JSON\\"+"Week"+weekOfYear+"_"+date.getYear();
    File f=new File(directoryName);
    boolean mkdir=f.mkdir();
   private final String PATH =directoryName+"\\"+fileName;
   private ObjectMapper objectMapper = new ObjectMapper();

   public JsonService() {

   }

    public void saveJson(Object object){
        //Get data
        //Write into JSON file

       // System.out.println(weekOfYear);

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
