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
import se.taekwondointernship.data.models.entity.Pass;
import se.taekwondointernship.data.models.form.PassForm;
import se.taekwondointernship.data.repository.PassRepository;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
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
  //  Pass partcipant1=new Pass("Dummy","Dummy","Dummy","Dummy","Dummy", LocalDate.now(),"Dummy");
    PassForm partcipant2=new PassForm("Test","Testson","0002222","Tester","test", LocalDate.now(),"30");


    @Test
    void create() throws IOException {
     //  Pass pass= passRepository.save(partcipant1);

      //  assertTrue(!f.exists());

        PassDto testPart1;
       testPart1 = passServiceImplementation.create(partcipant2);
       testEntityManager.flush();
      // assertEquals("Anusha",testPart1.getFirstName());
       // File f=new File("C:\\JSON\\Week48_2022\\pass_2022-12-01");
        //assertTrue(f.exists());
       // PassDto found=passServiceImplementation.findByName("Dummy","Dummy");
        List<PassDto> found= passServiceImplementation.findAll();
        assertEquals(1,found.size());
       PassDto foundParticipant =found.get(0);

        assertEquals(partcipant2.getFirstName(),foundParticipant.getFirstName());
        assertEquals(partcipant2.getLastName(),foundParticipant.getLastName());
        assertEquals(partcipant2.getParentPhoneNumber(),foundParticipant.getParentPhoneNumber());
        assertEquals(partcipant2.getParentName(),foundParticipant.getParentName());
        assertEquals(partcipant2.getClassName(),foundParticipant.getClassName());
        assertEquals(partcipant2.getAge(),foundParticipant.getAge());
        assertEquals(partcipant2.getDate(),foundParticipant.getDate());
    }

    @Test
    void findAll() {
    }

    @Test
    void findByName() {

        PassDto foundByName=passServiceImplementation.findByName(partcipant2.getFirstName(), partcipant2.getLastName());

        assertEquals(partcipant2.getFirstName(),foundByName.getFirstName());
        assertEquals(partcipant2.getLastName(),foundByName.getLastName());
        assertEquals(partcipant2.getParentPhoneNumber(),foundByName.getParentPhoneNumber());
        assertEquals(partcipant2.getParentName(),foundByName.getParentName());
        assertEquals(partcipant2.getClassName(),foundByName.getClassName());
        assertEquals(partcipant2.getAge(),foundByName.getAge());
        assertEquals(partcipant2.getDate(),foundByName.getDate());



    }

    @Test
    void findAllByClassName() {
        List<PassDto> found=passServiceImplementation.findAllByClassName(partcipant2.getClassName());
        assertEquals(1,found.size());

    }

    @Test
    void findByNameAndClassName() {
        PassDto found=passServiceImplementation.findByNameAndClassName(partcipant2.getFirstName(), partcipant2.getLastName(),partcipant2.getClassName());
        assertEquals(partcipant2.getFirstName(),found.getFirstName());
        assertEquals(found.getLastName(),partcipant2.getLastName());
        assertEquals(found.getParentPhoneNumber(),partcipant2.getParentPhoneNumber());
        assertEquals(found.getParentName(),partcipant2.getParentName());
        assertEquals(found.getClassName(),partcipant2.getClassName());
        assertEquals(found.getAge(),partcipant2.getAge());
        assertEquals(found.getDate(),partcipant2.getDate());

    }
}