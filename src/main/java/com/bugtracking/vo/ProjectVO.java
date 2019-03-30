package com.bugtracking.vo;

public class ProjectVO {

    private Integer projectId;
    private String projectName;
    private String projectDescribe;
    private String projectUrl;
    private String projectVersionUrl;
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
