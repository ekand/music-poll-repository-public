package org.perscholas.musicpollwebsite.controller;

import org.perscholas.musicpollwebsite.database.repository.UserRepository;
import org.perscholas.musicpollwebsite.database.entity.User;

import org.perscholas.musicpollwebsite.form.SignUpForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {

    Logger logger = LoggerFactory.getLogger(HomeController.class);

    private final UserRepository userRepository;

    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Login form
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public ModelAndView createUserPage() {
        ModelAndView result = new ModelAndView("signup");
        result.addObject("form", new SignUpForm());
        return result;
    }
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ModelAndView createUserSubmit(@Valid SignUpForm form, BindingResult bindingResult) throws Exception {
        ModelAndView result = new ModelAndView("signup");

        // form validation
        result.addObject("form", form);

        if (bindingResult.hasErrors()) {

            List<String> errors = new ArrayList<String>();

            for (FieldError error : bindingResult.getFieldErrors()) {
                logger.info(error.getField() + " = " + error.getDefaultMessage());
                errors.add(error.getDefaultMessage());
            }

            result.addObject("errorFields", bindingResult.getFieldErrors());
            result.addObject("errors", errors);
            result.addObject("usernameEntry", form.getUsername());
            return result;
        }

        User user = new User();
        user.setUsername(form.getUsername());
        user.setPassword(new BCryptPasswordEncoder().encode(form.getPassword()));

        userRepository.save(user);

        result = new ModelAndView("redirect:login");
        return result;
    }
}



