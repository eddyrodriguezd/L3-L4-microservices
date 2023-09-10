package pe.edu.pucp.microservices.accounts.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Transient;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;
    private double balance;
    private LocalDateTime createdAt;

    @Transient
    private int port;

    @PrePersist
    private void prePersist() {
        createdAt = LocalDateTime.now();
    }

}
