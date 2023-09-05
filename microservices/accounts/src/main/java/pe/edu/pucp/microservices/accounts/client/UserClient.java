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
import pe.edu.pucp.microservices.accounts.dto.UserDto;

@Repository
public class UserClient {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${user.api.url}")
    private String usersApiUrl;

    @Value("${user.api.key}")
    private String usersApiKey;

    public UserClient(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public UserDto getUser(String userId) throws Exception {
        String url = usersApiUrl + userId;

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-api-key", usersApiKey);

        HttpEntity<?> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
        System.out.println("Response from <" + url + ">: <" + response + ">");

        HttpStatusCode statusCode = response.getStatusCode();

        if (statusCode == HttpStatus.OK) {
            String responseBody = response.getBody();
            return objectMapper.readValue(responseBody, UserDto.class);
        }
        else {
            System.out.println("Status is not 200");
            throw new Exception("Status is not 200");
        }
    }
}
