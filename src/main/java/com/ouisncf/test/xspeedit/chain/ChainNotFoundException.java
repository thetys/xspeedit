package com.ouisncf.test.xspeedit.chain;

public class ChainNotFoundException extends RuntimeException {
    public ChainNotFoundException(Long id) {
        super("Could not find chain " + id);
    }
}
