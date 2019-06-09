package com.marcbengsch.ppmtool.services;


import com.marcbengsch.ppmtool.domain.Project;
import com.marcbengsch.ppmtool.exceptions.ProjectIdException;
import com.marcbengsch.ppmtool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

	@Autowired
	ProjectRepository projectRepository;


	public Project saveOrUpdateProject(Project project){

		try{
			project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			return projectRepository.save(project);
		}catch(Exception e){
			throw new ProjectIdException("Project ID " + project.getProjectIdentifier().toUpperCase() + " already exists");
		}


	}
}
