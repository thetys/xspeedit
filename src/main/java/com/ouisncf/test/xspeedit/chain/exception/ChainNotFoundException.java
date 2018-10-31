package com.ouisncf.test.xspeedit.chain.exception;

/**
 * Exception used to indicate that no chain was found with a specified id
 */
public class ChainNotFoundException extends RuntimeException {
    public ChainNotFoundException(Long id) {
        super("Could not find chain " + id);
    }
}
