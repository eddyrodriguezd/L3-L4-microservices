package pe.edu.pucp.microservices.transactions.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionCreateDto {
    private String accountId;
    private double amount;
}
