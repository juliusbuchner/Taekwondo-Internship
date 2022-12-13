package se.taekwondointernship.data.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.taekwondointernship.data.exceptions.ResourceNotFoundException;
import se.taekwondointernship.data.models.dto.PassDto;
import se.taekwondointernship.data.models.dto.PersonDto;
import se.taekwondointernship.data.models.dto.PersonSmallDto;
import se.taekwondointernship.data.models.entity.Pass;
import se.taekwondointernship.data.models.entity.Person;
import se.taekwondointernship.data.models.form.PassForm;
import se.taekwondointernship.data.repository.PassRepository;

import java.io.*;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class PassServiceImplementation implements PassService {
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;
    private final JsonService jsonService=new JsonService();
    private  final PassRepository passRepository;
        private JSONArray jsonArray=new JSONArray();
  //  private JSONObject passList=new JSONObject();
    JSONObject jsonObject = new JSONObject();



    @Autowired
    public PassServiceImplementation(ModelMapper modelMapper,ObjectMapper objectMapper,PassRepository passRepository) {
        this.modelMapper = modelMapper;
        this.passRepository = passRepository;
        this.objectMapper=objectMapper;

    }

//    public PassServiceImplementation(){}
    LocalDate date=LocalDate.now();
    int weekOfYear=date.get(WeekFields.of(Locale.getDefault()).weekOfYear());

    String fileName="pass_"+date+".json";
    String directoryName="C:\\JSON\\"+"Week"+weekOfYear+"_"+date.getYear();
    File f=new File(directoryName);
    boolean mkdir=f.mkdir();
    final String PATH =directoryName+"\\"+fileName;

    final String personPATH="C:\\JSON\\PersonList.json";


    //Reading the Person file

    public List<PersonSmallDto> readPersonFile(String personPATH){

        JSONArray persons= jsonService.getJson(personPATH);
        List<Person> personList= convertJsonArrayToPersonList(persons);
        List<PersonSmallDto> personSmallDtos= new ArrayList<>();
        PersonSmallDto personSmallDto=new PersonSmallDto();
        for (Person person: personList) {
           personSmallDto.setPersonId( person.getPersonId());
           personSmallDto.setFirstName(person.getFirstName());
           personSmallDto.setLastName(person.getLastName());
           personSmallDto.setPassCount(person.getPassCount());
           personSmallDto.setLocked(person.isLocked());
           personSmallDtos.add(personSmallDto);
        }
        return modelMapper.map(personSmallDtos, new TypeToken<List<PersonSmallDto>>(){}.getType());





    }
    private List<Person> convertJsonArrayToPersonList(JSONArray persons){
        return (List<Person>) persons.stream()
                .map(jsonObject -> {
                    try {

                        String json = objectMapper.writeValueAsString(((JSONObject) jsonObject).get("participant"));
                        return objectMapper.readValue(json, Person.class);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .collect(Collectors.toList());
    }




    //Get Data from PostMan
    //Get values
    //Store values into Json
    @Override
    @Transactional
    public PassDto create(PassForm form) {

        //Checking count variable and lock variable

        // fetching the specific person
          List<PersonSmallDto> list= readPersonFile(personPATH);
          PersonSmallDto foundPerson= list.stream().filter(personDto -> personDto.getPersonId().equals(form.getPersonSmallForm().getPersonId())
                                                             && personDto.getFirstName().equalsIgnoreCase(form.getPersonSmallForm().getFirstName())
          && personDto.getLastName().equalsIgnoreCase(form.getPersonSmallForm().getLastName())
          ).findAny().orElseThrow(() -> new ResourceNotFoundException("Person not found with id and name"));

          if(foundPerson.getPassCount()==3){
              foundPerson.setLocked(true);
              System.out.println("Person is locked, contact admin");
          }else{
        Pass pass= passRepository.save(modelMapper.map(form,Pass.class));
        System.out.println(pass);
          jsonArray= jsonService.getJson(PATH);

            jsonObject.put("id",pass.getPassId());
            jsonObject.put("firstName", pass.getFirstName());
            jsonObject.put("lastName", pass.getLastName());
            jsonObject.put("parentName", pass.getParentName());
            jsonObject.put("parentPhoneNumber", pass.getParentPhoneNumber());
            jsonObject.put("className", pass.getClassName());
            jsonObject.put("date",pass.getDate().toString());
            jsonObject.put("age",pass.getAge());
            //passList.put("passList",jsonObject);
            jsonArray.add(jsonObject);

        jsonService.saveJson(jsonArray, PATH);
            Pass saved=  modelMapper.map(jsonService.getJson(PATH), Pass.class);
            foundPerson.setPassCount(foundPerson.getPassCount()+1);
        return modelMapper.map(pass,PassDto.class);
          }
        return null;
    }


    @Transactional
    @Override
    public List<PassDto> findAll() throws IOException {
             JSONArray foundAll=jsonService.getJson(PATH);

        List<Pass> passList= convertJsonArrayToPassList(foundAll);
        return modelMapper.map(passList, new TypeToken<List<PassDto>>(){}.getType());

    }

    private List<Pass> convertJsonArrayToPassList(JSONArray jsonArray){
     return (List<Pass>) jsonArray.stream()
                .map(jsonObject -> {
                    try {
                        String json = objectMapper.writeValueAsString(jsonObject);
                        return objectMapper.readValue(json, Pass.class);
                    } catch (JsonProcessingException e) {
                       e.printStackTrace();
                    }
                    return null;
                })
                .collect(Collectors.toList());
    }


@Transactional(readOnly = true)
    @Override
    public PassDto findByName(String firstName, String lastName) {
        if(firstName==null) throw new IllegalArgumentException("First Name is null");
        if(lastName==null) throw new IllegalArgumentException("Last Name is null");
        List<PassDto> found= null;
         try {
             found = findAll();
          } catch (IOException e) {
              e.printStackTrace();
         }
        PassDto foundByName=found.stream()
            .filter(passDto -> passDto.getFirstName().equalsIgnoreCase(firstName) && passDto.getLastName().equalsIgnoreCase(lastName))
            .findFirst()
            .orElseThrow( () -> new ResourceNotFoundException("Name not found"));
            return foundByName;
    }

    @Transactional(readOnly = true)
    @Override
    public List<PassDto> findAllByClassName(String className) {
        if(className==null) throw new IllegalArgumentException("Class Name is null");
        List<PassDto> found= null;
        try{
            found=findAll();
        }catch (IOException e){
            e.printStackTrace();
        }
        List<PassDto> foundAllByClassName= found.stream()
                .filter(passDto -> passDto.getClassName().equalsIgnoreCase(className)).collect(Collectors.toList());
        return foundAllByClassName;
    }
    @Transactional(readOnly = true)
    @Override
    public PassDto findByNameAndClassName(String firstName, String lastName, String className) {
        if(firstName==null) throw new IllegalArgumentException("First Name is null");
        if(lastName==null) throw new IllegalArgumentException("Last Name is null");
        if(className==null) throw new IllegalArgumentException("Class Name is null");
        List<PassDto> found=null;
        try{
            found=findAll();
        }catch(IOException e){
            e.printStackTrace();
        }
        PassDto foundByNameAndClassName= found.stream()
                .filter(passDto -> passDto.getFirstName().equalsIgnoreCase(firstName)&& passDto.getLastName().equalsIgnoreCase(lastName) && passDto.getClassName().equalsIgnoreCase(className))
                .findFirst().orElseThrow(()->new ResourceNotFoundException("Cannot find by FirstName, Last Name and ClassName"));
                return foundByNameAndClassName;
    }

}
