package org.perscholas.musicpollwebsite.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class LoginForm {
    @Email
    @NotEmpty
    private String email;

    @NotEmpty
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
