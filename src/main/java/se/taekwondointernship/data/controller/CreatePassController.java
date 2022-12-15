package se.taekwondointernship.data.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.taekwondointernship.data.models.dto.CreatePassDto;
import se.taekwondointernship.data.models.form.CreatePassForm;
import se.taekwondointernship.data.repository.CreatePassRepository;
import se.taekwondointernship.data.service.CreatePassService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/createPass")
public class CreatePassController {
    private final CreatePassService createPassService;

    @Autowired
    public CreatePassController(CreatePassService createPassService) {
        this.createPassService = createPassService;
    }

    @PostMapping()
    public ResponseEntity<CreatePassDto> create(@RequestBody CreatePassForm createPassForm){
        return ResponseEntity.status(HttpStatus.CREATED).body(createPassService.create(createPassForm));
    }

    @PutMapping()
    public ResponseEntity<CreatePassDto> update(@RequestBody CreatePassDto dto){
        return ResponseEntity.ok(createPassService.update(dto));
    }

    @GetMapping()
    public ResponseEntity<List<CreatePassDto>> findAll(){
        return ResponseEntity.ok(createPassService.findAll());
    }
    @GetMapping("/byClassName")
    public ResponseEntity<List<CreatePassDto>> findByClassName(@RequestParam("className") String className){
        return ResponseEntity.ok(createPassService.findByClassName(className));
    }
    @GetMapping("/byDate")
    public ResponseEntity<List<CreatePassDto>> findByDate(@RequestParam("date") @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate date){
        return ResponseEntity.ok(createPassService.findByDate(date));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id){
        createPassService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }





}
