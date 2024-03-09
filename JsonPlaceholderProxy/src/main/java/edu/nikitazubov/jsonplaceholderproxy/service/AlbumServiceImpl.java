package edu.nikitazubov.jsonplaceholderproxy.service;

import edu.nikitazubov.jsonplaceholderproxy.model.Album;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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

    @Cacheable("albums")
    @Override
    public List<Album> getAllAlbums() {
        ResponseEntity<List<Album>> response = restTemplate.exchange(
                TARGET_URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        return response.getBody();
    }

    @Cacheable(value = "albums", key = "#id")
    @Override
    public Album getAlbumById(Long id) {
        try {
            return restTemplate.getForObject(TARGET_URL + id, Album.class);
        } catch (Exception e) {
            return null;
        }
    }

    @CachePut(value = "albums", key = "#result.id")
    @Override
    public Album addNewAlbum(Album album) {
        return restTemplate.postForObject(TARGET_URL, album, Album.class);
    }

    @CachePut(value = "albums", key = "#album.id")
    @Override
    public Album updateAlbum(Album album) {
        restTemplate.put(TARGET_URL + album.getId(), album);
        return album;
    }

    @CacheEvict(value = "albums", key = "#id")
    @Override
    public String deleteAlbum(Long id) {
        restTemplate.delete(TARGET_URL + id);
        return "Album deleted.";
    }
}
