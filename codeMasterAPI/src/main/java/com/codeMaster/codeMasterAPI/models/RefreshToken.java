package com.codeMaster.codeMasterAPI.models;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Data
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    String token;

    private Instant createdDate;
}