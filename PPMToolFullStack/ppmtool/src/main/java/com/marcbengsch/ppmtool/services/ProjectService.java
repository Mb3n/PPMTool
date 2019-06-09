package com.marcbengsch.ppmtool.services;


import com.marcbengsch.ppmtool.domain.Project;
import com.marcbengsch.ppmtool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

	@Autowired
	ProjectRepository projectRepository;


	public Project saveOrUpdateProject(Project project){

		//Logic

		return projectRepository.save(project);
	}
}
