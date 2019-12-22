package com.ppmtool.services;

import com.ppmtool.domain.Project;
import com.ppmtool.exceptions.ProjectIdException;
import com.ppmtool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;
	
	public Project saveOrUpdateProject(Project project) {
		try {
			project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			return projectRepository.save(project);
		} catch (Exception e){
			throw new ProjectIdException("Project Id '" + project.getProjectIdentifier().toUpperCase()+"' already exists.");
		}
	}

	public Project findProjectByIdentifier(String projectId){
		Project project = projectRepository.findByProjectIdentifier(projectId);
		if (project == null) {
			throw new ProjectIdException(("Project does '" + projectId +"'not exist."));
		}
		return projectRepository.findByProjectIdentifier(projectId.toUpperCase());
	}

	public Iterable<Project> findAllProjects(){
		return projectRepository.findAll();
	}

	public void deleteProjectByIdentifier(String projectId){
		Project project = projectRepository.findByProjectIdentifier(projectId);

		if(project == null){
			throw new ProjectIdException(("Cannot delete project with ID'"+projectId +"'. This project does not exist."));
		}

		projectRepository.delete(project);
	}
}