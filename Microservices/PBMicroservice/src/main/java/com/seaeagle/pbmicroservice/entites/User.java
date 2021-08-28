package com.seaeagle.pbmicroservice.entites;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.annotation.Scope;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity(name="User")
@Table(
        name="users",
        uniqueConstraints = { //It says that chat_id column should be unique
                @UniqueConstraint(name = "users_username_unique", columnNames = "username"),
                @UniqueConstraint(name = "users_wallet_unique", columnNames = "wallet")
        }
)
@Scope(value = "prototype") //Do i really need prototype?
@ToString
@NoArgsConstructor
public class User {

    @SequenceGenerator(
            name = "users_sequence",
            sequenceName = "users_sequence",
            allocationSize = 1
    )

    @Id
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "users_sequence"
    )
    @Column(
            name = "id",
            updatable = false,
            nullable = false
    )

    @Getter
    @Setter
    private Long id;

    @Column(
            name = "name",
            nullable = false,
            columnDefinition = "varchar(256)"
    )
    @Getter
    @Setter
    private String name;

    @Column(
            name = "username",
            nullable = false,
            columnDefinition = "varchar(256)"
    )
    @Getter
    @Setter
    private String username;

    @Column(
            name = "wallet",
            nullable = false,
            columnDefinition = "varchar(256)"
    )
    @Getter
    @Setter
    private String wallet;

    public User(String name, String username, String wallet) {
        this.name = name;
        this.username = username;
        this.wallet = wallet;
    }
}
