package com.example.bankmanagement.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.catalina.User;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Employee {
    @Id
    private Integer id;
    @Column
    private String position;
    @Column
    private double salary;

    @OneToOne
    @MapsId
    @JsonIgnore
    private MyUser myUser;
}
