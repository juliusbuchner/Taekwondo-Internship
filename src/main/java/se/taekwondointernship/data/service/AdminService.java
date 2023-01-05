package se.taekwondointernship.data.service;

import org.json.simple.parser.ParseException;
import org.springframework.transaction.annotation.Transactional;
import se.taekwondointernship.data.models.dto.AdminDto;
import se.taekwondointernship.data.models.form.AdminForm;

import java.io.IOException;

public interface AdminService {
    AdminDto create(AdminForm form);

    AdminDto editPassword(AdminForm form) throws IOException, ParseException;

    String getPassword();

    String getUsername();

    @Transactional
    AdminDto findByUsername(String username);

    AdminDto logIn();

    AdminDto logOut();

    String sendReset(String username);
}
