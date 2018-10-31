package com.ouisncf.test.xspeedit.chain.exception;

/**
 * Exception used to indicate that an argument is wrong or missing
 * in a chain creation request
 */
public class IllegalChainArgumentException extends IllegalArgumentException {
    public IllegalChainArgumentException(String s) {
        super(s);
    }
}
