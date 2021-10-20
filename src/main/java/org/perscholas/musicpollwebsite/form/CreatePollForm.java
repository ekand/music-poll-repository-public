package org.perscholas.musicpollwebsite.form;

import lombok.Data;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class CreatePollForm {
    @NotEmpty(message="Poll Title must not be empty")
    @Size(min = 1, max = 250, message = "Poll Title length must be between 1 and 250 characters")
    String pollTitle;

    @NotEmpty(message="Song One Title must not be empty")
    @Size(min = 1, max = 250, message = "Song One Title length must be between 1 and 250 characters")
    String songOneTitle;

    @NotEmpty(message="Song Two Title must not be empty")
    @Size(min = 1, max = 250, message = "Song Two Title length must be between 1 and 250 characters")
    String songTwoTitle;

    @NotEmpty(message="Song Three Title must not be empty")
    @Size(min = 1, max = 250, message = "Song Three Title length must be between 1 and 250 characters")
    String songThreeTitle;

}
