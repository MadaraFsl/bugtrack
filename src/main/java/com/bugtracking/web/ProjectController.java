package com.bugtracking.web;

import com.bugtracking.domain.entity.Buginfo;
import com.bugtracking.domain.entity.Project;
import com.bugtracking.domain.repository.BugInfoRepository;
import com.bugtracking.domain.repository.ProjectRepository;
import com.bugtracking.service.common.ShareService;
import com.bugtracking.service.project.ProjectService;
import com.bugtracking.vo.ProjectVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    @Autowired
    private BugInfoRepository bugInfoRepository;

    @RequestMapping("/myProject")
    public String myProject(Map<String, Object> map, HttpServletRequest request) {
        String username = shareService.getUser().getUsername();
        List<ProjectVO> projectVOList = projectService.getMyProject(username);
        map.put("projectVOList", projectVOList);
        return "myProject";
    }

    @RequestMapping("/projectInfo")
    public String projectInfo(Map<String, Object> map, String projectId) {
        map.put("projectId", projectId);
        // 获取管理员集合
        List<Object[]> managerList = projectService.getMemberInProjectByAuthority(Integer.parseInt(projectId), 4);
        if (managerList.size() > 0) {
            StringBuilder managerStr = new StringBuilder();
            for (int i = 0; i < managerList.size(); i++) {
                managerStr.append(managerList.get(i)[1] + ",");
            }
            managerStr.deleteCharAt(managerStr.lastIndexOf(","));
            map.put("managerStr", managerStr);
        }
        // 获取开发者集合
        List<Object[]> developerList = projectService.getMemberInProjectByAuthority(Integer.parseInt(projectId), 3);
        if (developerList.size() > 0) {
            StringBuilder developerStr = new StringBuilder();
            for (int i = 0; i < developerList.size(); i++) {
                developerStr.append(developerList.get(i)[1] + ",");
            }
            developerStr.deleteCharAt(developerStr.lastIndexOf(","));
            map.put("developerStr", developerStr);
        }
        // 获取测试人员集合
        List<Object[]> testList = projectService.getMemberInProjectByAuthority(Integer.parseInt(projectId), 2);
        if (testList.size() > 0) {
            StringBuilder testStr = new StringBuilder();
            for (int i = 0; i < testList.size(); i++) {
                testStr.append(testList.get(i)[1] + ",");
            }
            testStr.deleteCharAt(testStr.lastIndexOf(","));
            map.put("testStr", testStr);
        }

        return "projectInfo";
    }


    @RequestMapping("/projectBug")
    public String projectBug(Map<String, Object> map) {

        Integer currPage = 1;
        Integer pageSize = 20;

        map.put("bugInfos", projectService.getMyBugInfo(shareService.getUser().getUsername(), currPage - 1, pageSize));

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
                        String[] users = new String[1];
                        users[0] = shareService.getUser().getUsername();
                        projectService.saveMemberForProject(String.valueOf(project.getProjectId()), users);
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
    public String addMember(HttpServletRequest request, Map<String, Object> map, String action, String projectId) {
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
    public String addBug(HttpServletRequest request, Map<String, Object> map, String action, String projectId, @ModelAttribute Buginfo buginfo) {
        if (action != null) {
            switch (action) {
                case "show":
                    map.put("bugTypes", shareService.getAllBugType());
                    map.put("bugStatus", shareService.getAllBugStatus());
                    map.put("bugPrioritys", shareService.getAllBugPriority());
                    map.put("members", projectService.getMemberInProject(Integer.parseInt(projectId)));
                    map.put("buginfo", new Buginfo());
                    map.put("projectId", projectId);
                    return "addBug";
                case "add":
                    buginfo.setBugCreater(shareService.getUser().getUsername());
                    buginfo.setBugBegintime(new Date());
                    buginfo.setBugUpdater(shareService.getUser().getUsername());
                    buginfo.setBugUpdatetime(new Date());
                    bugInfoRepository.saveAndFlush(buginfo);
                    map.put("tips", "ok");
                    map.put("url", "myProject");
                    return "tips";
            }
        }
        return "";
    }

    @RequestMapping("/editBugInfo")
    public String editBugInfo(HttpServletRequest request, Map<String, Object> map, String action, String bugId, @ModelAttribute Buginfo buginfo) {
        if (action != null) {
            switch (action) {
                case "show":
                    buginfo = bugInfoRepository.getOne(Integer.valueOf(bugId));
                    map.put("buginfo", buginfo);
                    map.put("bugTypes", shareService.getAllBugType());
                    map.put("bugStatus", shareService.getAllBugStatus());
                    map.put("bugPrioritys", shareService.getAllBugPriority());
                    map.put("members", projectService.getMemberInProject(buginfo.getBugProject()));
                    return "editBugInfo";
                case "edit":
                    Buginfo oldBugInfo = bugInfoRepository.getOne(Integer.valueOf(bugId));
                    buginfo.setBugCreater(oldBugInfo.getBugCreater());
                    buginfo.setBugBegintime(oldBugInfo.getBugBegintime());
                    buginfo.setBugCompletetime(oldBugInfo.getBugCompletetime());
                    buginfo.setBugProject(oldBugInfo.getBugProject());
                    buginfo.setBugUpdater(shareService.getUser().getUsername());
                    buginfo.setBugUpdatetime(new Date());
                    bugInfoRepository.saveAndFlush(buginfo);
                    map.put("tips", "ok");
                    map.put("url", "projectBug");
                    return "tips";
            }
        }
        return "";
    }
}
