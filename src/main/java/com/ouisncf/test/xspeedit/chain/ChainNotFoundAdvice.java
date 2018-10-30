package com.ouisncf.test.xspeedit.chain;

import org.springframework.http.HttpStatus;
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
        return new ResponseEntity<>(
                body,
                HttpStatus.NOT_FOUND
        );
    }
}
