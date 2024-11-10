package ru.yandex.practicum.catsgram.model;
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
public class Image {
    Long id;
    long postId;
    String originalFileName;
    String filePath;
}
