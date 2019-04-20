package com.bugtracking.domain.repository;

import com.bugtracking.domain.entity.Buginfo;
import com.bugtracking.domain.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BugInfoRepository extends JpaRepository<Buginfo,String> {

}
