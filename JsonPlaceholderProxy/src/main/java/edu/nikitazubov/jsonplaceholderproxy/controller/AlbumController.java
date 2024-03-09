package edu.nikitazubov.jsonplaceholderproxy.controller;

import edu.nikitazubov.jsonplaceholderproxy.model.Album;
import edu.nikitazubov.jsonplaceholderproxy.service.AlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/albums")
public class AlbumController {

    private final AlbumService albumService;

    @GetMapping("/")
    public List<Album> getAllAlbums() {
        return albumService.getAllAlbums();
    }

    @GetMapping("/{id}")
    public Album getAlbumById(@PathVariable Long id) {
        return albumService.getAlbumById(id);
    }

    @PostMapping("/")
    public Album addNewAlbum(@RequestBody Album album) {
        return albumService.addNewAlbum(album);
    }

    @PutMapping("/{id}")
    public Album updateAlbum(@RequestBody Album album) {
        return albumService.updateAlbum(album);
    }

    @DeleteMapping("/{id}")
    public String deleteAlbum(@PathVariable("id") Long id) {
        return albumService.deleteAlbum(id);
    }
}
