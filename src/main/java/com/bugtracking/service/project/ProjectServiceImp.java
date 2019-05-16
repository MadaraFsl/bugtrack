package com.bugtracking.service.project;

import com.bugtracking.domain.entity.User;
import com.bugtracking.domain.repository.ProjectRepository;
import com.bugtracking.tools.Util;
import com.bugtracking.vo.BuginfoVo;
import com.bugtracking.vo.ProjectVO;
import com.bugtracking.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
        return Util.transferObjectsToList(list, ProjectVO.class);

    }

    @Override
    public void saveMemberForProject(String projectId, String[] users) throws SQLException {

        Statement stat = null;
        Connection connection = dataSource.getConnection();
        stat = connection.createStatement();
        for (int i = 0; i < users.length; i++) {
            stat.addBatch("INSERT INTO user_project(project_id,user_id) VALUES(" + Integer.parseInt(projectId) + ", '" + users[i] + "')");
        }
        stat.executeBatch();
        stat.close();
    }

    @Override
    public List getMemberNotInProject(Integer projectId) {
        String sql = " SELECT " +
                " u.username, " +
                " u.password, " +
                " u.cname  " +
                " FROM " +
                " user AS u  " +
                " WHERE " +
                " u.username NOT IN ( SELECT user_id FROM user_project AS up WHERE project_id = " + projectId + " )";
        Query query = entityManager.createNativeQuery(sql);
        return Util.transferObjectsToList(query.getResultList(), UserVO.class);
    }

    @Override
    public List getMemberInProject(Integer projectId) {
        String sql = " SELECT " +
                " u.username, " +
                " u.password, " +
                " u.cname  " +
                " FROM " +
                " `user` AS u " +
                " INNER JOIN user_project AS up ON u.username = up.user_id  " +
                " WHERE " +
                " up.project_id = " + projectId;
        Query query = entityManager.createNativeQuery(sql);
        return Util.transferObjectsToList(query.getResultList(), UserVO.class);
    }

    @Override
    public List getMyBugInfo(String username, Integer currPage, Integer pageSize) {
        String sql = " SELECT " +
                " bi.bug_id, " +
                " bt.type_name, " +
                " ba.bugstatus_cname, " +
                " bp.cname, " +
                " bi.bug_name, " +
                " ubi.cname AS bugCname, " +
                " bi.bug_updatetime, " +
                " uup.cname AS updater, " +
                " bi.bug_begintime  " +
                " FROM " +
                " buginfo AS bi " +
                " LEFT JOIN bugpriority AS bp ON bp.id = bi.bug_priority " +
                " LEFT JOIN bugstatus AS ba ON ba.bugstatus_id = bi.bug_status " +
                " LEFT JOIN bugtype AS bt ON bt.bugtype_id = bi.bug_type " +
                " LEFT JOIN `user` AS ubi ON ubi.username = bi.bug_user " +
                " LEFT JOIN `user` AS uup ON uup.username = bi.bug_updater  " +
                " WHERE " +
                " bi.bug_user = '" + username + "'" +
                " LIMIT " + currPage + ", " + pageSize;
        Query query = entityManager.createNativeQuery(sql);
        return Util.transferObjectsToList(query.getResultList(), BuginfoVo.class);
    }

    @Override
    public List getMemberInProjectByAuthority(Integer projectId, Integer authorityId) {
        String sql = " SELECT " +
                " u.username, " +
                " u.cname, " +
                " a.authority_cname  " +
                " FROM " +
                " `user` AS u " +
                " INNER JOIN user_project AS up ON u.username = up.user_id " +
                " LEFT JOIN user_authourity AS ua ON ua.user_id = u.username " +
                " LEFT JOIN authority AS a ON a.id = ua.authourity_id  " +
                " WHERE " +
                " up.project_id = " + projectId +
                " AND a.id = " + authorityId;
        return entityManager.createNativeQuery(sql).getResultList();
    }

    @Override
    public List getProjectBugInfo(Integer projectId) {
        String sql = " SELECT " +
                " bi.bug_id, " +
                " bt.type_name, " +
                " ba.bugstatus_cname, " +
                " bp.cname, " +
                " bi.bug_name, " +
                " ubi.cname AS bugCname, " +
                " bi.bug_updatetime, " +
                " uup.cname AS updater, " +
                " bi.bug_begintime  " +
                " FROM " +
                " buginfo AS bi " +
                " LEFT JOIN bugpriority AS bp ON bp.id = bi.bug_priority " +
                " LEFT JOIN bugstatus AS ba ON ba.bugstatus_id = bi.bug_status " +
                " LEFT JOIN bugtype AS bt ON bt.bugtype_id = bi.bug_type " +
                " LEFT JOIN `user` AS ubi ON ubi.username = bi.bug_user " +
                " LEFT JOIN `user` AS uup ON uup.username = bi.bug_updater  " +
                " WHERE " +
                " bi.bug_project = '" + projectId + "'";
        Query query = entityManager.createNativeQuery(sql);
        return Util.transferObjectsToList(query.getResultList(), BuginfoVo.class);
    }
}
