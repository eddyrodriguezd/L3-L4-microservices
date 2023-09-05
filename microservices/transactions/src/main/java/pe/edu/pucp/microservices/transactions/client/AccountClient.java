package pe.edu.pucp.microservices.transactions.client;

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
import pe.edu.pucp.microservices.transactions.dto.AccountDto;
import pe.edu.pucp.microservices.transactions.dto.UpdateBalanceDto;

@Repository
public class AccountClient {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${account.api.url}")
    private String accountsApiUrl;

    public AccountClient(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public AccountDto getAccount(String accountId) throws Exception {
        String url = accountsApiUrl + accountId;

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
        System.out.println("Response from <" + url + ">: <" + response + ">");

        HttpStatusCode statusCode = response.getStatusCode();

        if (statusCode == HttpStatus.OK) {
            String responseBody = response.getBody();
            return objectMapper.readValue(responseBody, AccountDto.class);
        }
        else {
            System.out.println("Status is not 200");
            throw new Exception("Status is not 200");
        }
    }

    public void updateAccountBalance(String accountId, double balance) throws Exception {
        String url = accountsApiUrl + accountId;

        UpdateBalanceDto updateBalanceDto = new UpdateBalanceDto();
        updateBalanceDto.setBalance(balance);

        HttpEntity<?> requestEntity = new HttpEntity<>(updateBalanceDto, null);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);
        System.out.println("Response from <" + url + ">: <" + response + ">");

        HttpStatusCode statusCode = response.getStatusCode();

        if (statusCode == HttpStatus.OK) {
            System.out.println("Balance updated");
        }
        else {
            System.out.println("Status is not 200");
            throw new Exception("Status is not 200");
        }
    }
}
