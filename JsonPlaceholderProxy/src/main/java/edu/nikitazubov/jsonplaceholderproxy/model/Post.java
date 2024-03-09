package edu.nikitazubov.jsonplaceholderproxy.model;

import lombok.*;

import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Post {
    private Long id;
    private Long userId;
    private String title;
    private String body;

    public Post(Long userId, String title, String body) {
        this.userId = userId;
        this.title = title;
        this.body = body;
    }
}
