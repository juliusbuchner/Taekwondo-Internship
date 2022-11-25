package se.taekwondointernship.data.service;

import se.taekwondointernship.model.dto.PassForm;

import java.util.List;

public interface PassService {
        List<PassForm> findAll();
        PassForm findByFirstName();
}
