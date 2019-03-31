package com.bugtracking.service.project;


import com.bugtracking.domain.entity.Project;
import com.bugtracking.vo.ProjectVO;

import java.util.List;

public interface ProjectService {

    List<ProjectVO> getMyProject(String username);
    void saveMemberForProject(String projectId,String[] users);

}
