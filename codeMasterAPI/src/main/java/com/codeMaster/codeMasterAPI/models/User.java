package com.codeMaster.codeMasterAPI.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", nullable = false)
    private Long user_id;

    @NotEmpty
    private String fullName;

    @Email(message = "Email Id must be valid.")
    @Column(name = "email", unique = true)
    private String email;

    @NotEmpty
    @Size(min = 8,message = "Password must be at least 8 characters long")
    @JsonIgnore
    private String password;

    private LocalDateTime createdTime;
    private LocalDateTime lastLoggedInTime;

    @OneToMany(mappedBy = "solvedBy")
    Set<Submission> submissions;

    public User(){
        this.createdTime = LocalDateTime.now();
        this.lastLoggedInTime = createdTime;
        submissions=new HashSet<>();
    }



}
