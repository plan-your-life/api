package com.planyourlifeapp.api;

import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class ApiApplicationTests {


    private RestTemplate restTemplate = new RestTemplate();


    private String UrlEncodedBuilder(List<String[]> list) {
        String returnString = "";
        for (String[] param : list) {
            returnString += param[0] + "=" + param[1] + "&";
        }

        return returnString;
    }

    @Test
    public void testOauthToken() {

        String plainCreds = "client:topsecret";
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);

        List<String[]> formData = new ArrayList();

        formData.add(new String[]{"username", "Lukas"});
        formData.add(new String[]{"password", "test123"});
        formData.add(new String[]{"grant_type", "password"});

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization", "Basic " + base64Creds);
        HttpEntity<Object> reqData = new HttpEntity<>(UrlEncodedBuilder(formData), headers);

        ResponseEntity<Object> entity = restTemplate.exchange("http://localhost:8080/oauth/token", HttpMethod.POST,
                reqData,
                Object.class);


    }


}
