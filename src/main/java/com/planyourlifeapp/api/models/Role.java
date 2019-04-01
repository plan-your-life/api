package com.planyourlifeapp.api.models;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;


public class Role {
    private String[] roles = {"ROLE_USER","ROLE_SUPPORTER", "ROLE_ADMIN", "ROLE_OWNER"};
    private int adminLevel;

    public Role(int adminLevel) {
        this.adminLevel = adminLevel;
    }

    public List<SimpleGrantedAuthority> getRole() {
        List<SimpleGrantedAuthority> roles = new ArrayList();
        roles.add(new SimpleGrantedAuthority(this.roles[adminLevel]));
        return roles;
    }
}

