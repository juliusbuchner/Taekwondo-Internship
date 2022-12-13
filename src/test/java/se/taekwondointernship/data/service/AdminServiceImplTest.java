package se.taekwondointernship.data.service;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import se.taekwondointernship.data.models.entity.Admin;

class AdminServiceImplTest {

    @Test
    void create() {
        try {
            final Admin admin = new Admin(1, "Bamse", "Långben");
            JSONObject jsonAdmin = new JSONObject();
            jsonAdmin.put("id", admin.getId());
            jsonAdmin.put("username", admin.getUsername());
            jsonAdmin.put("password", admin.getPassword());
            JSONAssert.assertEquals("{id:1}", String.valueOf(jsonAdmin), false);
            JSONAssert.assertEquals("{username:\"Bamse\"}", String.valueOf(jsonAdmin), false);
            JSONAssert.assertEquals("{password:\"Långben\"}", String.valueOf(jsonAdmin), false);
            JSONAssert.assertEquals("{id:1, username:\"Bamse\"}", String.valueOf(jsonAdmin), false);
            JSONAssert.assertEquals("{id:1, password:\"Långben\"}", String.valueOf(jsonAdmin), false);
            JSONAssert.assertEquals("{id:1, username:\"Bamse\", password:\"Långben\"}", String.valueOf(jsonAdmin), true);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void editPassword() {
        try {
            final Admin adminStart = new Admin(1, "Bamse", "Långben");
            JSONObject jsonAdmin = new JSONObject();
            jsonAdmin.put("id", adminStart.getId());
            jsonAdmin.put("username", adminStart.getUsername());
            jsonAdmin.put("password", adminStart.getPassword());
            JSONAssert.assertEquals("{id:1}", String.valueOf(jsonAdmin), false);
            JSONAssert.assertEquals("{username:\"Bamse\"}", String.valueOf(jsonAdmin), false);
            JSONAssert.assertEquals("{password:\"Långben\"}", String.valueOf(jsonAdmin), false);
            JSONAssert.assertEquals("{id:1, username:\"Bamse\"}", String.valueOf(jsonAdmin), false);
            JSONAssert.assertEquals("{id:1, password:\"Långben\"}", String.valueOf(jsonAdmin), false);
            JSONAssert.assertEquals("{id:1, username:\"Bamse\", password:\"Långben\"}", String.valueOf(jsonAdmin), true);

            final Admin adminEnd = new Admin(1, "Bamse", "1234");
            jsonAdmin.replace("password", adminEnd.getPassword());
            JSONAssert.assertEquals("{id:1}", String.valueOf(jsonAdmin), false);
            JSONAssert.assertEquals("{username:\"Bamse\"}", String.valueOf(jsonAdmin), false);
            JSONAssert.assertEquals("{password:\"1234\"}", String.valueOf(jsonAdmin), false);
            JSONAssert.assertEquals("{id:1, username:\"Bamse\"}", String.valueOf(jsonAdmin), false);
            JSONAssert.assertEquals("{id:1, password:\"1234\"}", String.valueOf(jsonAdmin), false);
            JSONAssert.assertEquals("{id:1, username:\"Bamse\", password:\"1234\"}", String.valueOf(jsonAdmin), true);
        } catch (JSONException e){
            throw new RuntimeException(e);
        }
    }
    @Test
    void logIn(){
        Admin admin = new Admin(1, "Bamse", "Långben");
        Assertions.assertFalse(admin.isLoggedIn());
        admin.setLoggedIn(true);
        Assertions.assertTrue(admin.isLoggedIn());
    }

    @Test
    void logOut(){
        Admin admin = new Admin(1, "Bamse", "Långben", true);
        Assertions.assertTrue(admin.isLoggedIn());
        admin.setLoggedIn(false);
        Assertions.assertFalse(admin.isLoggedIn());
    }
}