package com.ouisncf.test.xspeedit.chain;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

/**
 * Handle HttpMessageNotReadableExceptions
 */
@ControllerAdvice
public class HttpMessageNotReadableAdvice {

    /**
     * Handle HttpMessageNotReadableException and return the exception message
     * in a JSON object with a 400 error status
     *
     * @param ex Handled exception
     * @return A 400 error with an error text in a JSON object
     */
    @ResponseBody
    @ExceptionHandler(HttpMessageNotReadableException.class)
    ResponseEntity<Object> httpMessageNotReadableHandler(HttpMessageNotReadableException ex) {
        HashMap<String, String> body = new HashMap<>();
        body.put("error", "Required request body is missing");
        body.put("usage", "{\"articles\": <string>}");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PROBLEM_JSON_UTF8);
        return new ResponseEntity<>(
                body,
                headers,
                HttpStatus.BAD_REQUEST
        );
    }
}
