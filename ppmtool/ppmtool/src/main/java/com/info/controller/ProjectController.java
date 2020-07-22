package com.info.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.info.domain.Project;
import com.info.service.MapValidationErrorService;
import com.info.service.ProjectService;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

	@Autowired
	ProjectService projectService;
	
	@Autowired
	MapValidationErrorService mapValidationErrorService;
	
	
	@PostMapping
	public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult result ){
		
		ResponseEntity<?> errorMap= mapValidationErrorService.mapValidationErrorService(result);
		if(errorMap!=null) return errorMap;
	 	
		Project project1= projectService.saveOrUpdateProject(project);
		return new ResponseEntity<Project> (project1, HttpStatus.CREATED );
	}
	
	@GetMapping("/{projectId}")
	public ResponseEntity<?> findProjectById(@PathVariable String projectId){
		Project project= projectService.findProjectByIdentifier(projectId);
		return new ResponseEntity<Project>(project, HttpStatus.OK);
	}
	
	@GetMapping("/all")
	public Iterable<Project> findAllProjects(){
		return projectService.findAllProjects();
	}
	
	@DeleteMapping("/{projectId}")
	public ResponseEntity<?> deleteProjectById(@PathVariable String projectId){
		projectService.deleteProjectByIdentifier(projectId);
        return new ResponseEntity<String>("Project with ID '" + projectId + "' was deleted", HttpStatus.OK);
	}
	
	
}
