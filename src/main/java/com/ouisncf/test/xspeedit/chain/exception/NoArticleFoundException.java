package com.ouisncf.test.xspeedit.chain.exception;

/**
 * Exception used to indicate that no articles were found in a specified chain
 */
public class NoArticleFoundException extends RuntimeException {
    public NoArticleFoundException(Long id) {
        super("No article found in chain " + id);
    }
}
