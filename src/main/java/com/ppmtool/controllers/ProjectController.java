package com.ppmtool.controllers;

import com.ppmtool.domain.Project;
import com.ppmtool.services.MapValidationErrorService;
import com.ppmtool.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/project")
@CrossOrigin
public class ProjectController {
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private MapValidationErrorService mapValidationErrorService;

	public ProjectController() {
	}

	@PostMapping("")
	public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project,
											  BindingResult result, Principal principal){
		//binding result analyzes the object to see if there are errors
		ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
		if(errorMap.getStatusCode().equals(HttpStatus.BAD_REQUEST)) return errorMap;

		Project p = projectService.saveOrUpdateProject(project, principal.getName()); //saves the user who made project
		return new ResponseEntity<Project>(p, HttpStatus.CREATED);
	}

	@GetMapping("/{projectId}")
	public ResponseEntity<?> getProjectById(@PathVariable String projectId, Principal principal) {
		Project project = projectService.findProjectByIdentifier(projectId, principal.getName());
		return new ResponseEntity<Project>(project, HttpStatus.OK);
	}

	@GetMapping("/all")
	public Iterable<Project> getAllProjects(Principal principal){ return projectService.findAllProjects(principal.getName());}

	@DeleteMapping("/{projectId}")
	public ResponseEntity<?> deleteProject(@PathVariable String projectId, Principal principal) {
		projectService.deleteProjectByIdentifier(projectId, principal.getName());

		return new ResponseEntity<String>("project with ID: '" + projectId +"' was deleted", HttpStatus.OK);
	}
}
