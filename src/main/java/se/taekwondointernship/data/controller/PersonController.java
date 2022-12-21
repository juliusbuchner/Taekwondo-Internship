package se.taekwondointernship.data.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.taekwondointernship.data.models.dto.PersonDto;
import se.taekwondointernship.data.models.form.PersonForm;
import se.taekwondointernship.data.service.PersonService;

import java.util.List;

@RestController
@RequestMapping("/participant")
public class PersonController {
    private final PersonService personService;
    @Autowired
    public PersonController(PersonService personService){
        this.personService = personService;
    }
    @PostMapping
    public ResponseEntity<PersonDto> create(@RequestBody PersonForm form){
        System.out.println("### In Create Method ###");
        return ResponseEntity.status(HttpStatus.CREATED).body(personService.create(form));
    }
    @GetMapping
    public ResponseEntity<List<PersonDto>> findAll(){
        return ResponseEntity.ok(personService.findAll());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id){
        System.out.println("### Deleting Method ###");
        personService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<PersonDto> findById(@PathVariable Integer id){
        return ResponseEntity.ok(personService.findById(id));
    }
}
