package com.klaudi73.blog.validators;

@SuppressWarnings("serial")
public class PasswordNotMatchException extends Throwable {

    public PasswordNotMatchException(final String message) {
        super(message);
    }

}