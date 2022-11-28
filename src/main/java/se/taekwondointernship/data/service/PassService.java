package se.taekwondointernship.data.service;

import se.taekwondointernship.data.models.dto.PassDto;
import se.taekwondointernship.data.models.form.PassForm;

import java.util.List;

public interface PassService {
        PassDto create(PassForm form);
        List<PassDto> findAll();
        PassDto findByName(String firstName, String lastName);
}
