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
@RequestMapping("/member")
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
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id){
        System.out.println("### Deleting Method ###");
        personService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping(path = "/{id}")
    public ResponseEntity<PersonDto> findById(@PathVariable Integer id){
        return ResponseEntity.ok(personService.findById(id));
    }
    @PutMapping(path = "/unlock/{id}")
    public ResponseEntity<PersonDto> unlockById(@PathVariable Integer id){
        return ResponseEntity.ok(personService.unlock(id));
    }
    @PutMapping(path = "/lock/{id}")
    public ResponseEntity<PersonDto> lockById(@PathVariable Integer id){
        return ResponseEntity.ok(personService.lock(id));
    }
}
