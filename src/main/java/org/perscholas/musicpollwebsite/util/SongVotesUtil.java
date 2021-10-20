package org.perscholas.musicpollwebsite.util;

import org.perscholas.musicpollwebsite.database.entity.Poll;
import org.perscholas.musicpollwebsite.database.entity.Song;

import java.util.Comparator;

public class SongVotesUtil {

    public Integer countVotes(Poll poll, Song song) {
        Integer votes = ((Long) poll.getVoteList().stream()
                .filter(vote -> vote.getSongId().equals(song.getId())).count()).intValue();
        return votes;
    }

}


