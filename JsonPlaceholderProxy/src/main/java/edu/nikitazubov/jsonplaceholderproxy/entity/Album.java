package edu.nikitazubov.jsonplaceholderproxy.entity;

public class Album {
    private Long userId;
    private Long id;
    private String title;

    public Album() {
    }

    public Album(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
