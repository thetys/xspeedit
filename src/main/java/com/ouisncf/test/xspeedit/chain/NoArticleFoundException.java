package com.ouisncf.test.xspeedit.chain;

public class NoArticleFoundException extends RuntimeException {
    public NoArticleFoundException(Long id) {
        super("No article found in chain " + id);
    }
}
