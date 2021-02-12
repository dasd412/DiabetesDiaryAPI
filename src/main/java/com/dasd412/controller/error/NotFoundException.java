package com.dasd412.controller.error;

public class NotFoundException extends ServiceRuntimeException{

    static final String MESSAGE_KEY="error.notfound";

    public NotFoundException(String messageKey, String detailKey, Object[] params) {
        super(messageKey, detailKey, params);
    }

}
