package se.taekwondointernship.data.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.taekwondointernship.data.models.dto.AdminDto;
import se.taekwondointernship.data.models.entity.Admin;
import se.taekwondointernship.data.models.form.AdminForm;
import se.taekwondointernship.data.repository.AdminRepository;
import se.taekwondointernship.data.storage.Firebase;

import java.util.List;
import java.util.Objects;

@Service
public class AdminServiceImpl implements AdminService{
    AdminRepository repository;
    ModelMapper modelMapper;
    EmailServiceImpl emailService;
    Firebase firebase = new Firebase();
    public AdminServiceImpl(AdminRepository repository, EmailServiceImpl emailService, ModelMapper modelMapper){
        this.repository = repository;
        this.emailService = emailService;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public AdminDto create(AdminForm form) {
        Admin admin = modelMapper.map(form, Admin.class);
        firebase.uploadAdmin(admin);
        repository.save(admin);
        return modelMapper.map(admin, AdminDto.class);
    }

    @Override
    @Transactional
    public AdminDto editPassword(AdminForm form) {
        Admin admin = modelMapper.map(form, Admin.class);
        firebase.editAdmin("password", form.getPassword());
        return modelMapper.map(admin, AdminDto.class);
    }
    @Transactional(readOnly = true)
    public String getPassword(){
        List<Admin> adminList = getFromExistingAdmin();
        return adminList.get(0).getPassword();

    }

    @Transactional(readOnly = true)
    public String getUsername(){
        List<Admin> adminList = getFromExistingAdmin();
        return adminList.get(0).getUsername();
    }

    @Override
    @Transactional
    public AdminDto findByUsername(String username){
        List<Admin> adminList;
        adminList = getFromExistingAdmin();
        Admin admin = new Admin();
            for (Admin value : adminList){
                if (Objects.equals(value.getUsername(), username)){
                    admin = value;
                }
            }
        return modelMapper.map(admin, AdminDto.class);
        }

    @Override
    @Transactional
    public AdminDto logIn(){
        Admin admin = getFromExistingAdmin().get(0);
        firebase.logInAdmin(admin);
        return modelMapper.map(admin, AdminDto.class);
    }
    @Override
    @Transactional
    public AdminDto logOut(){
        Admin admin = getFromExistingAdmin().get(0);
        firebase.logOutAdmin(admin);
        return modelMapper.map(admin, AdminDto.class);
    }
    @Override
    @Transactional
    public String sendReset(String username){
        if (username.equals(getUsername())){
            System.out.println(emailService.getEmail());
            emailService.sendLink(emailService.getEmail());
            return "Det har skickats ett mail där du kan återställa ditt lösenord.";
        } else return "Den här adminen finns inte, kontrollera att du har stavat rätt eller lämna funktionen";
    }

    private List<Admin> getFromExistingAdmin() {
        return firebase.findAdmin();
    }
}
