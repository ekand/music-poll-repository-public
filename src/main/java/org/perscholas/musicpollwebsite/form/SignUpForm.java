package org.perscholas.musicpollwebsite.form;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.perscholas.musicpollwebsite.form.validation.UsernameUnique;
import org.perscholas.musicpollwebsite.form.validation.TwoFieldsAreEqual;

@Data
@TwoFieldsAreEqual(fieldOneName = "confirmPassword", fieldTwoName = "password", message = "Password and Conform Password must be the same.")
public class SignUpForm {

    @NotEmpty
    @UsernameUnique(message = "Username already exists in database")
    private String username;

    @NotEmpty(message = "Password can not be empty.")
    @Size(min = 10, max = 25, message = "Password must be between 10 and 25 characters")
    private String password;

    @NotEmpty(message = "Confirm Password is required.")
    private String confirmPassword;
}
