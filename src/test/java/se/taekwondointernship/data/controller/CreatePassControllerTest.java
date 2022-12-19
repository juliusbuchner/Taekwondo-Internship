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
import org.modelmapper.ModelMapper;
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
import org.springframework.web.context.WebApplicationContext;
import se.taekwondointernship.data.exceptions.ResourceNotFoundException;
import se.taekwondointernship.data.models.dto.CreatePassDto;
import se.taekwondointernship.data.models.entity.CreatePass;
import se.taekwondointernship.data.models.form.CreatePassForm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestEntityManager
@Transactional
@DirtiesContext

class CreatePassControllerTest {

    public static final String APPLICATION_JSON = "application/json";
    private MockMvc mockMvc;
    @InjectMocks
    private CreatePassController createPassControllerTest;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    ModelMapper modelMapper;

    File f;
    JSONObject jsonObject = new JSONObject();
    JSONObject jsonObject2 = new JSONObject();
    JSONArray jsonArray = new JSONArray();
    String PATH;


    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    JSONArray jsonSetUp() {
        CreatePass pass1 = new CreatePass(1, "ClassTest", LocalDate.parse("2023-01-07"), LocalTime.parse("16:00"), 60, false);
        CreatePass pass2 = new CreatePass(2, "ClassExtra", null, LocalTime.parse("17:00"), 45, true);
        LocalDate date = LocalDate.now();
        CreatePassForm form=modelMapper.map(pass1,CreatePassForm.class);
        CreatePassForm form2=modelMapper.map(pass2,CreatePassForm.class);
        //  Month month = date.getMonth();
        String fileName = "created_pass.json";
        String directoryName = "C:\\JSON\\" + date.getYear();
        f = new File(directoryName);
        boolean mkdir = f.mkdir();
        PATH = directoryName + "\\" + fileName;
        jsonObject.put("id", pass1.getId());
        jsonObject.put("className", form.getClassName());
        jsonObject.put("date", form.getDate());
        jsonObject.put("startTime", form.getStartTime());
        jsonObject.put("duration", form.getDuration());
        jsonObject.put("extraPass", form.isExtraPass());
        jsonArray.add(jsonObject);

        jsonObject2.put("id", pass2.getId());
        jsonObject2.put("className", form2.getClassName());
        jsonObject2.put("date", form2.getDate());
        jsonObject2.put("startTime", form2.getStartTime());
        jsonObject2.put("duration", form2.getDuration());
        jsonObject2.put("extraPass", form2.isExtraPass());
        jsonArray.add(jsonObject2);

        try {
            objectMapper.writeValue(new File(PATH), jsonArray);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            return objectMapper.readValue(new File(PATH), JSONArray.class);
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            return null;
        }

    }

