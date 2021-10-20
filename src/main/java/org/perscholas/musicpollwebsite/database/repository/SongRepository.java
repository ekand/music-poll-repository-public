package org.perscholas.musicpollwebsite.database.repository;

import org.perscholas.musicpollwebsite.database.entity.Song;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SongRepository extends CrudRepository<Song, Integer>{
    List<Song> findAllByTitleContainingIgnoreCase(String title);
}
