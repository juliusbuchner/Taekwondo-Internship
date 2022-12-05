package se.taekwondointernship.data.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.taekwondointernship.data.models.entity.Message;
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
    public ResponseEntity<Message> createWelcome(@RequestBody MessageForm form){
        System.out.println("### In Create Method ###");
        return ResponseEntity.status(HttpStatus.CREATED).body(messageService.createWelcome(form));
    }
    @PutMapping(path = "/welcome")
    public ResponseEntity<Message> editWelcome(@RequestBody MessageForm form){
        return ResponseEntity.ok(messageService.editWelcome(form));
    }
    @PostMapping(path = "/news")
    public ResponseEntity<Message> createNews(@RequestBody MessageForm form){
        System.out.println("### In Create Method ###");
        return ResponseEntity.status(HttpStatus.CREATED).body(messageService.createNews(form));
    }
    @PutMapping(path = "/news")
    public ResponseEntity<Message> editNews(@RequestBody MessageForm form){
        return ResponseEntity.ok(messageService.editNews(form));
    }
}
