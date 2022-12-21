package se.taekwondointernship.data.service;

import se.taekwondointernship.data.models.dto.PersonDto;
import se.taekwondointernship.data.models.form.PersonForm;

import java.util.List;

public interface PersonService {
    PersonDto create(PersonForm form);
    List<PersonDto> findAll();
    void delete(Integer id);
    PersonDto findById(Integer id);
}
