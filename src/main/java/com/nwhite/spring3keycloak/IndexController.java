package com.nwhite.spring3keycloak;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class IndexController {

    @GetMapping(path = "/")
    public HashMap index() {
        OAuth2User user =
                ((OAuth2User) SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal());


        return new LinkedHashMap(){{
            put("Здравствуй", user.getAttribute("name"));
            put("Роли: ", getRoles(user.getAttribute("roles")));
        }};
    }

    @GetMapping(path = "/unauthenticated")
    public HashMap unauthenticatedRequests() {
        return new HashMap(){{
            put("this is ", "unauthenticated endpoint");
        }};
    }

    private List<String> getRoles(List<String> roles) {
        return roles.stream()
                .filter(s -> s.matches("[а-яА-ЯёЁ]+"))
                .collect(Collectors.toList());
    }
}
