package org.perscholas.musicpollwebsite.controller;

import org.perscholas.musicpollwebsite.database.repository.PollRepository;
import org.perscholas.musicpollwebsite.database.repository.UserRepository;
import org.perscholas.musicpollwebsite.database.repository.VoteRepository;
import org.perscholas.musicpollwebsite.database.entity.Poll;
import org.perscholas.musicpollwebsite.database.entity.User;
import org.perscholas.musicpollwebsite.database.entity.Vote;
import org.perscholas.musicpollwebsite.exceptions.PollNotFoundException;
import org.perscholas.musicpollwebsite.util.SongVotesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class ViewPollController {

    Logger logger = LoggerFactory.getLogger(ViewPollController.class);

    private final PollRepository pollRepository;
    private final UserRepository userRepository;
    private final VoteRepository voteRepository;

    public ViewPollController(PollRepository pollRepository, UserRepository userRepository, VoteRepository voteRepository) {
        this.pollRepository = pollRepository;
        this.userRepository = userRepository;
        this.voteRepository = voteRepository;
    }

    @GetMapping("/viewpoll")
    public ModelAndView viewPoll(@RequestParam(name="id") Integer pollId,
                                 @RequestParam(name="voteSaveError", required = false) String voteSaveError) throws Exception, PollNotFoundException {
        ModelAndView modelAndView = new ModelAndView();
        SongVotesUtil songVotesUtil = new SongVotesUtil();
        Optional<Poll> optionalPoll = pollRepository.findById(pollId);
        if (optionalPoll.isPresent()) {
            modelAndView.addObject("poll", optionalPoll.get());
            modelAndView.addObject("songvotesutil", songVotesUtil);
            if (voteSaveError != null) {
                modelAndView.addObject("voteSaveErrorMessage", "Notice: you have already voted in this poll.");
            }
        } else {
            throw new PollNotFoundException("poll not found");
        }
        modelAndView.setViewName("viewpoll");
        return modelAndView;
    }

    @GetMapping("/submitvote")
    public ModelAndView submitVote(@RequestParam(name="pollid") Integer pollId,
                                   @RequestParam(name="songid") Integer songId) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User user = userRepository.findByUsername(currentPrincipalName).get();
        Integer userId = user.getId();
        Vote vote = new Vote(pollId, songId, userId);
        String voteSaveError = null;
        Optional<Vote> optionalExistingVote = voteRepository.findByVoterUserIdAndPollId(userId, pollId);
        if (optionalExistingVote.isEmpty()) {

            voteRepository.save(vote);
            logger.info("vote saved: " + vote);
        } else {
            logger.info("vote invalid: " + vote);
            voteSaveError = "alreadyvoted";
        }
        modelAndView.addObject("voteSaveError", voteSaveError);
        modelAndView.setViewName("redirect:viewpoll?id=" + pollId);

        return modelAndView;
    }
}
