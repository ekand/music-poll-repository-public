package org.perscholas.musicpollwebsite;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.perscholas.musicpollwebsite.database.entity.Poll;
import org.perscholas.musicpollwebsite.database.entity.Song;
import org.perscholas.musicpollwebsite.database.entity.Vote;
import org.perscholas.musicpollwebsite.database.repository.PollRepository;
import org.perscholas.musicpollwebsite.database.repository.UserRepository;
import org.perscholas.musicpollwebsite.database.repository.VoteRepository;
import org.perscholas.musicpollwebsite.util.SongVotesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MusicPollWebsiteDataTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private VoteRepository voteRepository;


    @Test
    @Order(1)
    void userOneExists() {
        assert(userRepository.findById(1).isPresent());
    }

    @Test
    @Order(2)
    void pollOneExists() {
        assert(pollRepository.findById(1).isPresent());
    }

    @Test
    @Order(3)
    @Transactional
    void pollOneContainsThreeSongs() {
        Poll poll = pollRepository.findById(1).get();
        List<Song> songList = poll.getSongList();
        Assertions.assertEquals(3, songList.size());
    }

    @Test
    @Order(4)
    @Transactional
    void songWithIdOneHasOneVote() {
        Poll poll = pollRepository.findById(1).get();
        Song songA = poll.getSongList().get(0);
        Integer voteCount = new SongVotesUtil().countVotes(poll, songA);
        Assertions.assertEquals(1, voteCount);
    }

    @Test
    @Order(5)
    void addVoteForSongWithIdOne() {
        Integer pollId = 1;
        Integer songId = 1;
        Integer userId = 3;
        Vote vote = new Vote(pollId, songId, userId);
        voteRepository.save(vote);
    }

    @Test
    @Order(6)
    @Transactional
    void songWithIdOneOneHasTwoVotes() {
        Poll poll = pollRepository.findById(1).get();
        Song songA = poll.getSongList().get(0);
        Integer voteCount = new SongVotesUtil().countVotes(poll, songA);
        Assertions.assertEquals(2, voteCount);
    }

}
