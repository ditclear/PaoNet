package com.ditclear.paonet.model.remote.exception;

/**
 * Created by dzq on 2016/10/18.
 */

public class EmptyException extends Exception {
    public EmptyException(int emptyType) {
        super("empty");
    }
}
