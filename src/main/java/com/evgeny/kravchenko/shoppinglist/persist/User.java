package com.evgeny.kravchenko.shoppinglist.persist;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true, nullable = false)
    String email;

    @Column(unique = true, nullable = false)
    String username;

    @Column(nullable = false)
    String password;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    List<ShoppingItem> shoppingItems;

    @Column(nullable = false)
    String isEnable = "false";

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role")
    Role role;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    Status status;
}
