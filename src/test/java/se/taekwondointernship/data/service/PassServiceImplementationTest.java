package se.taekwondointernship.data.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import se.taekwondointernship.data.models.dto.PassDto;
import se.taekwondointernship.data.models.entity.CreatePass;
import se.taekwondointernship.data.models.entity.Pass;
import se.taekwondointernship.data.models.entity.Person;
import se.taekwondointernship.data.models.form.PassForm;
import se.taekwondointernship.data.repository.PassRepository;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
//@DataJpaTest
@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@Transactional
@DirtiesContext

//@DataJpaTest
class PassServiceImplementationTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    PassRepository passRepository;
    @Autowired
    ModelMapper modelMapper=new ModelMapper();
    @Autowired
    ObjectMapper objectMapper=new ObjectMapper();
    @Autowired
    PassServiceImplementation passServiceImplementation;
   //PassServiceImplementation passServiceImplementation=new PassServiceImplementation(modelMapper,objectMapper,passRepository);

    void setUp(){

    }
      Person person= new Person(1,"Test","Testsson", "123456789","Testare Testsson","987654321","test.testsson@mail.com","900101-0000","32",true,0,false);
      CreatePass pass = new CreatePass(1,"class10",LocalDate.parse("2023-01-30"), LocalTime.parse("15:30"),45,false);
      PassForm partcipant1 =new PassForm(1,1,1,LocalDate.now());




    @Test
    void createFromService() throws IOException {

        Boolean testPart1;
       testPart1 = passServiceImplementation.create(partcipant1);
       testEntityManager.flush();
        List<PassDto> found= passServiceImplementation.findAll();
        assertEquals(1,found.size());
       PassDto foundParticipant =found.get(0);

        assertEquals(person.getFirstName(),foundParticipant.getFirstName());
        assertEquals(person.getLastName(),foundParticipant.getLastName());
        assertEquals(person.getParentNumber(),foundParticipant.getParentPhoneNumber());
        assertEquals(person.getParentName(),foundParticipant.getParentName());
        assertEquals(pass.getClassName(),foundParticipant.getClassName());
        assertEquals(person.getAge(),foundParticipant.getAge());
        assertEquals(partcipant1.getDate(),foundParticipant.getDate());
        assertTrue(testPart1);
    }


    @Test
    void findAll() throws Exception {
        List<PassDto> found= passServiceImplementation.findAll();

        assertEquals(1,found.size());

    }

    @Test
    void findByName() {

        PassDto foundByName=passServiceImplementation.findByName(person.getFirstName(), person.getLastName());

        assertEquals(person.getFirstName(),foundByName.getFirstName());
        assertEquals(person.getLastName(),foundByName.getLastName());
        assertEquals(person.getParentNumber(),foundByName.getParentPhoneNumber());
        assertEquals(person.getParentName(),foundByName.getParentName());
        assertEquals(pass.getClassName(),foundByName.getClassName());
        assertEquals(person.getAge(),foundByName.getAge());
        assertEquals(partcipant1.getDate(),foundByName.getDate());



    }

    @Test
    void findAllByClassName() {
        List<PassDto> found=passServiceImplementation.findAllByClassName(pass.getClassName());
        assertEquals(1,found.size());

    }

    @Test
    void findByNameAndClassName() {
        PassDto found=passServiceImplementation.findByNameAndClassName(person.getFirstName(), person.getLastName(),pass.getClassName());
        assertEquals(person.getFirstName(),found.getFirstName());
        assertEquals(found.getLastName(),person.getLastName());
        assertEquals(found.getParentPhoneNumber(),person.getParentNumber());
        assertEquals(found.getParentName(),person.getParentName());
        assertEquals(found.getClassName(),pass.getClassName());
        assertEquals(found.getAge(),person.getAge());
        assertEquals(found.getDate(),partcipant1.getDate());


    }
}