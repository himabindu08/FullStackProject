package com.info.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import com.info.domain.Project;
import com.info.exceptions.ProjectIdException;
import com.info.repository.ProjectRepository;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;

	@PostMapping("")
	public Project saveOrUpdateProject(Project project) {

	    try {
	    	project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			return projectRepository.save(project);
	    }catch(Exception e) {
	    	throw new ProjectIdException("Project ID" + " '" + project.getProjectIdentifier().toUpperCase() + "' already exists");
	    }

	}
	
	public Project findProjectByIdentifier(String projectId) {
		
		Project project= projectRepository.findByProjectIdentifier(projectId.toUpperCase());
		
		if(project== null) {
	    	throw new ProjectIdException("Project ID '" + projectId + "'does not exist");
		}
		return project;
	}
	
	public Iterable<Project> findAllProjects(){
		return projectRepository.findAll();
	}
	
	public void deleteProjectByIdentifier(String projectId) {
		
		Project project= projectRepository.findByProjectIdentifier(projectId.toUpperCase());
		if(project== null) {
	    	throw new ProjectIdException("Cannot Delete Project with Project ID '" + projectId + "'");
		} 
		 projectRepository.delete(project);
	}
	
	
}

