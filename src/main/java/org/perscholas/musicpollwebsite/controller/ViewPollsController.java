package org.perscholas.musicpollwebsite.controller;

import org.apache.commons.lang3.StringUtils;
import org.perscholas.musicpollwebsite.database.entity.Poll;
import org.perscholas.musicpollwebsite.database.entity.Song;
import org.perscholas.musicpollwebsite.database.repository.PollRepository;
import org.perscholas.musicpollwebsite.database.repository.SongRepository;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
public class ViewPollsController {

    private final PollRepository pollRepository;
    private final SongRepository songRepository;

    public ViewPollsController(PollRepository pollRepository, SongRepository songRepository) {
        this.pollRepository = pollRepository;
        this.songRepository = songRepository;
    }

    @GetMapping("/viewpolls")
    public ModelAndView viewAllPolls(@RequestParam(required = false) String pollTitle,
                                     @RequestParam(required = false) String songTitle) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("viewpolls");
        List<Poll> pollList = new ArrayList<>();
        if (StringUtils.isEmpty(pollTitle) && StringUtils.isEmpty(songTitle)) {
            pollList = pollRepository.getAllByIdIsNotNull();
        } else if (!StringUtils.isEmpty(pollTitle) && StringUtils.isEmpty(songTitle)) {
            pollList = getPollsByPollTitle(pollTitle);
        } else if (StringUtils.isEmpty(pollTitle) && !StringUtils.isEmpty(songTitle)) {
            pollList = getPollsBySongTitle(songTitle);
        } else { // both pollTitle and songTitle are not empty
            pollList = getPollsByPollTitleAndSongTitle(pollTitle, songTitle);
        }

        modelAndView.addObject("pollList",pollList);
        modelAndView.addObject("pollTitle", pollTitle);
        modelAndView.addObject("songTitle", songTitle);
        return modelAndView;
    }

    @GetMapping("/deletepoll")
    public ModelAndView deletepoll(@RequestParam(name="pollid") Integer pollId) {
        pollRepository.deleteById(pollId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:viewpolls");
        return modelAndView;
    }

    private List<Poll> getPollsByPollTitleAndSongTitle(String pollTitle, String songTitle) {
        List<Poll> pollListFilteredByPollTitle = new ArrayList<>();
        List<Poll> pollListFromPollTitle = getPollsByPollTitle(pollTitle);
        for (Poll p : pollListFromPollTitle) {
            boolean containsSongTitle = false;
            for (Song s : p.getSongList()) {
                if (titlesMatch(s.getTitle(), songTitle)) {
                    containsSongTitle = true;
                    break;
                }
            }
            if (containsSongTitle) {
                pollListFilteredByPollTitle.add(p);
            }
        }
        return pollListFilteredByPollTitle;
    }

    private boolean titlesMatch(String songTitleFromDatabase, String songTitleSearchQuery) {
        return songTitleFromDatabase.toLowerCase().matches("(.*)" + songTitleSearchQuery.toLowerCase() + "(.*)");
    }

    private List<Poll> getPollsBySongTitle(String songTitle) {
        List<Poll> pollList = new ArrayList<>();
        Set<Integer> pollIdSet = new HashSet<>();
        List<Song> songList = songRepository.findAllByTitleContainingIgnoreCase(songTitle);
        for (Song s : songList) {
            for (Poll p : s.getPollList()) {
                pollIdSet.add(p.getId());
            }
        }
        for (Integer id : pollIdSet) {
            pollList.add(pollRepository.findById(id).get());  // todo add isPresent() check
        }
        return pollList;
    }

    private List<Poll> getPollsByPollTitle(String pollTitle) {
        return pollRepository.findAllByTitleContainingIgnoreCase(pollTitle);
    }
}
