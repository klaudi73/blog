package com.klaudi73.blog.validators;

@SuppressWarnings("serial")
public class LoginExistsException extends Throwable {

    public LoginExistsException(final String message) {
        super(message);
    }

}