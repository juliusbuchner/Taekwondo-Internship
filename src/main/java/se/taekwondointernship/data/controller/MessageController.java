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
        return ResponseEntity.status(HttpStatus.CREATED).body(messageService.create(form, "welcomeMessage.json"));
    }
    @PutMapping(path = "/welcome")
    public ResponseEntity<MessageDto> editWelcome(@RequestBody MessageForm form){
        return ResponseEntity.ok(messageService.edit(form, "welcomeMessage.json"));
    }
    @GetMapping(path = "/welcome")
    public ResponseEntity<MessageDto> findWelcome(){
        return ResponseEntity.ok(messageService.findMessage("welcomeMessage.json"));
    }
    @PostMapping(path = "/news")
    public ResponseEntity<MessageDto> createNews(@RequestBody MessageForm form){
        System.out.println("### In Create Method ###");
        return ResponseEntity.status(HttpStatus.CREATED).body(messageService.create(form, "newsMessage.json"));
    }
    @PutMapping(path = "/news")
    public ResponseEntity<MessageDto> editNews(@RequestBody MessageForm form){
        return ResponseEntity.ok(messageService.edit(form, "newsMessage.json"));
    }
    @GetMapping(path = "/news")
    public ResponseEntity<MessageDto> findNews(){
        return ResponseEntity.ok(messageService.findMessage("newsMessage.json"));
    }
    @PostMapping(path = "/email")
    public ResponseEntity<MessageDto> createMail(@RequestBody MessageForm form){
        return ResponseEntity.status(HttpStatus.CREATED).body(messageService.create(form, "emailMessage.json"));
    }
    @PutMapping(path = "/email")
    public ResponseEntity<MessageDto> editMail(@RequestBody MessageForm form){
        return ResponseEntity.ok(messageService.edit(form, "emailMessage.json"));
    }
    @GetMapping(path = "/email")
    public ResponseEntity<MessageDto> findMail(){
        return ResponseEntity.ok(messageService.findMessage("emailMessage.json"));
    }
}
