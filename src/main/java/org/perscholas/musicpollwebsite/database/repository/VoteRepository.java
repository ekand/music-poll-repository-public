package org.perscholas.musicpollwebsite.database.repository;

import org.perscholas.musicpollwebsite.database.entity.Vote;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface VoteRepository extends CrudRepository<Vote, Integer> {
    Optional<Vote> findByVoterUserIdAndPollId(Integer userId, Integer pollId);
}
