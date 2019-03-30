package com.bugtracking.web;

import com.bugtracking.service.common.ShareService;
import com.bugtracking.service.project.ProjectService;
import com.bugtracking.vo.ProjectVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("project")
public class ProjectController {

    @Autowired
    private ShareService shareService;
    @Autowired
    private ProjectService projectService;


    @RequestMapping("/myProject")
    public String myProject(Map<String,Object> map){

        String username = shareService.getUser().getUsername();
        List<ProjectVO> projectVOList =  projectService.getMyProject(username);
        map.put("projectVOList",projectVOList);

        return "myProject";
    }

    @RequestMapping("/projectInfo")
    public String projectInfo(Map<String,Object> map){


        return "projectInfo";
    }


    @RequestMapping("/projectBug")
    public String projectBug(Map<String,Object> map){


        return "projectBug";
    }


}
