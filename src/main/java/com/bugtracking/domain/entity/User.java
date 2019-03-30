package com.bugtracking.domain.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user")
public class User implements Serializable {

    @Id
    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", unique = true, nullable = false)
    private String password;

    @Column(name = "cname",nullable = true)
    private String cname;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_authourity",
            joinColumns = {
                    @JoinColumn(name="user_id", nullable = false)
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "authourity_id",nullable = false)
            }
    )
    private Set<Authority> authoritySet;

    public Set<Authority> getAuthoritySet() {
        return authoritySet;
    }

    public void setAuthoritySet(Set<Authority> authoritySet) {
        this.authoritySet = authoritySet;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
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
}
