package com.questionsandanswers.questionsandanswers.controllers;

import com.questionsandanswers.questionsandanswers.auth.security.services.UserDetailsImpl;
import com.questionsandanswers.questionsandanswers.auth.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

public class MainClassController {

    @Autowired
    private UserDetailsServiceImpl authUser;

    /**
     * Retorna los datos del usuario autentificado
     * @return userDetailsImp
     */
    protected UserDetailsImpl getUserDetailsImp(){
        return authUser.loadUserByUsernameInfo(
                SecurityContextHolder.getContext().getAuthentication().getName()
        );
    }

}
