package org.perscholas.musicpollwebsite.controller;

import org.perscholas.musicpollwebsite.database.entity.Poll;
import org.perscholas.musicpollwebsite.database.entity.Vote;
import org.perscholas.musicpollwebsite.database.repository.PollRepository;
import org.perscholas.musicpollwebsite.database.repository.VoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.perscholas.musicpollwebsite.util.VotesPerPollComparator;


import java.util.List;

@Controller
public class StatsController {

    @Autowired
    private PollRepository pollRepository;
    @Autowired
    private VoteRepository voteRepository;

    @GetMapping("/stats")
    public String greeting(Model model) {
        List<Poll> pollList = pollRepository.getAllByIdIsNotNull();
        Integer numberOfPolls = pollList.size();
        List<Vote> voteList = (List<Vote>) voteRepository.findAll();
        Integer totalNumberOfVotes = voteList.size();
        pollList.sort(new VotesPerPollComparator());
        Integer mostVotesInInSinglePoll = pollList.get(0).getVoteList().size();
        Poll pollWithMostVotes = pollList.get(0);
        model.addAttribute("numberOfPolls", numberOfPolls);
        model.addAttribute("totalNumberOfVotes", totalNumberOfVotes);
        model.addAttribute("mostVotesInInSinglePoll", mostVotesInInSinglePoll);
        model.addAttribute("pollWithMostVotes", pollWithMostVotes);
        return "stats";
    }
}
