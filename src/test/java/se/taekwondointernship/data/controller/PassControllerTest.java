
package se.taekwondointernship.data.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import se.taekwondointernship.data.exceptions.ResourceNotFoundException;
import se.taekwondointernship.data.models.dto.PassDto;
import se.taekwondointernship.data.models.entity.CreatePass;
import se.taekwondointernship.data.models.entity.Message;
import se.taekwondointernship.data.models.entity.Person;
import se.taekwondointernship.data.models.form.PassForm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
//@WebMvcTest
@AutoConfigureTestEntityManager
@Transactional
@DirtiesContext
//@RequestMapping(value = "/accounts", produces = APPLICATION_JSON, consumes = APPLICATION_JSON)

class PassControllerTest {
    public static final String APPLICATION_JSON = "application/json";
    private MockMvc mockMvc;
    @InjectMocks
    private PassController passController;
    @Autowired
    private WebApplicationContext context;
    @Autowired
     private TestEntityManager testEntityManager;
    @Autowired
    ObjectMapper objectMapper;
    File f;
    JSONObject jsonObject =new JSONObject();
    JSONArray jsonArray=new JSONArray();
    String PATH;
    @BeforeEach
    public void setUp()  {
        mockMvc= MockMvcBuilders.webAppContextSetup(context).build();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false);

        }
    PassForm form=new PassForm(1,1,1,LocalDate.now());
    Person person= new Person(1,"Test","Testsson", "123456789","Testare Testsson","987654321","test.testsson@mail.com","900101-0000","32",true,0,false);
    CreatePass pass = new CreatePass(1,"class10",LocalDate.parse("2023-01-30"), LocalTime.parse("15:30"),45,false);


    JSONArray jsonSetUp() throws JsonProcessingException{


        LocalDate date=LocalDate.now();
        int weekOfYear=date.get(WeekFields.of(Locale.getDefault()).weekOfYear());

        String fileName="pass_"+date+".json";
        String directoryName="C:\\JSON\\"+"Week"+weekOfYear+"_"+date.getYear();
        f=new File(directoryName);
        boolean mkdir=f.mkdir();
         PATH =directoryName+"\\"+fileName;

        jsonObject.put("firstName",person.getFirstName());
        jsonObject.put("lastName",person.getLastName());
        jsonObject.put("parentPhoneNumber",person.getParentNumber());
        jsonObject.put("parentName",person.getParentName());
        jsonObject.put("className",pass.getClassName());
        jsonObject.put("age",person.getAge());
        jsonObject.put("date",form.getDate());
        jsonArray.add(jsonObject);
        System.out.println(jsonObject);


        //For creating folder, file and writing into json file

        try {

            objectMapper.writeValue(new File(PATH), jsonArray);

        } catch (IOException ex) {
            ex.printStackTrace();
        }

            // Reading from json file
             try {
                return objectMapper.readValue(new File(PATH), JSONArray.class);
            } catch (FileNotFoundException e){
                return null;
            }
            catch (IOException e) {
                return null;
            }
        }

     List<PassDto>   convertToPassDto(JSONArray jsonArray){

        return (List<PassDto>) jsonArray.stream().map(jsonObject -> {
            try{
                String json=objectMapper.writeValueAsString(jsonObject);
                return objectMapper.readValue(json, PassDto.class);

            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());

        }



    @Test
    public void createControllerClass() throws Exception {
        PassForm passForm=new PassForm();
        passForm.setPassId(1);
        passForm.setPersonId(1);
        passForm.setCreatePassId(1);
        passForm.setDate(LocalDate.now());
        String jsonRequest=objectMapper.writeValueAsString(passForm);
        mockMvc.perform(post("/api/pass/participant")
                .contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    String responseString = result.getResponse().getContentAsString();
                    System.out.println("hiraj");
                    System.out.println(responseString);
                    Message actualResponse = objectMapper.readValue(responseString, Message.class);
                  //  assertEquals(actualResponse.getFirstName(), person.getFirstName());
                   // assertEquals(actualResponse.getLastName(),person.getLastName());
                  //  assertEquals(actualResponse.getParentPhoneNumber(),person.getParentNumber());
                  //  assertEquals(actualResponse.getParentName(),person.getParentName());
                   // assertEquals(actualResponse.getClassName(),pass.getClassName());
                    assertEquals(1, actualResponse.getMessageId());
                    assertEquals("Sign-in is Success",actualResponse.getMessageContent());
                });
                   }


    @Test
    void findAll() throws Exception{
       JSONArray jsonArray= jsonSetUp();
        System.out.println("Printing at findall method");
        System.out.println(jsonArray);
       List<PassDto> expectedResult= convertToPassDto(jsonArray);
        com.fasterxml.jackson.core.type.TypeReference<List<PassDto>> typeReference=new com.fasterxml.jackson.core.type.TypeReference<List<PassDto>>() {};
        mockMvc.perform(get("/api/pass/participantList"))
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result ->{
                    String response= result.getResponse().getContentAsString();
                    List<PassDto> actualResponse= objectMapper.readValue(response,typeReference);
                    assertEquals(expectedResult.size(),actualResponse.size());
                } );

        if(f.exists()){
            f.delete();
        }
    }




    @Test
    void findByName() throws Exception {
        String firstName="Test";
        String lastName="Testsson";

      JSONArray jsonArray=  jsonSetUp();
      List<PassDto> list=convertToPassDto(jsonArray);
      PassDto expectedValue=list.stream()
              .filter(passDto -> passDto.getFirstName().equalsIgnoreCase(firstName) && passDto.getLastName().equalsIgnoreCase(lastName)).findFirst()
                      .orElseThrow(() -> new ResourceNotFoundException("Name not found"));

        mockMvc.perform(get("/api/pass/byName").param("firstName",firstName).param("lastName",lastName))
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String response= result.getResponse().getContentAsString();
                    PassDto actualResponse= objectMapper.readValue(response,PassDto.class);
                    assertEquals(actualResponse.getFirstName(),expectedValue.getFirstName());
                    assertEquals(actualResponse.getLastName(),expectedValue.getLastName());
                    assertEquals(actualResponse.getParentPhoneNumber(),expectedValue.getParentPhoneNumber());
                    assertEquals(actualResponse.getParentName(),expectedValue.getParentName());
                    assertEquals(actualResponse.getClassName(),expectedValue.getClassName());
                    assertEquals(actualResponse.getAge(),expectedValue.getAge());
                    assertEquals(actualResponse.getDate(),expectedValue.getDate());
                });
        if(f.exists()){
            f.delete();
        }
    }

    @Test
    void findByClassName() throws Exception {
        String className="class10";
        JSONArray json=jsonSetUp();
        List<PassDto> list =convertToPassDto(json);
        List<PassDto> expectedValue= list.stream().filter(passDto -> passDto.getClassName().equalsIgnoreCase(className)).collect(Collectors.toList());

    com.fasterxml.jackson.core.type.TypeReference<List<PassDto>> typeReference=new TypeReference<List<PassDto>>(){};
    mockMvc.perform(get("/api/pass/byClassName").param("className",className))
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(result -> {
                String response= result.getResponse().getContentAsString();
                List<PassDto> actualResponse=objectMapper.readValue(response,typeReference);
                assertEquals(expectedValue.size(),actualResponse.size());
            });

        if(f.exists()){
            f.delete();
        }
    }

    @Test
    void findByFirstNameLastNameClassName() throws Exception {
        String firstName="Test";
        String lastName="Testsson";
        String className="class10";

        JSONArray json=jsonSetUp();
        List<PassDto> list=convertToPassDto(json);
       PassDto expectedResponse= list.stream().filter(passDto -> passDto.getFirstName().equalsIgnoreCase(firstName) && passDto.getLastName().equalsIgnoreCase(lastName) && passDto.getClassName().equalsIgnoreCase(className))
                .findFirst().orElseThrow(()-> new ResourceNotFoundException("Name and Class Not found"));

        mockMvc.perform(get("/api/pass/byNameAndClassName")
                .param("firstName",firstName).param("lastName",lastName).param("className",className))
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String response= result.getResponse().getContentAsString();
                    PassDto actualResponse= objectMapper.readValue(response,PassDto.class);
                    assertEquals(expectedResponse.getFirstName(),actualResponse.getFirstName());
                    assertEquals(expectedResponse.getLastName(),actualResponse.getLastName());
                    assertEquals(expectedResponse.getParentPhoneNumber(),actualResponse.getParentPhoneNumber());
                    assertEquals(expectedResponse.getParentName(),actualResponse.getParentName());
                    assertEquals(expectedResponse.getClassName(),actualResponse.getClassName());
                    assertEquals(expectedResponse.getAge(),actualResponse.getAge());
                });
        if(f.exists()){
            f.delete();
        }
    }
}
