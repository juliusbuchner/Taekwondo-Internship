package se.taekwondointernship.data.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.taekwondointernship.data.models.dto.PersonDto;
import se.taekwondointernship.data.models.entity.Person;
import se.taekwondointernship.data.models.form.PersonForm;
import se.taekwondointernship.data.repository.PersonRepository;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import se.taekwondointernship.data.storage.Firebase;

@Service
public class PersonServiceImpl implements PersonService{
    PersonRepository personRepository;
    EmailServiceImpl emailService;
    MessageServiceImpl messageService;
    ModelMapper modelMapper;

    Firebase firebase = new Firebase();
    public PersonServiceImpl(PersonRepository personRepository, EmailServiceImpl emailService,
                             MessageServiceImpl messageService, ModelMapper modelMapper) {
        this.personRepository = personRepository;
        this.emailService = emailService;
        this.messageService = messageService;
        this.modelMapper = modelMapper;
    }
    @Override
    @Transactional
    public PersonDto create(PersonForm form) {
        Person person = modelMapper.map(form, Person.class);
        person.setAge(setAge(person.getSocialSecurityNumber()));
        firebase.uploadMember(person);
        personRepository.save(person);
        return modelMapper.map(person, PersonDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonDto> findAll() {
        List<Person> personList = getExistingMembers();
        List<PersonDto> personDtoList = new ArrayList<>();
        for (int i = 0; i<personList.size();i++) {
            Person person = personList.get(i);
            PersonDto personDto = modelMapper.map(person, PersonDto.class);
            personDtoList.add(personDto);
        }
            return personDtoList;
    }

    @Override
    @Transactional
    public void delete(Integer id){
        firebase.delete("members", id);
    }

    @Override
    @Transactional
    public PersonDto findById(Integer id) {
        List<Person> personList = firebase.findMember();
        Person person = new Person();
        for (Person value : personList) {
            if (Objects.equals(value.getPersonId(), id)) {
                person = value;
            }
        }
        return modelMapper.map(person, PersonDto.class);
    }

    @Override
    @Transactional
    public PersonDto unlock(Integer id){
        return changeLocked(id, false);
    }

    @Override
    @Transactional
    public PersonDto lock(Integer id){
        return changeLocked(id, true);
    }

    private PersonDto changeLocked(Integer id, Boolean status){
        PersonDto personDto = findById(id);
        Person person = modelMapper.map(personDto, Person.class);
        if (status){
            firebase.lockPerson(person);
        } else {
            firebase.unlockPerson(person);
        }
        return modelMapper.map(person, PersonDto.class);
    }

    private List<Person> getExistingMembers() {
        return firebase.findMember();
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