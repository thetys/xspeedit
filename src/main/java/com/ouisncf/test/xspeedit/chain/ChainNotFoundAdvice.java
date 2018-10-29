package com.ouisncf.test.xspeedit.chain;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@ControllerAdvice
public class ChainNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(ChainNotFoundException.class)
    ResponseEntity<Object> chainNotFoundHandler(ChainNotFoundException ex) {
        return new ResponseEntity<>(
                Map.of("error", ex.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }
}
