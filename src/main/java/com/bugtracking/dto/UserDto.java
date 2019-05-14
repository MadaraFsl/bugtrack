package com.bugtracking.dto;

import java.util.List;

public class UserDto {

    private String username;
    private List authoritys;
    private String password;
    private String cname;

    public List getAuthoritys() {
        return authoritys;
    }

    public void setAuthoritys(List authoritys) {
        this.authoritys = authoritys;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }
}
