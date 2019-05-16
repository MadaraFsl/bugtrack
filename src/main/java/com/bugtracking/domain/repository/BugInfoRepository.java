package com.bugtracking.domain.repository;

import com.bugtracking.domain.entity.Buginfo;
import com.bugtracking.domain.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BugInfoRepository extends JpaRepository<Buginfo, Integer> {

    @Query(nativeQuery = true, value = "select count(bug_id) from buginfo where bug_type = ?1 and bug_status = ?2 and bug_project = ?3")
    Integer getCount(Integer bugType, Integer bugStatus, Integer projectId);

    @Query(nativeQuery = true, value = "select count(bug_id) from buginfo where bug_type = ?1 and bug_project = ?2")
    Integer getCountAll(Integer bugType, Integer projectId);

}
