package se.taekwondointernship.data.service;

import se.taekwondointernship.data.models.form.PassForm;

import java.util.List;

public interface PassService {
        //PassForm create(String name);
        List<PassForm> findAll();
        PassForm findByName(String firstName, String lastName);
}
