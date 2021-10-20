package org.perscholas.musicpollwebsite.controller;

import org.perscholas.musicpollwebsite.database.entity.User;
import org.perscholas.musicpollwebsite.database.entity.UserRole;
import org.perscholas.musicpollwebsite.database.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.List;

@Controller
public class debugController {

    Logger logger = LoggerFactory.getLogger(ViewPollsController.class);

    private final UserRepository userRepository;

    public debugController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/debug")
    public ModelAndView debug() {
        User user1 = userRepository.findById(1).get();
        List<UserRole> userRoleList = user1.getUserRoles();
        logger.info("hello 925837");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> grantedAuthorities = authentication.getAuthorities();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("debug");
        return modelAndView;
    }

}
