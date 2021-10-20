package org.perscholas.musicpollwebsite.util;

import org.perscholas.musicpollwebsite.database.entity.Poll;

import java.util.Comparator;

public class VotesPerPollComparator implements Comparator<Poll> {

    @Override
    public int compare(Poll o1, Poll o2) {
        return Integer.compare(o2.getVoteList().size(), o1.getVoteList().size()) ;
    }
}