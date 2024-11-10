package ru.yandex.practicum.catsgram.model;

import java.time.Instant;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import lombok.AllArgsConstructor;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = { "email" })
@AllArgsConstructor
public class User {
    Long id;
    String username;
    String email;
    String password;
    Instant registrationDate;
}
