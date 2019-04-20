package com.bugtracking.service.project;

import com.bugtracking.vo.ProjectVO;

import java.sql.SQLException;
import java.util.List;

public interface ProjectService {

    List<ProjectVO> getMyProject(String username);

    void saveMemberForProject(String projectId, String[] users) throws SQLException;

    List getMemberNotInProject(Integer projectId);

    List getMemberInProject(Integer projectId);

    List getMyBugInfo(String username, Integer currPage, Integer pageSize);

    List getMemberInProjectByAuthority(Integer projectId, Integer authorityId);

}
