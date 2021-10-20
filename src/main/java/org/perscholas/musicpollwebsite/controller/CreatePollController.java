package org.perscholas.musicpollwebsite.controller;

import lombok.extern.slf4j.Slf4j;
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
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class CreatePollController {

//    Logger logger = LoggerFactory.getLogger(HomeController.class);

    private final SongRepository songRepository;
    private final PollRepository pollRepository;

    public CreatePollController(SongRepository songRepository, PollRepository pollRepository) {
        this.songRepository = songRepository;
        this.pollRepository = pollRepository;
    }

    @GetMapping("/createpoll")
    public ModelAndView createPoll() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("createpoll");
        return modelAndView;
    }

    @PostMapping("/createpoll")
    public ModelAndView submitCreatePoll(@Valid CreatePollForm form, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {

            List<String> errors = new ArrayList<String>();

            for (FieldError error : bindingResult.getFieldErrors()) {
                log.info(error.getField() + " = " + error.getDefaultMessage());
                errors.add(error.getDefaultMessage());
            }
            modelAndView.addObject("errors", errors);
            modelAndView.addObject("pollTitleEntry", form.getPollTitle());
            modelAndView.addObject("songOneTitleEntry", form.getSongOneTitle());
            modelAndView.addObject("songTwoTitleEntry", form.getSongTwoTitle());
            modelAndView.addObject("songThreeTitleEntry", form.getSongThreeTitle());


            modelAndView.setViewName("createpoll");
        } else {
            Song songOne = new Song(form.getSongOneTitle());
            Song songTwo = new Song(form.getSongTwoTitle());
            Song songThree = new Song(form.getSongThreeTitle());
            songRepository.save(songOne);
            songRepository.save(songTwo);
            songRepository.save(songThree);
            List<Song> songList = new ArrayList<>();
            songList.add(songOne);
            songList.add(songTwo);
            songList.add(songThree);
            Poll poll = new Poll(form.getPollTitle(), songList);
            pollRepository.save(poll);

            modelAndView.setViewName("redirect:viewpoll?id=" + poll.getId());
        }
        return modelAndView;


    }

}
