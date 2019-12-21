package com.ppmtool.repositories;

import com.ppmtool.domain.Project;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
	
//	@Override
//	Iterable<Project> findAllById(Iterable<Long> iterable);

	Project findByProjectIdentifier(String projectId);

	@Override
	<S extends Project> List<S> findAll(Example<S> example);

	//	Iterable<Project> findAll();
}
