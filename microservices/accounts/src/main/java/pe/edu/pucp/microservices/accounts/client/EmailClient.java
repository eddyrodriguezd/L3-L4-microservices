package pe.edu.pucp.microservices.accounts.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import pe.edu.pucp.microservices.accounts.dto.SendEmailDto;
import pe.edu.pucp.microservices.accounts.dto.UserDto;

@Repository
public class EmailClient {

    private final RestTemplate restTemplate;

    @Value("${email.api.url}")
    private String emailApiUrl;

    @Value("${email.api.key}")
    private String emailApiKey;

    public EmailClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void sendEmail(String email) {
        String url = emailApiUrl + "/send";

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-api-key", emailApiKey);

        SendEmailDto sendEmailDto = new SendEmailDto();
        sendEmailDto.setRecipient(email);
        sendEmailDto.setSubject("¡Bienvenido/a!");
        sendEmailDto.setBody("Gracias por crearte una cuenta con nosotros");

        HttpEntity<?> requestEntity = new HttpEntity<>(sendEmailDto, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        System.out.println("Response from <" + url + ">: <" + response + ">");

        HttpStatusCode statusCode = response.getStatusCode();

        if(statusCode == HttpStatus.OK) {
            System.out.println("Email sent");
        }
        else {
            System.out.println("Email not sent");
        }
    }
}
