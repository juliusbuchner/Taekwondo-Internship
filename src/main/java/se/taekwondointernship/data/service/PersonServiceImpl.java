package se.taekwondointernship.data.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.taekwondointernship.data.models.dto.PersonDto;
import se.taekwondointernship.data.models.entity.Person;
import se.taekwondointernship.data.models.form.PersonForm;
import se.taekwondointernship.data.exceptions.ResourceNotFoundException;
import se.taekwondointernship.data.repository.PassRepository;
import se.taekwondointernship.data.repository.PersonRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService{
    PersonRepository personRepository;
    PassRepository passRepository;
    ModelMapper modelMapper;
    public PersonServiceImpl(PersonRepository personRepository,PassRepository passRepository, ModelMapper modelMapper){
        this.personRepository = personRepository;
        this.passRepository = passRepository;
        this.modelMapper = modelMapper;
    }
    @Override
    @Transactional
    public PersonDto create(PersonForm form) {
        Person person = personRepository.save(modelMapper.map(form, Person.class));
        return modelMapper.map(person, PersonDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonDto> findAll() {
        List<Person> personList = personRepository.findAll();
        List<PersonDto> personDtoList = new ArrayList<>();
        personList.forEach((person -> personDtoList.add(modelMapper.map(person, PersonDto.class))));
        return personDtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public PersonDto findByName(String firstName, String lastName) {
        if (firstName == null && lastName == null) throw new IllegalArgumentException("Name can not be null");
        Person foundByName = personRepository.findByName(firstName, lastName).orElseThrow(
                () -> new ResourceNotFoundException("Participant can not be Found."));
        return modelMapper.map(foundByName, PersonDto.class);
    }
}
