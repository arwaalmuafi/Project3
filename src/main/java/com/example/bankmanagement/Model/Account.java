package com.example.bankmanagement.Model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String accountNumber;
    @Column
    private Double balance;
    @Column
    private Boolean isActive = false;

    @ManyToOne
    private Customer customer;

}
