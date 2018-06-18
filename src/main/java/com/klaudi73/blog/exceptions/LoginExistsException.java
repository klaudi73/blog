package com.klaudi73.blog.exceptions;

@SuppressWarnings("serial")
public class LoginExistsException extends Throwable {

    public LoginExistsException(final String message) {
        super(message);
    }
}