package com.bugtracking.domain.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "project")
public class Project {

    @Id
    @Column(name = "project_id", unique = true, nullable = false)
    private Integer projectId;

    @Column(name = "project_name", unique = true, nullable = false)
    private String projectName;

    @Column(name = "project_describe")
    private String projectDescribe;

    @Column(name = "project_url")
    private String projectUrl;

    @Column(name = "project_versionUrl")
    private String projectVersionUrl;

    @Column(name = "project_databaseUrl")
    private String projectDatabaseUrl;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectDescribe() {
        return projectDescribe;
    }

    public void setProjectDescribe(String projectDescribe) {
        this.projectDescribe = projectDescribe;
    }

    public String getProjectUrl() {
        return projectUrl;
    }

    public void setProjectUrl(String projectUrl) {
        this.projectUrl = projectUrl;
    }

    public String getProjectVersionUrl() {
        return projectVersionUrl;
    }

    public void setProjectVersionUrl(String projectVersionUrl) {
        this.projectVersionUrl = projectVersionUrl;
    }

    public String getProjectDatabaseUrl() {
        return projectDatabaseUrl;
    }

    public void setProjectDatabaseUrl(String projectDatabaseUrl) {
        this.projectDatabaseUrl = projectDatabaseUrl;
    }

}
