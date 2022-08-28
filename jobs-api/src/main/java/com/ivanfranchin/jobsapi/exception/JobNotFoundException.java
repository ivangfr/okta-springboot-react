package com.ivanfranchin.jobsapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class JobNotFoundException extends RuntimeException {

    public JobNotFoundException(String id) {
        super(String.format("Job with id %s not found", id));
    }
}
