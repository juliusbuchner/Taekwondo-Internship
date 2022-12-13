package se.taekwondointernship.data.service;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import se.taekwondointernship.data.models.entity.Person;

class PersonServiceImplTest {

    @Test
    void createParticipant() {
            final Person person = new Person(1, "Test", "Testsson",
                    "123456789", "Tester", "987654321",
                    "test@mail.com", "000101-0000", true);
            JSONObject jsonPerson = new JSONObject();
        try {
            jsonPerson.put("personId", person.getPersonId());
            jsonPerson.put("firstName", person.getFirstName());
            jsonPerson.put("lastName", person.getLastName());
            jsonPerson.put("phoneNumber", person.getPhoneNumber());
            jsonPerson.put("parentName", person.getParentName());
            jsonPerson.put("parentNumber", person.getParentNumber());
            jsonPerson.put("email", person.getEmail());
            jsonPerson.put("socialSecurityNumber", person.getSocialSecurityNumber());
            JSONAssert.assertEquals("{personId:1}", String.valueOf(jsonPerson), false);
            JSONAssert.assertEquals("{firstName:\"Test\"}", String.valueOf(jsonPerson), false);
            JSONAssert.assertEquals("{lastName:\"Testsson\"}", String.valueOf(jsonPerson), false);
            JSONAssert.assertEquals("{phoneNumber:\"123456789\"}", String.valueOf(jsonPerson), false);
            JSONAssert.assertEquals("{parentName:\"Tester\"}", String.valueOf(jsonPerson), false);
            JSONAssert.assertEquals("{parentNumber:\"987654321\"}", String.valueOf(jsonPerson), false);
            JSONAssert.assertEquals("{email:\"test@mail.com\"}", String.valueOf(jsonPerson), false);
            JSONAssert.assertEquals("{socialSecurityNumber:\"000101-0000\"}", String.valueOf(jsonPerson), false);
            JSONAssert.assertEquals("{personId:1, firstName: \"Test\"}", String.valueOf(jsonPerson), false);
            JSONAssert.assertEquals("{personId:1, lastName: \"Testsson\"}", String.valueOf(jsonPerson), false);
            JSONAssert.assertEquals("{personId:1, phoneNumber: \"123456789\"}", String.valueOf(jsonPerson), false);
            JSONAssert.assertEquals("{personId:1, parentName: \"Tester\"}", String.valueOf(jsonPerson), false);
            JSONAssert.assertEquals("{personId:1, parentNumber: \"987654321\"}", String.valueOf(jsonPerson), false);
            JSONAssert.assertEquals("{personId:1, email: \"test@mail.com\"}", String.valueOf(jsonPerson), false);
            JSONAssert.assertEquals("{personId:1, socialSecurityNumber: \"000101-0000\"}", String.valueOf(jsonPerson), false);
            JSONAssert.assertEquals("{personId:1, firstName: \"Test\", lastName:\"Testsson\"}", String.valueOf(jsonPerson), false);
            JSONAssert.assertEquals("{personId:1, firstName: \"Test\", phoneNumber:\"123456789\"}", String.valueOf(jsonPerson), false);
            JSONAssert.assertEquals("{personId:1, firstName: \"Test\", parentName:\"Tester\"}", String.valueOf(jsonPerson), false);
            JSONAssert.assertEquals("{personId:1, firstName: \"Test\", parentNumber:\"987654321\"}", String.valueOf(jsonPerson), false);
            JSONAssert.assertEquals("{personId:1, firstName: \"Test\", email:\"test@mail.com\"}", String.valueOf(jsonPerson), false);
            JSONAssert.assertEquals("{personId:1, firstName: \"Test\", socialSecurityNumber:\"000101-0000\"}",
                    String.valueOf(jsonPerson), false);
            JSONAssert.assertEquals("{personId:1, firstName: \"Test\", lastName:\"Testsson\"," +
                    "phoneNumber:\"123456789\"}", String.valueOf(jsonPerson), false);
            JSONAssert.assertEquals("{personId:1, firstName: \"Test\", lastName:\"Testsson\"," +
                    "parentName:\"Tester\"}", String.valueOf(jsonPerson), false);
            JSONAssert.assertEquals("{personId:1, firstName: \"Test\", lastName:\"Testsson\"," +
                    "parentNumber:\"987654321\"}", String.valueOf(jsonPerson), false);
            JSONAssert.assertEquals("{personId:1, firstName: \"Test\", lastName:\"Testsson\"," +
                    "email:\"test@mail.com\"}", String.valueOf(jsonPerson), false);
            JSONAssert.assertEquals("{personId:1, firstName: \"Test\", lastName:\"Testsson\"," +
                    "socialSecurityNumber:\"000101-0000\"}", String.valueOf(jsonPerson), false);
            JSONAssert.assertEquals("{personId:1, firstName: \"Test\", lastName:\"Testsson\"," +
                    "phoneNumber:\"123456789\", parentName:\"Tester\"}", String.valueOf(jsonPerson), false);
            JSONAssert.assertEquals("{personId:1, firstName: \"Test\", lastName:\"Testsson\"," +
                    "phoneNumber:\"123456789\", parentNumber:\"987654321\"}", String.valueOf(jsonPerson), false);
            JSONAssert.assertEquals("{personId:1, firstName: \"Test\", lastName:\"Testsson\"," +
                    "phoneNumber:\"123456789\", email:\"test@mail.com\"}", String.valueOf(jsonPerson), false);
            JSONAssert.assertEquals("{personId:1, firstName: \"Test\", lastName:\"Testsson\"," +
                    "phoneNumber:\"123456789\", socialSecurityNumber:\"000101-0000\"}", String.valueOf(jsonPerson), false);
            JSONAssert.assertEquals("{personId:1, firstName: \"Test\", lastName:\"Testsson\"," +
                    "phoneNumber:\"123456789\", parentName:\"Tester\", parentNumber:\"987654321\"}", String.valueOf(jsonPerson), false);
            JSONAssert.assertEquals("{personId:1, firstName: \"Test\", lastName:\"Testsson\"," +
                    "phoneNumber:\"123456789\", parentName:\"Tester\", email:\"test@mail.com\"}", String.valueOf(jsonPerson), false);
            JSONAssert.assertEquals("{personId:1, firstName: \"Test\", lastName:\"Testsson\"," +
                    "phoneNumber:\"123456789\", parentName:\"Tester\", socialSecurityNumber:\"000101-0000\"}", String.valueOf(jsonPerson), false);
            JSONAssert.assertEquals("{personId:1, firstName: \"Test\", lastName:\"Testsson\"," +
                    "phoneNumber:\"123456789\", parentName:\"Tester\", parentNumber:\"987654321\"," +
                    "socialSecurityNumber:\"000101-0000\"}", String.valueOf(jsonPerson), false);
            JSONAssert.assertEquals("{personId:1, firstName: \"Test\", lastName:\"Testsson\"," +
                    "phoneNumber:\"123456789\", parentName:\"Tester\", parentNumber:\"987654321\"," +
                    "email: \"test@mail.com\", socialSecurityNumber:\"000101-0000\"}", String.valueOf(jsonPerson), true);
            System.out.println("create: " + jsonPerson);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void findAll() {
        final Person person1 = new Person(1, "Test", "Testsson",
                "123456789", "Tester", "987654321",
                "test@mail.com", "000101-0000", true);

        final Person person2 = new Person(2, "Bob", "Bobsson",
                "214365879", "Bobber", "896745231",
                "bob@mail.com", "000101-0001", false);

        JSONObject jsonPerson1 = new JSONObject();
        JSONObject jsonPerson2 = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(jsonPerson1);
        jsonArray.add(jsonPerson2);
        try {
            jsonPerson1.put("personId", person1.getPersonId());
            jsonPerson1.put("firstName", person1.getFirstName());
            jsonPerson1.put("lastName", person1.getLastName());
            jsonPerson1.put("phoneNumber", person1.getPhoneNumber());
            jsonPerson1.put("parentName", person1.getParentName());
            jsonPerson1.put("parentNumber", person1.getParentNumber());
            jsonPerson1.put("email", person1.getEmail());
            jsonPerson1.put("socialSecurityNumber", person1.getSocialSecurityNumber());
            jsonPerson2.put("personId", person2.getPersonId());
            jsonPerson2.put("firstName", person2.getFirstName());
            jsonPerson2.put("lastName", person2.getLastName());
            jsonPerson2.put("phoneNumber", person2.getPhoneNumber());
            jsonPerson2.put("parentName", person2.getParentName());
            jsonPerson2.put("parentNumber", person2.getParentNumber());
            jsonPerson2.put("email", person2.getEmail());
            jsonPerson2.put("socialSecurityNumber", person2.getSocialSecurityNumber());
            JSONAssert.assertEquals("{personId:1}", String.valueOf(jsonArray.get(0)), false);
            JSONAssert.assertEquals("{firstName:\"Test\"}", String.valueOf(jsonArray.get(0)), false);
            JSONAssert.assertEquals("{lastName:\"Testsson\"}", String.valueOf(jsonArray.get(0)), false);
            JSONAssert.assertEquals("{phoneNumber:\"123456789\"}", String.valueOf(jsonArray.get(0)), false);
            JSONAssert.assertEquals("{parentName:\"Tester\"}", String.valueOf(jsonArray.get(0)), false);
            JSONAssert.assertEquals("{parentNumber:\"987654321\"}", String.valueOf(jsonArray.get(0)), false);
            JSONAssert.assertEquals("{email:\"test@mail.com\"}", String.valueOf(jsonArray.get(0)), false);
            JSONAssert.assertEquals("{socialSecurityNumber:\"000101-0000\"}", String.valueOf(jsonArray.get(0)), false);
            JSONAssert.assertEquals("{personId:1, firstName: \"Test\"}", String.valueOf(jsonArray.get(0)), false);
            JSONAssert.assertEquals("{personId:1, lastName: \"Testsson\"}", String.valueOf(jsonArray.get(0)), false);
            JSONAssert.assertEquals("{personId:1, phoneNumber: \"123456789\"}", String.valueOf(jsonArray.get(0)), false);
            JSONAssert.assertEquals("{personId:1, parentName: \"Tester\"}", String.valueOf(jsonArray.get(0)), false);
            JSONAssert.assertEquals("{personId:1, parentNumber: \"987654321\"}", String.valueOf(jsonArray.get(0)), false);
            JSONAssert.assertEquals("{personId:1, email: \"test@mail.com\"}", String.valueOf(jsonArray.get(0)), false);
            JSONAssert.assertEquals("{personId:1, socialSecurityNumber: \"000101-0000\"}", String.valueOf(jsonArray.get(0)), false);
            JSONAssert.assertEquals("{personId:1, firstName: \"Test\", lastName:\"Testsson\"}", String.valueOf(jsonArray.get(0)), false);
            JSONAssert.assertEquals("{personId:1, firstName: \"Test\", phoneNumber:\"123456789\"}", String.valueOf(jsonArray.get(0)), false);
            JSONAssert.assertEquals("{personId:1, firstName: \"Test\", parentName:\"Tester\"}", String.valueOf(jsonArray.get(0)), false);
            JSONAssert.assertEquals("{personId:1, firstName: \"Test\", parentNumber:\"987654321\"}", String.valueOf(jsonArray.get(0)), false);
            JSONAssert.assertEquals("{personId:1, firstName: \"Test\", email:\"test@mail.com\"}", String.valueOf(jsonArray.get(0)), false);
            JSONAssert.assertEquals("{personId:1, firstName: \"Test\", socialSecurityNumber:\"000101-0000\"}",
                    String.valueOf(jsonArray.get(0)), false);
            JSONAssert.assertEquals("{personId:1, firstName: \"Test\", lastName:\"Testsson\"," +
                    "phoneNumber:\"123456789\"}", String.valueOf(jsonArray.get(0)), false);
            JSONAssert.assertEquals("{personId:1, firstName: \"Test\", lastName:\"Testsson\"," +
                    "parentName:\"Tester\"}", String.valueOf(jsonArray.get(0)), false);
            JSONAssert.assertEquals("{personId:1, firstName: \"Test\", lastName:\"Testsson\"," +
                    "parentNumber:\"987654321\"}", String.valueOf(jsonArray.get(0)), false);
            JSONAssert.assertEquals("{personId:1, firstName: \"Test\", lastName:\"Testsson\"," +
                    "email:\"test@mail.com\"}", String.valueOf(jsonArray.get(0)), false);
            JSONAssert.assertEquals("{personId:1, firstName: \"Test\", lastName:\"Testsson\"," +
                    "socialSecurityNumber:\"000101-0000\"}", String.valueOf(jsonArray.get(0)), false);
            JSONAssert.assertEquals("{personId:1, firstName: \"Test\", lastName:\"Testsson\"," +
                    "phoneNumber:\"123456789\", parentName:\"Tester\"}", String.valueOf(jsonArray.get(0)), false);
            JSONAssert.assertEquals("{personId:1, firstName: \"Test\", lastName:\"Testsson\"," +
                    "phoneNumber:\"123456789\", parentNumber:\"987654321\"}", String.valueOf(jsonArray.get(0)), false);
            JSONAssert.assertEquals("{personId:1, firstName: \"Test\", lastName:\"Testsson\"," +
                    "phoneNumber:\"123456789\", email:\"test@mail.com\"}", String.valueOf(jsonArray.get(0)), false);
            JSONAssert.assertEquals("{personId:1, firstName: \"Test\", lastName:\"Testsson\"," +
                    "phoneNumber:\"123456789\", socialSecurityNumber:\"000101-0000\"}", String.valueOf(jsonArray.get(0)), false);
            JSONAssert.assertEquals("{personId:1, firstName: \"Test\", lastName:\"Testsson\"," +
                    "phoneNumber:\"123456789\", parentName:\"Tester\", parentNumber:\"987654321\"}", String.valueOf(jsonArray.get(0)), false);
            JSONAssert.assertEquals("{personId:1, firstName: \"Test\", lastName:\"Testsson\"," +
                    "phoneNumber:\"123456789\", parentName:\"Tester\", email:\"test@mail.com\"}", String.valueOf(jsonArray.get(0)), false);
            JSONAssert.assertEquals("{personId:1, firstName: \"Test\", lastName:\"Testsson\"," +
                    "phoneNumber:\"123456789\", parentName:\"Tester\", socialSecurityNumber:\"000101-0000\"}", String.valueOf(jsonArray.get(0)), false);
            JSONAssert.assertEquals("{personId:1, firstName: \"Test\", lastName:\"Testsson\"," +
                    "phoneNumber:\"123456789\", parentName:\"Tester\", parentNumber:\"987654321\"," +
                    "socialSecurityNumber:\"000101-0000\"}", String.valueOf(jsonArray.get(0)), false);
            JSONAssert.assertEquals("{personId:1, firstName: \"Test\", lastName:\"Testsson\"," +
                    "phoneNumber:\"123456789\", parentName:\"Tester\", parentNumber:\"987654321\"," +
                    "email: \"test@mail.com\", socialSecurityNumber:\"000101-0000\"}", String.valueOf(jsonArray.get(0)), true);
            JSONAssert.assertEquals("{personId:2}", String.valueOf(jsonArray.get(1)), false);
            JSONAssert.assertEquals("{firstName:\"Bob\"}", String.valueOf(jsonArray.get(1)), false);
            JSONAssert.assertEquals("{lastName:\"Bobsson\"}", String.valueOf(jsonArray.get(1)), false);
            JSONAssert.assertEquals("{phoneNumber:\"214365879\"}", String.valueOf(jsonArray.get(1)), false);
            JSONAssert.assertEquals("{parentName:\"Bobber\"}", String.valueOf(jsonArray.get(1)), false);
            JSONAssert.assertEquals("{parentNumber:\"896745231\"}", String.valueOf(jsonArray.get(1)), false);
            JSONAssert.assertEquals("{email:\"bob@mail.com\"}", String.valueOf(jsonArray.get(1)), false);
            JSONAssert.assertEquals("{socialSecurityNumber:\"000101-0001\"}", String.valueOf(jsonArray.get(1)), false);
            JSONAssert.assertEquals("{personId:2, firstName: \"Bob\"}", String.valueOf(jsonArray.get(1)), false);
            JSONAssert.assertEquals("{personId:2, lastName: \"Bobsson\"}", String.valueOf(jsonArray.get(1)), false);
            JSONAssert.assertEquals("{personId:2, phoneNumber: \"214365879\"}", String.valueOf(jsonArray.get(1)), false);
            JSONAssert.assertEquals("{personId:2, parentName: \"Bobber\"}", String.valueOf(jsonArray.get(1)), false);
            JSONAssert.assertEquals("{personId:2, parentNumber: \"896745231\"}", String.valueOf(jsonArray.get(1)), false);
            JSONAssert.assertEquals("{personId:2, email: \"bob@mail.com\"}", String.valueOf(jsonArray.get(1)), false);
            JSONAssert.assertEquals("{personId:2, socialSecurityNumber: \"000101-0001\"}", String.valueOf(jsonArray.get(1)), false);
            JSONAssert.assertEquals("{personId:2, firstName: \"Bob\", lastName:\"Bobsson\"}", String.valueOf(jsonArray.get(1)), false);
            JSONAssert.assertEquals("{personId:2, firstName: \"Bob\", phoneNumber:\"214365879\"}", String.valueOf(jsonArray.get(1)), false);
            JSONAssert.assertEquals("{personId:2, firstName: \"Bob\", parentName:\"Bobber\"}", String.valueOf(jsonArray.get(1)), false);
            JSONAssert.assertEquals("{personId:2, firstName: \"Bob\", parentNumber:\"896745231\"}", String.valueOf(jsonArray.get(1)), false);
            JSONAssert.assertEquals("{personId:2, firstName: \"Bob\", email:\"bob@mail.com\"}", String.valueOf(jsonArray.get(1)), false);
            JSONAssert.assertEquals("{personId:2, firstName: \"Bob\", socialSecurityNumber:\"000101-0001\"}",
                    String.valueOf(jsonArray.get(1)), false);
            JSONAssert.assertEquals("{personId:2, firstName: \"Bob\", lastName:\"Bobsson\"," +
                    "phoneNumber:\"214365879\"}", String.valueOf(jsonArray.get(1)), false);
            JSONAssert.assertEquals("{personId:2, firstName: \"Bob\", lastName:\"Bobsson\"," +
                    "parentName:\"Bobber\"}", String.valueOf(jsonArray.get(1)), false);
            JSONAssert.assertEquals("{personId:2, firstName: \"Bob\", lastName:\"Bobsson\"," +
                    "parentNumber:\"896745231\"}", String.valueOf(jsonArray.get(1)), false);
            JSONAssert.assertEquals("{personId:2, firstName: \"Bob\", lastName:\"Bobsson\"," +
                    "email:\"bob@mail.com\"}", String.valueOf(jsonArray.get(1)), false);
            JSONAssert.assertEquals("{personId:2, firstName: \"Bob\", lastName:\"Bobsson\"," +
                    "socialSecurityNumber:\"000101-0001\"}", String.valueOf(jsonArray.get(1)), false);
            JSONAssert.assertEquals("{personId:2, firstName: \"Bob\", lastName:\"Bobsson\"," +
                    "phoneNumber:\"214365879\", parentName:\"Bobber\"}", String.valueOf(jsonArray.get(1)), false);
            JSONAssert.assertEquals("{personId:2, firstName: \"Bob\", lastName:\"Bobsson\"," +
                    "phoneNumber:\"214365879\", parentNumber:\"896745231\"}", String.valueOf(jsonArray.get(1)), false);
            JSONAssert.assertEquals("{personId:2, firstName: \"Bob\", lastName:\"Bobsson\"," +
                    "phoneNumber:\"214365879\", email:\"bob@mail.com\"}", String.valueOf(jsonArray.get(1)), false);
            JSONAssert.assertEquals("{personId:2, firstName: \"Bob\", lastName:\"Bobsson\"," +
                    "phoneNumber:\"214365879\", socialSecurityNumber:\"000101-0001\"}", String.valueOf(jsonArray.get(1)), false);
            JSONAssert.assertEquals("{personId:2, firstName: \"Bob\", lastName:\"Bobsson\"," +
                    "phoneNumber:\"214365879\", parentName:\"Bobber\", parentNumber:\"896745231\"}", String.valueOf(jsonArray.get(1)), false);
            JSONAssert.assertEquals("{personId:2, firstName: \"Bob\", lastName:\"Bobsson\"," +
                    "phoneNumber:\"214365879\", parentName:\"Bobber\", email:\"bob@mail.com\"}", String.valueOf(jsonArray.get(1)), false);
            JSONAssert.assertEquals("{personId:2, firstName: \"Bob\", lastName:\"Bobsson\"," +
                    "phoneNumber:\"214365879\", parentName:\"Bobber\", socialSecurityNumber:\"000101-0001\"}", String.valueOf(jsonArray.get(1)), false);
            JSONAssert.assertEquals("{personId:2, firstName: \"Bob\", lastName:\"Bobsson\"," +
                    "phoneNumber:\"214365879\", parentName:\"Bobber\", parentNumber:\"896745231\"," +
                    "socialSecurityNumber:\"000101-0001\"}", String.valueOf(jsonArray.get(1)), false);
            JSONAssert.assertEquals("{personId:2, firstName: \"Bob\", lastName:\"Bobsson\"," +
                    "phoneNumber:\"214365879\", parentName:\"Bobber\", parentNumber:\"896745231\"," +
                    "email: \"bob@mail.com\", socialSecurityNumber:\"000101-0001\"}", String.valueOf(jsonArray.get(1)), true);
            System.out.println("findAll: " + jsonPerson1 + "\n" + jsonPerson2);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}