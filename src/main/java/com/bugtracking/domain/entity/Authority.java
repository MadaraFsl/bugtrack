package com.bugtracking.domain.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "authority")
public class Authority implements Serializable {
    @Id
    @Column(unique = true,nullable = false)
    private int id;

    @Column(name = "authority_ename",nullable = false)
    private String ename;

    @Column(name = "authority_cname",nullable = false)
    private String cname;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_authourity",
            joinColumns = {
                    @JoinColumn(name="authourity_id", nullable = false)
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "user_id",nullable = false)
            }
    )
    private List<User> userList;

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }
}
