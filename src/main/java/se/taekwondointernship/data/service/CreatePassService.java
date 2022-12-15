package se.taekwondointernship.data.service;

import se.taekwondointernship.data.models.dto.CreatePassDto;
import se.taekwondointernship.data.models.form.CreatePassForm;

import java.time.LocalDate;
import java.util.List;

public interface CreatePassService {

    CreatePassDto create(CreatePassForm form);
    List<CreatePassDto> findAll();
    List<CreatePassDto> findByClassName(String className);
    List<CreatePassDto> findByDate(LocalDate date);
    CreatePassDto update(CreatePassDto dto );
    void delete(int id);
    CreatePassDto findById(Integer id);

}
