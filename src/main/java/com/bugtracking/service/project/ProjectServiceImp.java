package com.bugtracking.service.project;

import com.bugtracking.domain.repository.ProjectRepository;
import com.bugtracking.tools.Util;
import com.bugtracking.vo.ProjectVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Service("ProjectService")
public class ProjectServiceImp implements ProjectService {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private DataSource dataSource;

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
                " user_id = '" + username + "' ";


        List<Object[]> list = entityManager.createNativeQuery(sql).getResultList();
        return Util.transferObjectsToList(list, new ProjectVO());

    }

    @Override
    public void saveMemberForProject(String projectId, String[] users) {

        Statement stat = null;
        try {
            Connection connection = dataSource.getConnection();
            stat = connection.createStatement();
            for (int i = 0; i < users.length; i++) {
                stat.addBatch("INSERT INTO user_project(project_id,user_id) VALUES(" + Integer.parseInt(projectId)+ ", '" + users[i] + "')");
            }
            stat.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stat.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
