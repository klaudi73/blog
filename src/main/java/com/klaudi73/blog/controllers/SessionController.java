package com.klaudi73.blog.controllers;

import com.klaudi73.blog.models.UserEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

public class SessionController {
    public static Long getCurrentUserId() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        Long id = null;
        if (authentication != null)
            if (authentication.getPrincipal() instanceof UserEntity)
                id = ((UserEntity) authentication.getPrincipal()).getUserId();
            else if (authentication.getPrincipal() instanceof String)
                id = Long.valueOf((String) authentication.getPrincipal());
        try {
            if (Objects.isNull(id)) {
                return 0L; //anonymoususer
            } else {
                return id;
            }
        } catch (NumberFormatException e) {
            return 0L; //anonymoususer
        }
    }
}
