package se.taekwondointernship.data.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import se.taekwondointernship.data.models.dto.CreatePassDto;
import se.taekwondointernship.data.models.dto.PassDto;
import se.taekwondointernship.data.models.form.CreatePassForm;
import se.taekwondointernship.data.repository.CreatePassRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestEntityManager
@Transactional
@DirtiesContext

class CreatePassServiceImplementationTest {

    @Autowired
    TestEntityManager testEntityManager;
    @Autowired
    CreatePassRepository createPassRepository;
    @Autowired
    ModelMapper modelMapper;
    ObjectMapper objectMapper=new ObjectMapper();
    @Autowired
    CreatePassService createPassService;
    CreatePassForm form=new CreatePassForm("TestClass", LocalDate.parse("2022-12-18"), LocalTime.parse("15:30"),60,false);
    CreatePassForm form2=new CreatePassForm("TestClass2", null, LocalTime.parse("15:30"),60,true);

    @Test
    void create() {
        CreatePassDto testCreatePass= createPassService.create(form);
        CreatePassDto testCreatePassWithDateNull= createPassService.create(form2);
        testEntityManager.flush();
        List<CreatePassDto> found= createPassService.findAll();
        assertEquals(2,found.size());
        CreatePassDto foundCreatePass=found.get(0);
        assertEquals(form.getClassName(),foundCreatePass.getClassName());
        assertEquals(form.getDate(),foundCreatePass.getDate());
        assertEquals(form.getStartTime(),foundCreatePass.getStartTime());
        assertEquals(form.getDuration(),foundCreatePass.getDuration());
        assertEquals(form.isExtraPass(),foundCreatePass.isExtraPass());

        CreatePassDto foundCreatePassWithDateNull= found.get(1);
        assertEquals(form2.getClassName(),foundCreatePassWithDateNull.getClassName());
        assertEquals(form2.getDate(),foundCreatePassWithDateNull.getDate());
        assertEquals(form2.getStartTime(),foundCreatePassWithDateNull.getStartTime());
        assertEquals(form2.getDuration(),foundCreatePassWithDateNull.getDuration());
        assertEquals(form2.isExtraPass(),foundCreatePassWithDateNull.isExtraPass());

    }

    @Test
    void findAll() {


    }

    @Test
    void findByClassName() {
        List<CreatePassDto> foundByClassName= createPassService.findByClassName(form.getClassName());
        assertEquals(1,foundByClassName.size());
        CreatePassDto createPassDto= foundByClassName.get(0);
        assertEquals(form.getClassName(),createPassDto.getClassName());
        assertEquals(form.getDate(),createPassDto.getDate());
        assertEquals(form.getStartTime(),createPassDto.getStartTime());
        assertEquals(form.getDuration(),createPassDto.getDuration());
        assertEquals(form.isExtraPass(),createPassDto.isExtraPass());

    }

    @Test
    void findByDate() {
        List<CreatePassDto> foundByDate = createPassService.findByDate(LocalDate.parse("2022-12-18"));
        assertEquals(1,foundByDate.size());
        CreatePassDto createPassDto=foundByDate.get(0);
        assertEquals(form.getClassName(),createPassDto.getClassName());
        assertEquals(form.getDate(), createPassDto.getDate());
        assertEquals(form.getStartTime(),createPassDto.getStartTime());
        assertEquals(form.getDuration(),createPassDto.getDuration());
        assertEquals(form.isExtraPass(),createPassDto.isExtraPass());
    }
}