package com.ouisncf.test.xspeedit.chain;

import com.ouisncf.test.xspeedit.chain.exception.ChainNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@ControllerAdvice
public class ChainNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(ChainNotFoundException.class)
    ResponseEntity<Object> chainNotFoundHandler(ChainNotFoundException ex) {
        HashMap<String, String> body = new HashMap<>();
        body.put("error", ex.getLocalizedMessage());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PROBLEM_JSON_UTF8);
        return new ResponseEntity<>(
                body,
                headers,
                HttpStatus.NOT_FOUND
        );
    }
}
