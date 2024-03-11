package edu.nikitazubov.jsonplaceholderproxy.service;

import edu.nikitazubov.jsonplaceholderproxy.model.Album;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;

import java.util.Objects;

@SpringBootTest
public class AlbumServiceTest {

    @Autowired
    AlbumService albumService;

    @Autowired
    CacheManager cacheManager;

    private final static Album TEST_ALBUM = new Album(1L, "Album");

    @BeforeEach
    @CacheEvict(value = "albums", allEntries = true)
    public void clearCache() {
    }

    @Test
    public void getAllAlbumsCaching() {
        String albums = albumService.getAllAlbums().toString();
        String cached = Objects.requireNonNull(cacheManager.getCache("albums")).getNativeCache().toString();
        Assertions.assertTrue(cached.contains(albums));
    }

    @Test
    public void addNewAlbumCaching() {
        Album album = albumService.addNewAlbum(TEST_ALBUM);
        Album cachedAlbum = albumService.getAlbumById(album.getId());
        Assertions.assertEquals(album, cachedAlbum);
    }

    @Test
    public void updateAlbumCaching() {
        Album album = albumService.getAlbumById(1L);
        album.setUserId(2L);
        album.setTitle("UPDATED");
        albumService.updateAlbum(album);
        Album cachedUpdatedAlbum = albumService.getAlbumById(album.getId());
        Assertions.assertEquals(album, cachedUpdatedAlbum);
    }

    @Test
    public void deleteAlbumCaching() {
        Album album = albumService.addNewAlbum(TEST_ALBUM);
        Assertions.assertNotNull(albumService.getAlbumById(album.getId()));
        albumService.deleteAlbum(album.getId());
        Assertions.assertNull(albumService.getAlbumById(album.getId()));
    }
}