    List<CreatePassDto> convertToCreatePassDto(JSONArray jsonArray) {
        return (List<CreatePassDto>) jsonArray.stream().map(jsonObject -> {
            try {
                String json = objectMapper.writeValueAsString(jsonObject);
                return objectMapper.readValue(json, CreatePassDto.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());
    }

    List<CreatePass> convertToList(JSONArray jsonArray) {
        return (List<CreatePass>) jsonArray.stream().map(jsonObject -> {
            try {
                String json = objectMapper.writeValueAsString(jsonObject);
                return objectMapper.readValue(json, CreatePass.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());
    }

    @Test
    void create() throws Exception {

        String jsonRequest;

        CreatePassForm createPassForm = new CreatePassForm("TestCreate", LocalDate.parse("2023-01-14"), LocalTime.parse("15:30"), 60, false);

        jsonRequest = objectMapper.writeValueAsString(createPassForm);

        mockMvc.perform(post("/api/createPass")
                        .contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(result -> {
                    String responseString = result.getResponse().getContentAsString();
                    CreatePassDto actualResponse = objectMapper.readValue(responseString, CreatePassDto.class);
                    assertEquals(createPassForm.getClassName(), actualResponse.getClassName());
                    assertEquals(createPassForm.getDate(), actualResponse.getDate());
                    assertEquals(createPassForm.getStartTime(), actualResponse.getStartTime());
                    assertEquals(createPassForm.getDuration(), actualResponse.getDuration());
                    assertEquals(createPassForm.isExtraPass(), actualResponse.isExtraPass());
                });
        //  Path path= Paths.get(PATH);
        //boolean result= Files.deleteIfExists(path);
    }

    @Test
    void findAll() throws Exception {
        JSONArray jsonArray1 = jsonSetUp();
        List<CreatePassDto> expextedResult = convertToCreatePassDto(jsonArray1);
        TypeReference<List<CreatePassDto>> typeReference = new TypeReference<List<CreatePassDto>>() {
        };
        mockMvc.perform(get("/api/createPass"))
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String response = result.getResponse().getContentAsString();
                    List<CreatePassDto> actualResponse = objectMapper.readValue(response, typeReference);
                    assertEquals(expextedResult.size(), actualResponse.size());
                });

        Path path = Paths.get(PATH);

        boolean result = Files.deleteIfExists(path);
        System.out.println("ANUSHA..........................." + result);

    }

    @Test
    void findByClassName() throws Exception {
        String className = "ClassTest";
        JSONArray jsonArray1 = jsonSetUp();
        List<CreatePassDto> list = convertToCreatePassDto(jsonArray1);
        List<CreatePassDto> expectedValue = list.stream()
                .filter(createPassDto -> createPassDto.getClassName().equalsIgnoreCase(className)).collect(Collectors.toList());
        TypeReference<List<CreatePassDto>> typeReference = new TypeReference<List<CreatePassDto>>() {
        };
        mockMvc.perform(get("/api/createPass/byClassName").param("className", className))
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String response = result.getResponse().getContentAsString();
                    List<CreatePassDto> actualResponse = objectMapper.readValue(response, typeReference);
                    assertEquals(expectedValue.size(), actualResponse.size());
                });

        Path path = Paths.get(PATH);
        boolean result = Files.deleteIfExists(path);
    }

    @Test
    void findByDate() throws Exception {
        LocalDate date = LocalDate.parse("2023-01-07");
        JSONArray jsonArray1 = jsonSetUp();
        List<CreatePassDto> list = convertToCreatePassDto(jsonArray1);
        List<CreatePassDto> expectedValue = list.stream().filter(createPassDto -> createPassDto.getDate().equals(date)).collect(Collectors.toList());
        TypeReference<List<CreatePassDto>> typeReference = new TypeReference<>() {
        };

        mockMvc.perform(get("/api/createPass/byDate").param("date", "2023-01-07").contentType(MediaType.APPLICATION_JSON))
                //     .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String response = result.getResponse().getContentAsString();
                    List<CreatePassDto> actualResponse = objectMapper.readValue(response, typeReference);
                    assertEquals(expectedValue.size(), actualResponse.size());
                });
        Path path = Paths.get(PATH);
        boolean result = Files.deleteIfExists(path);

    }

    @Test
    void update() throws Exception {
        CreatePassDto createpass = new CreatePassDto(1, "class20", LocalDate.parse("2023-02-13"), LocalTime.parse("16:30"), 60, false);
        JSONArray jsonArray1 = jsonSetUp();
        List<CreatePass> list = convertToList(jsonArray1);
        CreatePass updateValue = list.stream().filter(createPassDto -> createPassDto.getId() == createpass.getId()).findAny().orElseThrow(() -> new ResourceNotFoundException("Not found by id"));
        updateValue.setClassName(createpass.getClassName());
        updateValue.setDate(createpass.getDate());
        updateValue.setStartTime(createpass.getStartTime());
        updateValue.setDuration(createpass.getDuration());
        updateValue.setExtraPass(createpass.isExtraPass());

        String jsonRequest = objectMapper.writeValueAsString(updateValue);

        mockMvc.perform(put("/api/createPass").contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(result -> {
                    String responseString = result.getResponse().getContentAsString();
                    CreatePassDto actualValue = objectMapper.readValue(responseString, CreatePassDto.class);
                    assertEquals(actualValue.getClassName(), createpass.getClassName());
                    assertEquals(actualValue.getDate(), createpass.getDate());
                    assertEquals(actualValue.getStartTime(), createpass.getStartTime());
                    assertEquals(actualValue.getDuration(), createpass.getDuration());
                    assertEquals(actualValue.isExtraPass(), createpass.isExtraPass());

                });
    }




}