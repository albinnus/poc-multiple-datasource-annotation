package com.example.poc.infra.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "teste")
@Data
public class TesteEntity {

    @Id
    private Integer id;

    private String name;

}
