package com.marcbengsch.ppmtool.services;


import com.marcbengsch.ppmtool.domain.Backlog;
import com.marcbengsch.ppmtool.domain.Project;
import com.marcbengsch.ppmtool.exceptions.ProjectIdException;
import com.marcbengsch.ppmtool.repositories.BacklogRepository;
import com.marcbengsch.ppmtool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

	@Autowired
	ProjectRepository projectRepository;

	@Autowired
	BacklogRepository backlogRepository;

	public Project saveOrUpdateProject(Project project){
		try{
			project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());

			if(project.getId() == null){
				Backlog backlog = new Backlog();
				project.setBacklog(backlog);
				backlog.setProject(project);
				backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			}

			if(project.getId() != null){
				project.setBacklog(backlogRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
			}

			return projectRepository.save(project);
		}catch(Exception e){
			throw new ProjectIdException("Project ID " + project.getProjectIdentifier().toUpperCase() + " already exists");
		}
	}

	public Project findProjectByIdentifier(String projectId){

		Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

		if(project == null){
			throw new ProjectIdException("Project with ID " + projectId + " doesn't exist");
		}

		return project;
	}

	public Iterable<Project> findAllByProjects(){
		return projectRepository.findAll();
	}


	public void deleteProjectByIdentifier(String projectId){
		Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

		if(project == null){
			throw new ProjectIdException("Cannot delete project with ID " + projectId + ". This project does not exist");
		}

		projectRepository.delete(project);
	}
}
