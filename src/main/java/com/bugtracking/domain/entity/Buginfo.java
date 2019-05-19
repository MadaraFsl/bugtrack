package com.bugtracking.domain.entity;

import org.omg.PortableInterceptor.AdapterNameHelper;
import org.springframework.security.authentication.AnonymousAuthenticationToken;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "buginfo")
public class Buginfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bug_id")
    private Integer bugId;

    @Column(name = "bug_name")
    private String bugName;

    @Column(name = "bug_type")
    private Integer bugType;

    @Column(name = "bug_describe")
    private String bugDescribe;

    @Column(name = "bug_priority")
    private String bugPriority;

    @Column(name = "bug_begintime")
    private Date bugBegintime;

    @Column(name = "bug_completetime")
    private String bugCompletetime;

    @Column(name = "bug_project")
    private Integer bugProject;

    @Column(name = "bug_user")
    private String bugUser;

    @Column(name = "bug_status")
    private Integer bugStatus;

    @Column(name = "bug_creater")
    private String bugCreater;

    @Column(name = "bug_updater")
    private String bugUpdater;

    @Column(name = "bug_updatetime")
    private Date bugUpdatetime;


    public Date getBugUpdatetime() {
        return bugUpdatetime;
    }

    public void setBugUpdatetime(Date bugUpdatetime) {
        this.bugUpdatetime = bugUpdatetime;
    }

    public String getBugUpdater() {
        return bugUpdater;
    }

    public void setBugUpdater(String bugUpdater) {
        this.bugUpdater = bugUpdater;
    }

    public Integer getBugId() {
        return bugId;
    }

    public String getBugCompletetime() {
        return bugCompletetime;
    }

    public void setBugCompletetime(String bugCompletetime) {
        this.bugCompletetime = bugCompletetime;
    }

    public void setBugId(Integer bugId) {
        this.bugId = bugId;
    }

    public String getBugName() {
        return bugName;
    }

    public void setBugName(String bugName) {
        this.bugName = bugName;
    }

    public Integer getBugType() {
        return bugType;
    }

    public void setBugType(Integer bugType) {
        this.bugType = bugType;
    }

    public String getBugDescribe() {
        return bugDescribe;
    }

    public void setBugDescribe(String bugDescribe) {
        this.bugDescribe = bugDescribe;
    }

    public String getBugPriority() {
        return bugPriority;
    }

    public void setBugPriority(String bugPriority) {
        this.bugPriority = bugPriority;
    }

    public Date getBugBegintime() {
        return bugBegintime;
    }

    public void setBugBegintime(Date bugBegintime) {
        this.bugBegintime = bugBegintime;
    }

    public Integer getBugProject() {
        return bugProject;
    }

    public void setBugProject(Integer bugProject) {
        this.bugProject = bugProject;
    }

    public String getBugUser() {
        return bugUser;
    }

    public void setBugUser(String bugUser) {
        this.bugUser = bugUser;
    }

    public Integer getBugStatus() {
        return bugStatus;
    }

    public void setBugStatus(Integer bugStatus) {
        this.bugStatus = bugStatus;
    }

    public String getBugCreater() {
        return bugCreater;
    }

    public void setBugCreater(String bugCreater) {
        this.bugCreater = bugCreater;
    }
}
