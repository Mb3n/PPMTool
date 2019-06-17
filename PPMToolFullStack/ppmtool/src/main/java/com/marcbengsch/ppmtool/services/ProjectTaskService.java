package com.marcbengsch.ppmtool.services;

import com.marcbengsch.ppmtool.domain.ProjectTask;
import com.marcbengsch.ppmtool.repositories.BacklogRepository;
import com.marcbengsch.ppmtool.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskService {

	@Autowired
	private BacklogRepository backlogRepository;

	@Autowired
	private ProjectTaskRepository projectTaskRepository;

	public ProjectTask addProjectTask(){
		//PT's to be added to a specific project, project != null, Backlog exists
		//set the Backlog to the PT
		//We want our project sequence to be like this: IDPRO-1 IDPRO-2 ... 100 101
		//Update the Backlog SEQUENCE

		//INITIAL priority when priority null
		//INITIAL status when status is null
	}
}
