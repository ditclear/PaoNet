package com.ditclear.paonet.model.remote.exception;

import java.io.IOException;

/**
 * Created by dzq on 2016/10/18.
 */

public class ApiException extends IOException {
    public ApiException(String message) {
        super(message);
    }
}
