package com.bugtracking.service.project;

import com.bugtracking.tools.Util;
import com.bugtracking.vo.ProjectVO;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service("ProjectService")
public class ProjectServiceImp implements ProjectService {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<ProjectVO> getMyProject(String username) {

        String sql = " SELECT " +
                " p.project_id," +
                " p.project_name," +
                " p.project_describe," +
                " p.project_url," +
                " p.project_versionUrl," +
                " p.project_databaseUrl " +
                " FROM " +
                " project AS p" +
                " INNER JOIN user_project AS up ON up.project_id = p.project_id " +
                " WHERE " +
                " user_id = '"+username+"' ";


        List<Object[]> list= entityManager.createNativeQuery(sql).getResultList();
        return Util.transferObjectsToList(list,new ProjectVO());

    }
}
