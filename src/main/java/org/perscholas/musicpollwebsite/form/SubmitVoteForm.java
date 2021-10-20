package org.perscholas.musicpollwebsite.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubmitVoteForm {
    Integer pollId;
    Integer songId;
    Integer userId;
}

