package com.rost.models;


import com.rost.security.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 100, message = "Имя должно быть от 2 до 100 символов")
    @Column(name = "username")
    private String username;

    @Min(value = 1900, message = "Год рождения должен быть ≥ 1904") //Люсиль Рэндон, Франция
    private int yearOfBirth;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
}