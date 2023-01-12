package se.taekwondointernship.data.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.taekwondointernship.data.models.dto.MessageDto;
import se.taekwondointernship.data.models.form.MessageForm;
import se.taekwondointernship.data.service.MessageService;

@RestController
@RequestMapping("/message")
public class MessageController {
    private final MessageService messageService;
    @Autowired
    public MessageController(MessageService personService){
        this.messageService = personService;
    }
    @PostMapping(path = "/welcome")
    public ResponseEntity<MessageDto> createWelcome(@RequestBody MessageForm form){
        System.out.println("### In Create Method ###");
        return ResponseEntity.status(HttpStatus.CREATED).body(messageService.create(form, "welcome"));
    }
    @PutMapping(path = "/welcome")
    public ResponseEntity<MessageDto> editWelcome(@RequestBody MessageForm form){
        return ResponseEntity.ok(messageService.edit("welcome", form));
    }
    @GetMapping(path = "/welcome")
    public ResponseEntity<MessageDto> findWelcome(){
        return ResponseEntity.ok(messageService.findMessage("welcome"));
    }
    @PostMapping(path = "/news")
    public ResponseEntity<MessageDto> createNews(@RequestBody MessageForm form){
        System.out.println("### In Create Method ###");
        return ResponseEntity.status(HttpStatus.CREATED).body(messageService.create(form, "news"));
    }
    @PutMapping(path = "/news")
    public ResponseEntity<MessageDto> editNews(@RequestBody MessageForm form){
        return ResponseEntity.ok(messageService.edit("news", form));
    }
    @GetMapping(path = "/news")
    public ResponseEntity<MessageDto> findNews(){
        return ResponseEntity.ok(messageService.findMessage("news"));
    }
    @PostMapping(path = "/email")
    public ResponseEntity<MessageDto> createMail(@RequestBody MessageForm form){
        return ResponseEntity.status(HttpStatus.CREATED).body(messageService.create(form, "email"));
    }
    @PutMapping(path = "/email")
    public ResponseEntity<MessageDto> editMail(@RequestBody MessageForm form){
        return ResponseEntity.ok(messageService.edit("email", form));
    }
    @GetMapping(path = "/email")
    public ResponseEntity<MessageDto> findMail(){
        return ResponseEntity.ok(messageService.findMessage("email"));
    }
}
