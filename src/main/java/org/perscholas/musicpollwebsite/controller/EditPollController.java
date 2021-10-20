package org.perscholas.musicpollwebsite.controller;

import org.perscholas.musicpollwebsite.database.entity.Poll;
import org.perscholas.musicpollwebsite.database.entity.Song;
import org.perscholas.musicpollwebsite.database.repository.PollRepository;
import org.perscholas.musicpollwebsite.database.repository.SongRepository;
import org.perscholas.musicpollwebsite.form.CreatePollForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class EditPollController {

    Logger logger = LoggerFactory.getLogger(HomeController.class);

    private final SongRepository songRepository;
    private final PollRepository pollRepository;

    public EditPollController(SongRepository songRepository, PollRepository pollRepository) {
        this.songRepository = songRepository;
        this.pollRepository = pollRepository;
    }

    @GetMapping("/editpoll")
    public ModelAndView editPoll(@RequestParam(name="id") Integer pollId) {
        ModelAndView modelAndView = new ModelAndView();
        Poll poll = pollRepository.findById(pollId).get();
        List<Song> songList = poll.getSongList();
        Song songA = songList.get(0);
        Song songB = songList.get(1);
        Song songC = songList.get(2);

        modelAndView.addObject("poll", poll);
        modelAndView.addObject("pollTitleEntry", poll.getTitle());
        modelAndView.addObject("songOneTitleEntry", songA.getTitle());
        modelAndView.addObject("songTwoTitleEntry", songB.getTitle());
        modelAndView.addObject("songThreeTitleEntry", songC.getTitle());
        modelAndView.setViewName("editpoll");
        return modelAndView;
    }

    @PostMapping("/editpoll")
    public ModelAndView submitEditPoll(@RequestParam(name="id") Integer pollId,
                                       @Valid CreatePollForm form,
                                       BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        Poll poll = pollRepository.findById(pollId).get();

        if (bindingResult.hasErrors()) {

            List<String> errors = new ArrayList<String>();

            for (FieldError error : bindingResult.getFieldErrors()) {
                logger.info(error.getField() + " = " + error.getDefaultMessage());
                errors.add(error.getDefaultMessage());
            }
            modelAndView.addObject("errors", errors);
            modelAndView.addObject("poll", poll);
            modelAndView.addObject("pollTitleEntry", form.getPollTitle());
            modelAndView.addObject("songOneTitleEntry", form.getSongOneTitle());
            modelAndView.addObject("songTwoTitleEntry", form.getSongTwoTitle());
            modelAndView.addObject("songThreeTitleEntry", form.getSongThreeTitle());

            modelAndView.setViewName("editpoll");
        } else {
            poll = pollRepository.findById(pollId).get();
            List<Song> songList = poll.getSongList();
            Song songA = songList.get(0);
            Song songB = songList.get(1);
            Song songC = songList.get(2);
            songA.setTitle(form.getSongOneTitle());
            songB.setTitle(form.getSongTwoTitle());
            songC.setTitle(form.getSongThreeTitle());
            songRepository.save(songA);
            songRepository.save(songB);
            songRepository.save(songC);
            poll.setTitle(form.getPollTitle());
            poll.setSongList(songList);
            pollRepository.save(poll);

            modelAndView.setViewName("redirect:viewpoll?id=" + poll.getId());
        }
        return modelAndView;
    }
}
