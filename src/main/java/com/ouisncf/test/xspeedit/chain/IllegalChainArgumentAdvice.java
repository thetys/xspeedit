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

/**
 * Handle IllegalChainArgumentExceptions
 */
@ControllerAdvice
public class IllegalChainArgumentAdvice {

    /**
     * Handle IllegalChainArgumentException and return the exception message
     * in a JSON object with a 400 error status
     *
     * @param ex Handled exception
     * @return A 400 error with an error text in a JSON object
     */
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
