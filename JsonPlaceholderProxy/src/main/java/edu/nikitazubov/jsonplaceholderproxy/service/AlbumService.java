package edu.nikitazubov.jsonplaceholderproxy.service;

import edu.nikitazubov.jsonplaceholderproxy.entity.Album;

import java.util.List;

public interface AlbumService {
    List<Album> getAllAlbums();

    Album getAlbumById(Long id);

    Album addNewAlbum(Album album);

    Album updateAlbum(Album album);

    String deleteAlbum(Long id);
}
