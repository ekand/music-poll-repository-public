package org.perscholas.musicpollwebsite.database.loader;

import org.perscholas.musicpollwebsite.controller.HomeController;
import org.perscholas.musicpollwebsite.database.repository.*;
import org.perscholas.musicpollwebsite.database.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component  // uncomment this to load data in a fresh database
class DataLoader {
    Logger logger = LoggerFactory.getLogger(DataLoader.class);

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PollRepository pollRepository;
    private final SongRepository songRepository;
    private final VoteRepository voteRepository;

    public DataLoader(UserRepository userRepository, UserRoleRepository userRoleRepository, PollRepository pollRepository, SongRepository songRepository, VoteRepository voteRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.pollRepository = pollRepository;
        this.songRepository = songRepository;
        this.voteRepository = voteRepository;
    }

    @PostConstruct
    private void loadData() {
        logger.info("checking if user id 1 is present in database");
        if (userRepository.findById(1).isPresent()) {
            logger.info("user 1 found; exiting the data loader");
            return;
        }
        try {
            logger.info("user 1 not found; loading data into the database");


            User user1 = new User("admin", "$2a$12$8Bw4ciRYTDa2tzad5uwK4uat9Ps0Esfzt8j/5EX5d53GWiAs7rzvG");
            userRepository.save(user1);
            UserRole userRoleUser1User = new UserRole("USER", user1);
            userRoleRepository.save(userRoleUser1User);
            UserRole userRoleUser1Admin = new UserRole("ADMIN", user1);
            userRoleRepository.save(userRoleUser1Admin);
            User user2 = new User("Jerry", "$2a$12$bu.7.555Cp.H45OWkRGmAud1uDbuThevyixWgRQMT5Mfxz5O0TORu");
            userRepository.save(user2);
            UserRole userRoleUser2User = new UserRole("USER", user2);
            userRoleRepository.save(userRoleUser2User);

            User user3 = new User("Bob", "$2a$12$zEwu.qWnFOIHo1dtnt5qXePen2y6vxFPTuKBW5jpIAUOQRYHmHPI2");
            userRepository.save(user3);
            userRoleRepository.save(new UserRole("USER", user3));

            User user4 = new User("Sally", "$2a$12$blbPse75lfJOOH33yfxDxup35XROenReU.aBTHrA82VduHjyOGsfm");
            userRepository.save(user4);
            userRoleRepository.save(new UserRole("USER", user4));

            // poll one with votes
            Song SongOneA = new Song("Californication");
            Song songOneB = new Song("Under the Bridge");
            Song songOneC = new Song("Can't Stop");
            List<Song> songListOne = addSongs(SongOneA, songOneB, songOneC);
            Poll poll1 = new Poll("Red Hot Chili Peppers songs", songListOne);
            pollRepository.save(poll1);
            Vote voteOne = new Vote(poll1.getId(), SongOneA.getId(), user1.getId());
            Vote voteTwo = new Vote(poll1.getId(), songOneC.getId(), user2.getId());
            voteRepository.save(voteOne);
            voteRepository.save(voteTwo);

            // poll two
            List<Song> songListTwo = addSongs(new Song("Good Times Bad Times"), new Song("Come Together"), new Song("Baba O'Riley"));
            Poll poll2 = new Poll("Classic Rock", songListTwo);
            pollRepository.save(poll2);

            List<Song> songListThree = addSongs(new Song("All The Small Things"), new Song("Basketcase"), new Song("The Kids Aren't Alright"));
            Poll poll3 = new Poll("Punk Rock", songListThree);
            pollRepository.save(poll3);


        } catch (Exception e) {
            logger.warn(String.valueOf(e));
        }

    }

    private List<Song> addSongs(Song songTwoA, Song songTwoB, Song songTwoC) {
        songRepository.save(songTwoA);
        songRepository.save(songTwoB);
        songRepository.save(songTwoC);
        List<Song>  songList = new ArrayList<>();
        songList.add(songTwoA);
        songList.add(songTwoB);
        songList.add(songTwoC);
        return songList;
    }
}