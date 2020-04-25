package com.zykspring.funnycore.exception;

public class UpdateFailedException extends BaseException{

    private static final long serialVersionUID = 6058294324031642376L;

    public UpdateFailedException() {}

    public UpdateFailedException(String message) {
        super(message);
    }

    public UpdateFailedException(String code, String message) {
        super(message);
        this.code = code;
    }

}
