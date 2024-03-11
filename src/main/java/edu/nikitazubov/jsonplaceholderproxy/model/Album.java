package edu.nikitazubov.jsonplaceholderproxy.model;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Album {
    private Long id;
    private Long userId;
    private String title;

    public Album(Long userId, String title) {
        this.userId = userId;
        this.title = title;
    }
}
