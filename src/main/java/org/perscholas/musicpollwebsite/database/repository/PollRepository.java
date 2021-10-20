package org.perscholas.musicpollwebsite.database.repository;

import org.perscholas.musicpollwebsite.database.entity.Poll;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PollRepository extends CrudRepository<Poll, Integer> {
    List<Poll> getAllByIdIsNotNull();

    List<Poll> findAllByTitleContainingIgnoreCase(String titleQuery);
}
