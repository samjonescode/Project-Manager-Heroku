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
											  BindingResult result){
		//binding result analyzes the object to see if there are errors
		ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
		if(errorMap.getStatusCode().equals(HttpStatus.BAD_REQUEST)) return errorMap;

		Project p = projectService.saveOrUpdateProject(project);
		return new ResponseEntity<Project>(p, HttpStatus.CREATED);
	}

	@GetMapping("/{projectId}")
	public ResponseEntity<?> getProjectById(@PathVariable String projectId) {
		Project project = projectService.findProjectByIdentifier(projectId);
		return new ResponseEntity<Project>(project, HttpStatus.OK);
	}

	@GetMapping("/all")
	public Iterable<Project> getAllProjects(){ return projectService.findAllProjects();}

	@DeleteMapping("/{projectId}")
	public ResponseEntity<?> deleteProject(@PathVariable String projectId) {
		projectService.deleteProjectByIdentifier(projectId);

		return new ResponseEntity<String>("project with ID: '" + projectId +"' was deleted", HttpStatus.OK);
	}
	
}
