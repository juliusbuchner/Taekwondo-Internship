package se.taekwondointernship.data.storage;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;
import se.taekwondointernship.data.models.entity.Admin;
import se.taekwondointernship.data.models.entity.Email;
import se.taekwondointernship.data.models.entity.MessageEntity;
import se.taekwondointernship.data.models.entity.Person;
import se.taekwondointernship.data.models.form.MessageForm;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class Firebase {
    private static final String databaseURL = "https://signinwithadmin-3b3ac-default-rtdb.europe-west1.firebasedatabase.app";
    private final FirebaseDatabase database;
    private final DatabaseReference ref;
    public Firebase() {
        FileInputStream serviceAccount;
        try {
            serviceAccount = new FileInputStream("signinwithadmin-3b3ac-firebase-adminsdk-2xcxh-a17850b73d.json");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        FirebaseOptions options;
        try {
            options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl(databaseURL)
                    .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
        }
        database = FirebaseDatabase.getInstance(databaseURL);
        ref = database.getReference("signinwithadmin");
    }
    public List<Admin> findAdmin(){
        final CountDownLatch sync = new CountDownLatch(1);
        List<Admin> adminList = new ArrayList<>();
        DatabaseReference drMembers = ref.child("admins");
        drMembers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adminList.clear();
                for (DataSnapshot postSnapShot: dataSnapshot.getChildren()) {
                    Admin admin = postSnapShot.getValue(Admin.class);
                    adminList.add(admin);
                    sync.countDown();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed");
            }
        });
        try {
            sync.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return adminList;
    }
    public List<Email> findEmail(){
        final CountDownLatch sync = new CountDownLatch(1);
        List<Email> emailList = new ArrayList<>();
        DatabaseReference drMembers = ref.child("emails");
        drMembers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                emailList.clear();
                for (DataSnapshot postSnapShot: dataSnapshot.getChildren()) {
                    Email email = postSnapShot.getValue(Email.class);
                    emailList.add(email);
                    sync.countDown();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed");
            }
        });
        try {
            sync.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return emailList;
    }
    public List<MessageEntity> findMessage(String type){
        final CountDownLatch sync = new CountDownLatch(1);
        List<MessageEntity> messageList = new ArrayList<>();
        DatabaseReference messageRef = ref.child("messages/"+type);
        messageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                messageList.clear();
                for (DataSnapshot postSnapShot: dataSnapshot.getChildren()) {
                    MessageEntity message = postSnapShot.getValue(MessageEntity.class);
                    messageList.add(message);
                    sync.countDown();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed");
            }
        });
        try {
            sync.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return messageList;
    }
    public List<Person> findMember(){
        final CountDownLatch sync = new CountDownLatch(1);
        List<Person> personList = new ArrayList<>();
        DatabaseReference drMembers = ref.child("members");
        drMembers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    personList.clear();
                    System.out.println("A");
                    for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                        Person person = postSnapShot.getValue(Person.class);
                        personList.add(person);
                        sync.countDown();
                    }
                }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed");
            }
        });
        try {
            sync.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return personList;
    }
    public void uploadMember(Person person){
        final CountDownLatch syncs = new CountDownLatch(1);
        List<Person> personList = new ArrayList<>();
        DatabaseReference usersRef = ref.child("members");
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                        Person person = postSnapShot.getValue(Person.class);
                        personList.add(person);
                    }
                    person.setPersonId(personList.get(personList.size()-1).getPersonId() + 1);
                    personList.clear();
                    syncs.countDown();
                } else {
                    person.setPersonId(1);
                    syncs.countDown();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed");
            }
        });
        try {
            syncs.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        usersRef.child(person.getPersonId().toString()).setValueAsync(person);
    }
    public void uploadAdmin(Admin admin){
        DatabaseReference usersRef = ref.child("admins");
        List<Admin> adminList = new ArrayList<>();
        final CountDownLatch syncs = new CountDownLatch(1);
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                        Admin admin = postSnapShot.getValue(Admin.class);
                        adminList.add(admin);
                    }
                    admin.setId(adminList.get(adminList.size()-1).getId() + 1);
                    adminList.clear();
                    syncs.countDown();
                } else {
                    admin.setId(1);
                    syncs.countDown();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed");
            }
        });
        try {
            syncs.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        usersRef.child(admin.getId().toString()).setValueAsync(admin);
    }
    public void uploadMessage(MessageEntity messageEntity, String type){
        DatabaseReference usersRef = ref.child("messages/"+type);
        List<MessageEntity> messageEntityList = new ArrayList<>();
        final CountDownLatch syncs = new CountDownLatch(1);
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                        MessageEntity message = postSnapShot.getValue(MessageEntity.class);
                        messageEntityList.add(message);
                    }
                    messageEntity.setMessageId(messageEntityList.get(messageEntityList.size()-1).getMessageId() + 1);
                    messageEntityList.clear();
                    syncs.countDown();
                } else {
                    messageEntity.setMessageId(1);
                    syncs.countDown();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed");
            }
        });
        try {
            syncs.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        usersRef.child(messageEntity.getMessageId().toString()).setValueAsync(messageEntity);
    }
    public void uploadEmail(Email email){
        DatabaseReference usersRef = ref.child("emails");
        List<Email> emailList = new ArrayList<>();
        final CountDownLatch syncs = new CountDownLatch(1);
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                        Email email = postSnapShot.getValue(Email.class);
                        emailList.add(email);
                    }
                    email.setId(emailList.get(emailList.size()-1).getId() + 1);
                    emailList.clear();
                    syncs.countDown();
                } else {
                    email.setId(1);
                    syncs.countDown();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed");
            }
        });
        try {
            syncs.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        usersRef.child(email.getId().toString()).setValueAsync(email);
    }
    public void editMessage(MessageForm form, MessageEntity message){
        DatabaseReference messageRef = ref.child("messages/"+message.getMessageType());
        Map<String, Object> messageMap = new HashMap<>();
        messageMap.put(message.getMessageId()+"/messageContent", form.getMessageContent());
        messageRef.updateChildrenAsync(messageMap);
        messageMap.clear();
    }
    public void editEmail(String path, String change){
        DatabaseReference emailRef = ref.child("email");
        Map<String, Object> emailMap = new HashMap<>();
        emailMap.put(findEmail().get(0).getId()+"/"+path, change);
        emailRef.updateChildrenAsync(emailMap);
        emailMap.clear();
    }
    public void editAdmin(String path, String change){
        DatabaseReference adminRef = ref.child("admins");
        Map<String, Object> adminMap = new HashMap<>();
        adminMap.put(findAdmin().get(0).getId()+"/"+path, change);
        adminRef.updateChildrenAsync(adminMap);
        adminMap.clear();
    }
    public void logInAdmin(Admin admin){
        changeLogIn(admin, true);
    }
    public void logOutAdmin(Admin admin){
        changeLogIn(admin, false);
    }
    private void changeLogIn(Admin admin, Boolean status){
        DatabaseReference adminRef = ref.child("admins");
        Map<String, Object> adminMap = new HashMap<>();
        admin.setLoggedIn(status);
        adminMap.put(String.valueOf(admin.getId()), admin);
        adminRef.updateChildrenAsync(adminMap);
        adminMap.clear();
    }
    public void unlockPerson(Person person){
        changeLocked(person, false);
    }
    public void lockPerson(Person person){
        changeLocked(person, true);
    }
    private void changeLocked(Person person, Boolean status){
        DatabaseReference memberRef = ref.child("members");
        Map<String, Object> memberMap = new HashMap<>();
        person.setLocked(status);
        memberMap.put(String.valueOf(person.getPersonId()), person);
        memberRef.updateChildrenAsync(memberMap);
        memberMap.clear();
    }
    public void delete(String path, Integer id){
        DatabaseReference deleteRef = ref.child(path);
        Map<String, Object> deleteMap = new HashMap<>();
        deleteMap.put(String.valueOf(id), null);
        deleteRef.updateChildrenAsync(deleteMap);
        deleteMap.clear();
    }
}