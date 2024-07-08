package com.exam.utils;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;


public class AuthUtils {

    public static UserDetails getCurrentPrincipal() {
        UserDetails userDetails=null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails ) {
             userDetails= (UserDetails) authentication.getPrincipal();
           // System.out.println(ud.getUsername());
            return userDetails;
        }
        return userDetails;
    }


}