package org.perscholas.musicpollwebsite.exceptions;

public class PollNotFoundException extends Throwable {
    public PollNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
