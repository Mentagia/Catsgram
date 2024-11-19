package ru.yandex.practicum.catsgram.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.catsgram.exception.ConditionsNotMetException;
import ru.yandex.practicum.catsgram.exception.NotFoundException;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.model.SortOrder;

import java.time.Instant;
import java.util.*;


@Service
public class PostService {
    private final UserService userService;

    @Autowired
    public PostService(UserService userService) {
        this.userService = userService;
    }

    private final Map<Long, Post> posts = new HashMap<>();

    private final Comparator<Post> postDateComparator = Comparator.comparing(Post::getPostDate);

    public Collection<Post> findAll(SortOrder sort, int from, int size) {
        return posts.values()
                .stream()
                .sorted(sort.equals(SortOrder.ASCENDING) ?
                        postDateComparator : postDateComparator.reversed())
                .skip(from)
                .limit(size)
                .toList();
    }

    public Post create(Post post) {
        if (post.getDescription() == null || post.getDescription().isBlank()) {
            throw new ConditionsNotMetException("Описание не может быть пустым");
        }
        Long authorId = post.getAuthorId();

        if (userService.findUserById(authorId).isEmpty()) {
            throw new ConditionsNotMetException("«Автор с id = " + authorId + " не найден»");
        }

        post.setId(getNextId());
        post.setPostDate(Instant.now());
        posts.put(post.getId(), post);
        return post;
    }

    public Post update(Post newPost) {
        if (newPost.getId() == null) {
            throw new ConditionsNotMetException("Id должен быть указан");
        }
        if (posts.containsKey(newPost.getId())) {
            Post oldPost = posts.get(newPost.getId());
            if (newPost.getDescription() == null || newPost.getDescription().isBlank()) {
                throw new ConditionsNotMetException("Описание не может быть пустым");
            }
            oldPost.setDescription(newPost.getDescription());
            return oldPost;
        }
        throw new NotFoundException("Пост с id = " + newPost.getId() + " не найден");
    }

    private long getNextId() {
        long currentMaxId = posts.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }

    public Optional<Post> findPostById(Long postId) {
        if (postId == null) {
            throw new ConditionsNotMetException("Id должен быть указан");
        }

        Optional<Post> post =  posts.entrySet().stream()
                .filter(e -> postId.equals(e.getKey()))
                .map(Map.Entry::getValue).findFirst();

        if (post.isEmpty()) {
            throw new ConditionsNotMetException(String.format(
                    "Пост с id = %s не найден", postId));
        }

        return post;
    }
}