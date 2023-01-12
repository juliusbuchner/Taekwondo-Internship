package se.taekwondointernship.data.service;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.taekwondointernship.data.models.dto.EmailDto;
import se.taekwondointernship.data.models.dto.MessageDto;
import se.taekwondointernship.data.models.entity.Email;
import se.taekwondointernship.data.models.form.EmailForm;
import se.taekwondointernship.data.repository.EmailRepository;
import se.taekwondointernship.data.storage.Firebase;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

@Service
public class EmailServiceImpl implements EmailService{
    MessageServiceImpl messageService;
    EmailRepository repository;
    ModelMapper modelMapper;
    Firebase firebase = new Firebase();
    public EmailServiceImpl(MessageServiceImpl messageService, EmailRepository repository, ModelMapper modelMapper){
        this.messageService = messageService;
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public EmailDto create(EmailForm form) {
        Email email = modelMapper.map(form, Email.class);
        firebase.uploadEmail(email);
        repository.save(email);
        return modelMapper.map(email, EmailDto.class);
    }

    @Override
    @Transactional
    public EmailDto editSender(String sender) {
        Email email = firebase.findEmail().get(0);
        firebase.editEmail("sender", sender);
        return modelMapper.map(email, EmailDto.class);
    }

    @Override
    @Transactional
    public EmailDto editPassword(String password) {
        Email email = firebase.findEmail().get(0);
        firebase.editEmail("password", password);
        return modelMapper.map(email, EmailDto.class);
    }

    @Override
    @Transactional
    public EmailDto editSenderName(String name){
        Email email = firebase.findEmail().get(0);
        firebase.editEmail("senderName", name);
        return modelMapper.map(email, EmailDto.class);
    }

    @Override
    @Transactional
    public EmailDto editSubject(String subject){
        Email email = firebase.findEmail().get(0);
        firebase.editEmail("subject", subject);
        return modelMapper.map(email, EmailDto.class);
    }

    @Override
    @Transactional
    public EmailDto editAttachURL(String attachURL){
        Email email = firebase.findEmail().get(0);
        firebase.editEmail("attachURL", attachURL);
        return modelMapper.map(email, EmailDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public String getPassword() {
        List<Email> listOfEmailInfo = firebase.findEmail();
        return listOfEmailInfo.get(0).getPassword();
    }

    @Override
    @Transactional(readOnly = true)
    public MessageDto getEmailMessage() {
        return messageService.findMessage("emailMessage");
    }

    @Override
    @Transactional(readOnly = true)
    public String getEmail(){
        return firebase.findEmail().get(0).getSender();
    }

    @Override
    @Transactional
    public void sendAttach(String recipient) {
        try {
            Email email = firebase.findEmail().get(0);
            String a = firebase.findEmail().get(0).getSenderName();
            String b = firebase.findEmail().get(0).getPassword();
            System.out.println(a+"\n"+b);

            MimeMessage msg = preparingMail(email);
            msg.setSubject(email.getSubject(), "UTF-8");
            msg.setSentDate(new Date());

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient, false));
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(messageService.findMessage("emailMessage").getMessageContent());
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            if (!email.getAttachURL().equals("") || email.getAttachURL() != null){
                messageBodyPart = new MimeBodyPart();
                DataSource source = new FileDataSource(email.getAttachURL());
                messageBodyPart.setDataHandler(new DataHandler(source));
                messageBodyPart.setFileName(email.getAttachURL());
                multipart.addBodyPart(messageBodyPart);
                msg.setContent(multipart);
            }
            Transport.send(msg);
            System.out.println("Email Sent Successfully with attachment!");
        } catch (MessagingException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private MimeMessage preparingMail(Email email) throws MessagingException, UnsupportedEncodingException {
        MimeMessage msg = new MimeMessage(setUpSession());
        msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
        msg.addHeader("format", "flowed");
        msg.addHeader("Content-Transfer-Encoding", "8bit");
        msg.setFrom(new InternetAddress(email.getSender(), email.getSenderName()));
        msg.setReplyTo(InternetAddress.parse(email.getSender(), false));
        return msg;
    }

    @Override
    @Transactional
    public void sendLink(String recipient){
        try {
            String newPassword = randomTempPassword();
            firebase.editAdmin("password", newPassword);

            Email email = firebase.findEmail().get(0);
            String a = firebase.findEmail().get(0).getSenderName();
            String b = firebase.findEmail().get(0).getPassword();
            System.out.println(a);
            System.out.println(b);

            MimeMessage msg = preparingMail(email);
            msg.setSubject("Återställning av lösenord", "UTF-8");
            msg.setSentDate(new Date());

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient, false));
            MimeBodyPart linkBodyPart = new MimeBodyPart();
            String url = "http://localhost:3000/newpwd/reset";
            String link = "<a href='"+url+"'>"+"Återställ lösenord"+"</a>";
            String message = "För att återställa lösenordet, tryck på länken som följt med mailet. Och skriv in det nya" +
                    "slumpade lösenordet.\n Det temporära lösenordet är "+ firebase.findAdmin().get(0).getPassword();
            linkBodyPart.setText(message+"\n"+link, "utf-8", "html");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(linkBodyPart);
            msg.setContent(multipart);
            Transport.send(msg);
            System.out.println("Email sent successfully with link!");
        } catch (MessagingException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Session setUpSession() {
        Email email = firebase.findEmail().get(0);
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");
        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email.getSender(), email.getPassword());
            }
        };
        return Session.getDefaultInstance(properties, auth);
    }
    private String randomTempPassword(){
        String randomString;
        Random random = new Random();
            char[] word = new char[random.nextInt(6)+5];
            for(int j = 0; j < word.length; j++)
            {
                word[j] = (char)('a' + random.nextInt(26));
            }
            randomString = new String(word);
        return randomString;
    }
}