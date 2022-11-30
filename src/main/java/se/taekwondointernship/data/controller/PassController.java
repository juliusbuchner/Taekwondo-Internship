package se.taekwondointernship.data.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import se.taekwondointernship.data.models.dto.PassDto;
import se.taekwondointernship.data.models.form.PassForm;
import se.taekwondointernship.data.service.PassService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/pass")
public class PassController {
  public final  PassService passService;

    @Autowired
    public PassController(PassService passService) {

        this.passService = passService;
    }

   // @PostMapping(path = "/participant", consumes = "application/json", produces = "application/json" )
   @PostMapping("/participant")
   public ResponseEntity<PassDto> createPass(@RequestBody PassForm passForm){
        return ResponseEntity.status(HttpStatus.CREATED).body(passService.create(passForm));
       }


    @GetMapping("/participantList")
    public ResponseEntity<List<PassDto>> findAll() throws IOException {
        return ResponseEntity.ok(passService.findAll());
    }

    @GetMapping("/byName")
    public ResponseEntity<PassDto> findByName(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName){
        return ResponseEntity.ok(passService.findByName(firstName,lastName));
    }

    @GetMapping("/byClassName")
    public ResponseEntity<List<PassDto>> findByClassName(@RequestParam("className") String className){
        return ResponseEntity.ok(passService.findAllByClassName(className));
    }

    @GetMapping("/byNameAndClassName")
    public ResponseEntity<PassDto> findByFirstNameLastNameClassName(@RequestParam("firstName") String firstName,@RequestParam("lastName") String lastName, @RequestParam("className") String className){
        return ResponseEntity.ok(passService.findByNameAndClassName(firstName,lastName,className));

    }


}
