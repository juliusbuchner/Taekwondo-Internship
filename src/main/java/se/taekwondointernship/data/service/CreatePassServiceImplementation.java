package se.taekwondointernship.data.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.taekwondointernship.data.models.dto.CreatePassDto;
import se.taekwondointernship.data.models.entity.CreatePass;
import se.taekwondointernship.data.models.form.CreatePassForm;
import se.taekwondointernship.data.repository.CreatePassRepository;

import java.io.File;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class CreatePassServiceImplementation implements  CreatePassService{
    private final CreatePassRepository createPassRepository;
    private final JsonService jsonService=new JsonService();
    private ModelMapper modelMapper;
    private ObjectMapper objectMapper;
    private JSONArray jsonArray=new JSONArray();
    private JSONObject jsonObject=new JSONObject();




    LocalDate date=LocalDate.now();
    Month month= date.getMonth();
    String fileName="created_pass"+".json";
    String directoryName="C:\\JSON\\"+month+"_"+date.getYear();
    File f=new File(directoryName);

    boolean mkdir=f.mkdir();
    final String PATH =directoryName+"\\"+fileName;


    @Autowired
    public CreatePassServiceImplementation(CreatePassRepository createPassRepository, ModelMapper modelMapper, ObjectMapper objectMapper) {
        this.createPassRepository = createPassRepository;
        this.modelMapper = modelMapper;
        this.objectMapper = objectMapper;
    }

    @Transactional
    @Override
    public CreatePassDto create(CreatePassForm form) {
         CreatePass createPass= createPassRepository.save(modelMapper.map(form,CreatePass.class));

        System.out.println(createPass);
        jsonArray=jsonService.getJson(PATH);

        jsonObject.put("id", createPass.getId());
        jsonObject.put("className",createPass.getClassName());
        jsonObject.put("date", createPass.getDate());
        jsonObject.put("startTime",createPass.getStartTime());
        jsonObject.put("duration", createPass.getDuration());
        jsonObject.put("extraPass",createPass.isExtraPass());
        System.out.println(jsonObject);

        jsonArray.add(jsonObject);
        jsonService.saveJson(jsonArray,PATH);

        return modelMapper.map(jsonObject,CreatePassDto.class);
    }
    private List<CreatePass> convertJsonArrayToList(JSONArray jsonArray){
        return  (List<CreatePass>) jsonArray.stream().map(jsonObject -> {
            try{
                String json= objectMapper.writeValueAsString(jsonObject);
                return objectMapper.readValue(json,CreatePass.class);
            }  catch (JsonProcessingException e) {
                    e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());
    }

    @Override
    public List<CreatePassDto> findAll() {
        JSONArray foundAll= jsonService.getJson(PATH);
        List<CreatePass> createPassList=convertJsonArrayToList(foundAll);
        return modelMapper.map(createPassList,new TypeToken<List<CreatePassDto>>(){}.getType());
    }

    @Override
    public List<CreatePassDto> findByClassName(String className) {
        if(className==null) throw new IllegalArgumentException("Class Name is null");
        List<CreatePassDto> found =findAll();
        List<CreatePassDto> foundByClassName= found.stream()
                .filter(createPassDto -> createPassDto.getClassName().equalsIgnoreCase(className))
                .collect(Collectors.toList());
        return foundByClassName;
    }

    @Override
    public List<CreatePassDto> findByDate(LocalDate date) {
        List<CreatePassDto> found= findAll();
        List<CreatePassDto> foundByDate= found.stream().filter(createPassDto -> createPassDto.getDate().equals(date)).collect(Collectors.toList());
        return foundByDate;
    }
}
