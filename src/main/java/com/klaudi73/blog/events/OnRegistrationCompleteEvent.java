package com.klaudi73.blog.events;

import com.klaudi73.blog.models.UserEntity;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

@SuppressWarnings("serial")
public class OnRegistrationCompleteEvent extends ApplicationEvent {

    private final String appUrl;
    private final Locale locale;
    private final UserEntity user;

    public OnRegistrationCompleteEvent(final UserEntity user,
                                       final Locale locale,
                                       final String appUrl) {
        super(user);
        System.out.println("user: " + user);
        System.out.println("locale: " + locale);
        System.out.println("appUrl: " + appUrl);
        this.user = user;
        this.locale = locale;
        this.appUrl = appUrl;
    }

    //

    public String getAppUrl() {
        return appUrl;
    }

    public Locale getLocale() {
        return locale;
    }

    public UserEntity getUser() {
        return user;
    }
}
