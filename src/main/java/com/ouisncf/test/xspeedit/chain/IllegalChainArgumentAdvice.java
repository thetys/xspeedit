package com.ouisncf.test.xspeedit.chain;

import com.ouisncf.test.xspeedit.chain.exception.IllegalChainArgumentException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@ControllerAdvice
public class IllegalChainArgumentAdvice {

    @ResponseBody
    @ExceptionHandler(IllegalChainArgumentException.class)
    ResponseEntity<Object> illegalChainArgumentHandler(IllegalChainArgumentException ex) {
        HashMap<String, String> body = new HashMap<>();
        body.put("error", ex.getLocalizedMessage());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PROBLEM_JSON_UTF8);
        return new ResponseEntity<>(
                body,
                headers,
                HttpStatus.BAD_REQUEST
        );
    }
}
