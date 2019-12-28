package com.ppmtool.services;

import com.ppmtool.domain.Backlog;
import com.ppmtool.domain.Project;
import com.ppmtool.domain.User;
import com.ppmtool.exceptions.ProjectIdException;
import com.ppmtool.exceptions.ProjectNotFoundException;
import com.ppmtool.repositories.BacklogRepository;
import com.ppmtool.repositories.ProjectRepository;
import com.ppmtool.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private BacklogRepository backlogRepository;

	@Autowired
	private UserRepository userRepository;

	public Project saveOrUpdateProject(Project project, String username) {

		if(project.getId()!=null){
			Project existentProject = projectRepository.findByProjectIdentifier(project.getProjectIdentifier());

			if(existentProject !=null && (!existentProject.getUser().getUsername().equals(username))){
				// if a proj exists, but the username attempting to update does not match the user on proj
				throw new ProjectNotFoundException("Project not found in your account.");
			} else if (existentProject==null){
				throw new ProjectNotFoundException("Project with ID '" + project.getProjectIdentifier() + "' cannot be updated; it doesn't exist.");

			}
		}
		try {

			User user = userRepository.findByUsername(username);
			project.setUser(user);
			project.setProjectLeader(user.getUsername());
			String projectIdentifier = project.getProjectIdentifier().toUpperCase();
			project.setProjectIdentifier(projectIdentifier);

			if(project.getId()==null){
				Backlog backlog = new Backlog();
				project.setBacklog(backlog);
				backlog.setProject(project);
				backlog.setProjectIdentifier(projectIdentifier);
			}

			if(project.getId()!=null){
				project.setBacklog(backlogRepository.findByProjectIdentifier(projectIdentifier));
			}
			return projectRepository.save(project);
		} catch (Exception e){
			throw new ProjectIdException("Project Id '" + project.getProjectIdentifier().toUpperCase()+"' already exists.");
		}
	}

	public Project findProjectByIdentifier(String projectId, String username){
		Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
		if (project == null) {
			throw new ProjectIdException(("Project does '" + projectId +"'not exist."));
		}

		if (!project.getProjectLeader().equals(username)){ //if person fetching isnot creator
			throw new ProjectNotFoundException("Project not in your account");
		}
		return projectRepository.findByProjectIdentifier(projectId.toUpperCase());
	}

	public Iterable<Project> findAllProjects(String username){
		return projectRepository.findAllByProjectLeader(username);
	}

	public void deleteProjectByIdentifier(String projectId, String username){
//		Project project = projectRepository.findByProjectIdentifier(projectId);
//
//		if(project == null){
//			throw new ProjectIdException(("Cannot delete project with ID'"+projectId +"'. This project does not exist."));
//		}
		System.out.println("Inside delete method of proj service");

		projectRepository.delete(findProjectByIdentifier(projectId,username));
	}
}
