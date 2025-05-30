package edu.eci.cvds.ECIBienestarGym.model;


import edu.eci.cvds.ECIBienestarGym.enums.Gender;
import edu.eci.cvds.ECIBienestarGym.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "users")
public class User {

    @Id
    private String id;
    private String name;
    @Indexed(unique = true)
    private String email;
    private String password;
    private Role role;
    private Gender gender;
    private boolean registered;
    private LocalDate registrationDate;

    public User(String id) {
        this.id = id;
    }
    public User(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
