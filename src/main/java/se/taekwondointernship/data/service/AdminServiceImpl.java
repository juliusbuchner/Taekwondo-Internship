package se.taekwondointernship.data.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.taekwondointernship.data.models.dto.AdminDto;
import se.taekwondointernship.data.models.entity.Admin;
import se.taekwondointernship.data.models.form.AdminForm;
import se.taekwondointernship.data.repository.AdminRepository;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService{
    AdminRepository repository;
    ModelMapper modelMapper;
    public AdminServiceImpl(AdminRepository repository, ModelMapper modelMapper){
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public AdminDto create(AdminForm form) {
        Admin admin = repository.save(modelMapper.map(form, Admin.class));
        extractAdmin(admin);
        return modelMapper.map(admin, AdminDto.class);
    }

    @Override
    @Transactional
    public AdminDto editPassword(AdminForm form) {
        try {
            List<Admin> adminList = getFromExistingAdminJSON();
            Admin admin = modelMapper.map(form, Admin.class);
            admin.setUsername(adminList.get(0).getUsername());
            admin.setId(1);
            extractAdmin(admin);
            return modelMapper.map(admin, AdminDto.class);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private void extractAdmin(Admin admin) {
        JSONObject jsonAdminDetails = new JSONObject();
        jsonAdminDetails.put("id", admin.getId());
        jsonAdminDetails.put("username", admin.getUsername());
        jsonAdminDetails.put("password", admin.getPassword());
        try (FileWriter file = new FileWriter("admin.json")) {
            file.write(jsonAdminDetails.toJSONString());
            file.flush();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    @Transactional(readOnly = true)
    public String getPassword(){
        try {
            List<Admin> adminList = getFromExistingAdminJSON();
            return adminList.get(0).getPassword();
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }

    }

    @Transactional(readOnly = true)
    public String getUsername(){
        try {
            List<Admin> adminList = getFromExistingAdminJSON();
            return adminList.get(0).getUsername();
        } catch (IOException | ParseException e){
            throw new RuntimeException(e);
        }
    }

    public AdminDto logIn(){
        try {
            List<Admin> adminList = getFromExistingAdminJSON();
            Admin admin = adminList.get(0);
            admin.setLoggedIn(true);
            extractAdmin(admin);
            return modelMapper.map(admin, AdminDto.class);
        } catch (IOException | ParseException e){
            throw new RuntimeException(e);
        }
    }
    public AdminDto logOut(){
        try {
            List<Admin> adminList = getFromExistingAdminJSON();
            Admin admin = adminList.get(0);
            admin.setLoggedIn(false);
            extractAdmin(admin);
            return modelMapper.map(admin, AdminDto.class);
        } catch (IOException | ParseException e){
            throw new RuntimeException(e);
        }
    }

    private List<Admin> getFromExistingAdminJSON() throws IOException, ParseException {
        List<Admin> adminList = new ArrayList<>();
        JSONParser jsonParser = new JSONParser();
        FileReader reader = new FileReader("admin.json");
        Object obj = jsonParser.parse(reader);
        JSONArray jsonAdminList = new JSONArray();
        jsonAdminList.add(obj);
        jsonAdminList.forEach(mbr -> adminList.add(parseJsonAdmin((JSONObject) mbr)));
        return adminList;
    }

    private Admin parseJsonAdmin(JSONObject objectAdmin) {
        Integer id = Integer.parseInt(String.valueOf(objectAdmin.get("id")));
        String username = (String) objectAdmin.get("username");
        String password = (String) objectAdmin.get("password");
        return new Admin(id, username, password);
    }
}
