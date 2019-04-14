package com.bugtracking.web;

import com.bugtracking.domain.entity.Project;
import com.bugtracking.domain.repository.ProjectRepository;
import com.bugtracking.service.common.ShareService;
import com.bugtracking.service.project.ProjectService;
import com.bugtracking.vo.ProjectVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("project")
public class ProjectController {

    @Autowired
    private ShareService shareService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private ProjectRepository projectRepository;

    @RequestMapping("/myProject")
    public String myProject(Map<String, Object> map,HttpServletRequest request) {
        String username = shareService.getUser().getUsername();
        List<ProjectVO> projectVOList = projectService.getMyProject(username);
        map.put("projectVOList", projectVOList);
        return "myProject";
    }

    @RequestMapping("/projectInfo")
    public String projectInfo(Map<String, Object> map,String projectId) {
        map.put("projectId",projectId);
        return "projectInfo";
    }


    @RequestMapping("/projectBug")
    public String projectBug(Map<String, Object> map) {


        return "projectBug";
    }

    @RequestMapping("/addProject")
    public String addProject(HttpServletRequest request, Map<String, Object> map, Project project) {
        String action = request.getParameter("action");
        if (action != null) {
            switch (action) {
                case "show":
                    break;
                case "add":
                    try {
                        project.setProjectCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                        project = projectRepository.saveAndFlush(project);
                    } catch (Exception e) {
                        e.printStackTrace();
                        map.put("tips", "failed");
                        map.put("url", "addProject?action=show");
                        return "tips";
                    }
                    map.put("projectId", project.getProjectId());
                    map.put("userList", projectService.getMemberNotInProject(project.getProjectId()));
                    return "addMember";
            }
        }
        return "addProject";
    }

    @RequestMapping("/addMember")
    public String addMember(HttpServletRequest request, Map<String, Object> map,String action,String projectId) {
        if (action != null) {
            switch (action) {
                case "show":
                    map.put("projectId", projectId);
                    map.put("userList", projectService.getMemberNotInProject(Integer.parseInt(projectId)));
                    return "addMember";
                case "add":
                    try {
                        String[] users = request.getParameterValues("userId");
                        if (users != null && users.length > 0) {
                            projectService.saveMemberForProject(projectId, users);
                        }
                        map.put("tips", "ok");
                        map.put("url", "myProject");
                    } catch (Exception e) {
                        e.printStackTrace();
                        map.put("tips", "failed");
                        map.put("url", "addProject?action=show");
                    }
                    return "tips";
            }
        }
        return "";
    }

    @RequestMapping("/addBug")
    public String addBug(HttpServletRequest request, Map<String, Object> map,String action,String projectId) {
        if (action != null) {
            switch (action) {
                case "show":
                    map.put("projectId", projectId);
                    return "addBug";
                case "add":
                    return "tips";
            }
        }
        return "";
    }
}
