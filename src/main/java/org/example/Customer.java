package org.example;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Setter
    @Getter
    private String phoneNumber;
    private String email;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders;

}
