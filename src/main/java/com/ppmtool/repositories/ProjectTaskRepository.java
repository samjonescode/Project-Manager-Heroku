package com.ppmtool.repositories;

import com.ppmtool.domain.ProjectTask;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProjectTaskRepository extends CrudRepository<ProjectTask, Long> {

    List<ProjectTask> findByProjectIdentifierOrderByPriority(String backlog_id);

    ProjectTask findByProjectSequence(String sequence);

}
