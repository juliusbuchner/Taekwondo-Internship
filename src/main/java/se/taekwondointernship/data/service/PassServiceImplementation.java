package se.taekwondointernship.data.service;

import aj.org.objectweb.asm.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.taekwondointernship.data.exceptions.ResourceNotFoundException;
import se.taekwondointernship.data.models.dto.PassDto;
import se.taekwondointernship.data.models.entity.Pass;
import se.taekwondointernship.data.models.form.PassForm;
import se.taekwondointernship.data.repository.PassRepository;

import java.io.*;
import java.util.List;

@Service
public class PassServiceImplementation implements PassService {
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;
    private final PassRepository passRepository;


    @Autowired
    public PassServiceImplementation(ModelMapper modelMapper, ObjectMapper objectMapper,PassRepository passRepository) {
        this.modelMapper = modelMapper;
        this.objectMapper=objectMapper;
        this.passRepository = passRepository;
    }

  //  @Override
   // public PassDto create(PassForm form) {
     //   Pass pass=modelMapper.map(form, Pass.class);
       /* String passJsonString = this.getJsonStringFromObject(pass);
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("className",pass.getClassName());
        jsonObject.put("firstName",pass.getFirstName());
        jsonObject.put("lastName",pass.getLastName());
        jsonObject.put("parentPhoneNumber",pass.getParentPhoneNumber());
        jsonObject.put("Date",pass.getDate());
        try(FileWriter file=new FileWriter("C:\\JSON\\Pass.json")){
            file.write(jsonObject.toJSONString());
            file.flush();
            return "File Created";
        }catch(IOException e){
            e.printStackTrace();
            return "File Not created";

        } */
       // Pass savedPass=passRepository.save(pass);
        //return modelMapper.map(savedPass, PassDto.class);

  //  }


    //Get Data from PostMan
    //Get values
    //Store values into Json
    @Override
    @Transactional

    public PassDto create(PassForm form) {
        Pass pass= passRepository.save(modelMapper.map(form,Pass.class));
        System.out.println(pass);
        JSONObject jsonObject = new JSONObject();


        PassDto returnValue;
        try {
            jsonObject.put("firstName", pass.getFirstName());
            jsonObject.put("lastName", form.getLastName());
            jsonObject.put("parentName", form.getParentName());
            jsonObject.put("parentPhoneNumber", form.getParentPhoneNumber());
            jsonObject.put("className", form.getClassName());

            FileWriter file = new FileWriter("C:\\JSON\\pass.json");
            file.write(jsonObject.toJSONString());
            returnValue = modelMapper.map(file, PassDto.class);
            file.flush();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return returnValue;

    }




        @Transactional
    @Override
    public List<PassDto> findAll() {
        List<Pass> foundAll=passRepository.findAll();
        List<PassDto> ListOfPersons=modelMapper.map(foundAll,new TypeToken<List<PassDto>>(){}.getType());
        return ListOfPersons;
    }
@Transactional
    @Override
    public PassDto findByName(String firstName, String lastName) {
        if(firstName==null) throw new IllegalArgumentException("First Name is null");
        Pass foundName = passRepository.findByName(firstName,lastName).orElseThrow(() -> new ResourceNotFoundException("Name not found"));
        return modelMapper.map(foundName,PassDto.class);
    }

    private String getJsonStringFromObject(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception ex) {

            return null;
        }
    }
}
