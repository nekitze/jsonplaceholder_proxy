package edu.nikitazubov.jsonplaceholderproxy.service;

import edu.nikitazubov.jsonplaceholderproxy.entity.Album;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class AlbumServiceImpl implements AlbumService {

    private static final String TARGET_URL = "https://jsonplaceholder.typicode.com/albums/";
    private final RestTemplate restTemplate;

    public AlbumServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public List<Album> getAllAlbums() {
        ResponseEntity<List<Album>> response = restTemplate.exchange(
                TARGET_URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Album>>() {
                }
        );
        return response.getBody();
    }

    @Override
    public Album getAlbumById(Long id) {
        return restTemplate.getForObject(TARGET_URL + id, Album.class);
    }

    @Override
    public Album addNewAlbum(Album album) {
        return restTemplate.postForObject(TARGET_URL, album, Album.class);
    }

    @Override
    public Album updateAlbum(Album album) {
        restTemplate.put(TARGET_URL, album);
        return album;
    }

    @Override
    public String deleteAlbum(Long id) {
        restTemplate.delete(TARGET_URL + id);
        return "Album with id=" + id + " has been deleted.";
    }
}
