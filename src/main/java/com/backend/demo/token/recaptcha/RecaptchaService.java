package com.backend.demo.token.recaptcha;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor

public class RecaptchaService {

    public Recaptcha verifyToken(String token) {
        final String apiKey = "6LcFanEeAAAAAKWD5e43ai0hd1lRw7uAbJVv1Krc";

        final String uri = "https://www.google.com/recaptcha/api/siteverify?secret=" + apiKey + "&response=" + token;

        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.getForObject(uri, Recaptcha.class);


    }


}
