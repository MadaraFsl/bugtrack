package com.bugtracking.domain.repository;

import com.bugtracking.domain.entity.Project;
import com.bugtracking.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProjectRepository extends JpaRepository<Project,Integer> {

}
