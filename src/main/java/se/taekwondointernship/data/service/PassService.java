package se.taekwondointernship.data.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import se.taekwondointernship.data.models.dto.PassDto;
import se.taekwondointernship.data.models.form.PassForm;

import java.io.IOException;
import java.util.List;

public interface PassService {
        PassDto create(PassForm form);
        List<PassDto> findAll() throws IOException;
        PassDto findByName(String firstName, String lastName);
        List<PassDto> findAllByClassName(String className);
        PassDto findByNameAndClassName(String firstName, String lastName, String className);
}
