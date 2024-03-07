package edu.nikitazubov.jsonplaceholderproxy.service;

import edu.nikitazubov.jsonplaceholderproxy.entity.Album;

import java.util.List;

public interface AlbumService {
    public List<Album> getAllAlbums();

    public Album getAlbumById(Long id);

    public Album addNewAlbum(Album album);

    public Album updateAlbum(Album album);

    public String deleteAlbum(Long id);
}
