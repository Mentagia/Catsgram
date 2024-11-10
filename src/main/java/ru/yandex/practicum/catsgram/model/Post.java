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
@EqualsAndHashCode(of = { "id" })
@AllArgsConstructor
public class Post {
    Long id;
    long authorId;
    String description;
    Instant postDate;
}
