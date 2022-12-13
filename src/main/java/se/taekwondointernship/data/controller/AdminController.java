package se.taekwondointernship.data.controller;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.taekwondointernship.data.models.dto.AdminDto;
import se.taekwondointernship.data.models.form.AdminForm;
import se.taekwondointernship.data.service.AdminService;

import java.io.IOException;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService){this.adminService = adminService;}

    @PostMapping
    public ResponseEntity<AdminDto> create(@RequestBody AdminForm form){
        System.out.println("### In Create Method ###");
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.create(form));
    }
    @PutMapping("/{oldPassword}")
    public ResponseEntity<?> edit(@RequestBody AdminForm form, @PathVariable String oldPassword){
        if (oldPassword.equals(adminService.getPassword())){
            try {
                return ResponseEntity.ok(adminService.editPassword(form));
            } catch (IOException | ParseException e) {
                throw new RuntimeException(e);
            }
        } else {
            return ResponseEntity.status(403).body("Det nuvarande lösenordet är fel. Om du inte är behörig, var god och låt bli admin.");
        }
    }
    @PutMapping("/login/{username}/{password}")
    public ResponseEntity<?> logIn(@PathVariable String username, @PathVariable String password){
        if (username.equals(adminService.getUsername()) && password.equals(adminService.getPassword())){
            return ResponseEntity.ok(adminService.logIn());
        } else {
            return ResponseEntity.status(403).body("Användarnamnet och/eller lösenordet är fel. Om du inte är behörig, var god och låt bli admin.");
        }
    }

    @PutMapping("/logout")
    public ResponseEntity<?> logOut(){
        return ResponseEntity.ok(adminService.logOut());
    }
}