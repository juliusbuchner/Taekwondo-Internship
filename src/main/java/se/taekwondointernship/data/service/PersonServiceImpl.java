package se.taekwondointernship.data.service;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.taekwondointernship.data.models.dto.PersonDto;
import se.taekwondointernship.data.models.entity.Person;
import se.taekwondointernship.data.models.form.PersonForm;
import se.taekwondointernship.data.repository.PersonRepository;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@Service
public class PersonServiceImpl implements PersonService{
    PersonRepository personRepository;
    ModelMapper modelMapper;
    JSONParser jsonParser = new JSONParser();

    JSONArray memberList = new JSONArray();
    public PersonServiceImpl(PersonRepository personRepository, ModelMapper modelMapper){
        this.personRepository = personRepository;
        this.modelMapper = modelMapper;
    }
    @Override
    @Transactional
    public PersonDto create(PersonForm form) {
        Person person = personRepository.save(modelMapper.map(form, Person.class));
        memberList.add(parsePerson(person));
        printJSON(memberList);
        return modelMapper.map(person, PersonDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonDto> findAll() {
        List<Person> personList = new ArrayList<>();
        List<PersonDto> personDtoList = new ArrayList<>();
        try (FileReader reader = new FileReader("members.json")) {
            Object obj = jsonParser.parse(reader);
            JSONArray membersList = (JSONArray) obj;
            membersList.forEach(mbr -> personList.add(parseJsonPerson((JSONObject) mbr)));
            personList.forEach((person -> personDtoList.add(modelMapper.map(person, PersonDto.class))));
        } catch (IOException | ParseException e){
            e.printStackTrace();
        }
        return personDtoList;
    }

    @Override
    @Transactional
    public void delete(Integer id){
        List<Person> personList = new ArrayList<>();
        try(FileReader reader = new FileReader("members.json")) {
            Object obj = jsonParser.parse(reader);
            memberList = (JSONArray) obj;
            memberList.forEach(mbr -> personList.add(parseJsonPerson((JSONObject) mbr)));
            memberList.clear();
            printJSON(memberList);
            personList.removeIf(person -> person.getPersonId().equals(id));
            personList.forEach(mbr -> memberList.add(parsePerson(mbr)));
            printJSON(memberList);
        } catch (IOException | ParseException e){
            e.printStackTrace();
        }
    }


    private Person parseJsonPerson(JSONObject objectPerson){
        JSONObject jsonPerson = (JSONObject) objectPerson.get("participant");
        Integer personId = Integer.parseInt(String.valueOf(jsonPerson.get("personId")));
        String firstName = (String) jsonPerson.get("firstName");
        String lastName = (String) jsonPerson.get("lastName");
        String phoneNumber = (String) jsonPerson.get("phoneNumber");
        String email = (String) jsonPerson.get("email");
        String socialSecurityNumber = (String) jsonPerson.get("socialSecurityNumber");
        String age = (String) jsonPerson.get("age");
        String parentName = (String) jsonPerson.get("parentName");
        String parentNumber = (String) jsonPerson.get("parentNumber");
        boolean permissionPhoto = Boolean.parseBoolean(String.valueOf(jsonPerson.get("permissionPhoto")));
        return new Person(personId, firstName, lastName, phoneNumber, parentName, parentNumber, email, socialSecurityNumber,age, permissionPhoto);
    }
    private JSONObject parsePerson(Person person){
        JSONObject jsonPersonDetails = new JSONObject();
        String age = setAge(person.getSocialSecurityNumber());
        jsonPersonDetails.put("personId", person.getPersonId());
        jsonPersonDetails.put("firstName", person.getFirstName());
        jsonPersonDetails.put("lastName", person.getLastName());
        jsonPersonDetails.put("phoneNumber", person.getPhoneNumber());
        jsonPersonDetails.put("email", person.getEmail());
        jsonPersonDetails.put("socialSecurityNumber", person.getSocialSecurityNumber());
        jsonPersonDetails.put("age", age);
        jsonPersonDetails.put("parentName", person.getParentName());
        jsonPersonDetails.put("parentNumber", person.getParentNumber());
        jsonPersonDetails.put("permissionPhoto", person.isPermissionPhoto());
        JSONObject jsonPerson = new JSONObject();
        jsonPerson.put("participant", jsonPersonDetails);
        return jsonPerson;
    }
    private void printJSON(JSONArray jsonArray){
        try (FileWriter file = new FileWriter("members.json")){
            file.write(jsonArray.toJSONString());
            file.flush();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    @Transactional
    public String setAge(String socialSecurityNumber){
        StringBuilder sb = new StringBuilder(socialSecurityNumber.substring(0,6));
        String socialNumberYear = socialSecurityNumber.substring(0,2);
        int socialNumberYearCheck = Integer.parseInt(socialNumberYear);
        if (socialNumberYearCheck<LocalDate.now().getYear()%100){
            sb.insert(0, "20");
        } else {
            sb.insert(0, "19");
        }
        sb.insert(4, "-");
        sb.insert(7, "-");
        return String.valueOf(Period.between(LocalDate.parse(sb.toString()), LocalDate.now()).getYears());
    }
}
