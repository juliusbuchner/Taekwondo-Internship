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
import java.util.Objects;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
//import se.taekwondointernship.data.storage.Firebase;

@Service
public class PersonServiceImpl implements PersonService{
    PersonRepository personRepository;
    EmailServiceImpl emailService;
    MessageServiceImpl messageService;
    ModelMapper modelMapper;

//    Firebase firebase = new Firebase();

    JSONParser jsonParser = new JSONParser();
    JSONArray memberList = new JSONArray();
    public PersonServiceImpl(PersonRepository personRepository, EmailServiceImpl emailService,
                             MessageServiceImpl messageService, ModelMapper modelMapper) {
        this.personRepository = personRepository;
        this.emailService = emailService;
        this.messageService = messageService;
        this.modelMapper = modelMapper;
    }
    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public PersonDto create(PersonForm form) {
        Person person = personRepository.save(modelMapper.map(form, Person.class));
        memberList.add(parsePerson(person));
        emailService.sendAttach(person.getEmail());
        printJSON(memberList);
//        firebase.uploadMember(person);
        return modelMapper.map(person, PersonDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonDto> findAll() {
        List<Person> personList = getExistingMembers();
        List<PersonDto> personDtoList = new ArrayList<>();
        personList.forEach(mbr -> personDtoList.add(modelMapper.map(mbr, PersonDto.class)));
        return personDtoList;
    }

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
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

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public PersonDto findById(Integer id) {
        List<Person> personList = new ArrayList<>();
        Person person = new Person();
        try (FileReader reader = new FileReader("members.json")) {
            Object obj = jsonParser.parse(reader);
            JSONArray membersList = (JSONArray) obj;
            membersList.forEach(mbr -> personList.add(parseJsonPerson((JSONObject) mbr)));
            for (Person value : personList) {
                if (Objects.equals(value.getPersonId(), id)) {
                    person = value;
                }
            }
        } catch (IOException | ParseException e){
            e.printStackTrace();
        }
        return modelMapper.map(person, PersonDto.class);
    }

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public PersonDto unlock(Integer id){
        List<Person> personList = new ArrayList<>();
        PersonDto personDto = findById(id);
        Person person = modelMapper.map(personDto, Person.class);
        person.setLocked(false);
        personList.add(person);
        delete(id);
        personList.forEach(mbr -> memberList.add(parsePerson(mbr)));
        printJSON(memberList);
        return modelMapper.map(person, PersonDto.class);
    }

    @SuppressWarnings("unchecked")
    private List<Person> getExistingMembers() {
        List<Person> personList = new ArrayList<>();
        try (FileReader reader = new FileReader("members.json")) {
            Object obj = jsonParser.parse(reader);
            JSONArray membersList = (JSONArray) obj;
            membersList.forEach(mbr -> personList.add(parseJsonPerson((JSONObject) mbr)));
        } catch (IOException | ParseException e){
            e.printStackTrace();
        }
        return personList;
    }

    private Person parseJsonPerson(JSONObject objectPerson){
        Integer personId = Integer.parseInt(String.valueOf(objectPerson.get("personId")));
        String firstName = (String) objectPerson.get("firstName");
        String lastName = (String) objectPerson.get("lastName");
        String phoneNumber = (String) objectPerson.get("phoneNumber");
        String email = (String) objectPerson.get("email");
        String socialSecurityNumber = (String) objectPerson.get("socialSecurityNumber");
        String age = (String) objectPerson.get("age");
        String parentName = (String) objectPerson.get("parentName");
        String parentNumber = (String) objectPerson.get("parentNumber");
        boolean permissionPhoto = Boolean.parseBoolean(String.valueOf(objectPerson.get("permissionPhoto")));
        Integer passCount = Integer.parseInt(String.valueOf(objectPerson.get("passCount")));
        boolean locked = Boolean.parseBoolean(String.valueOf(objectPerson.get("locked")));
        return new Person(personId, firstName, lastName, phoneNumber, parentName, parentNumber, email, socialSecurityNumber,age, permissionPhoto, passCount, locked);
    }
    @SuppressWarnings("unchecked")
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
        jsonPersonDetails.put("passCount", person.getPassCount());
        jsonPersonDetails.put("locked", person.isLocked());
        return jsonPersonDetails;
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
