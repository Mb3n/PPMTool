package com.marcbengsch.ppmtool.services;

import com.marcbengsch.ppmtool.domain.Backlog;
import com.marcbengsch.ppmtool.domain.Project;
import com.marcbengsch.ppmtool.domain.ProjectTask;
import com.marcbengsch.ppmtool.exceptions.ProjectNotFoundException;
import com.marcbengsch.ppmtool.repositories.BacklogRepository;
import com.marcbengsch.ppmtool.repositories.ProjectRepository;
import com.marcbengsch.ppmtool.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskService {

	@Autowired
	private BacklogRepository backlogRepository;

	@Autowired
	private ProjectTaskRepository projectTaskRepository;

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private ProjectService projectService;

	public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask, String username){

		//Exceptions: Project not found

			//PTs to be added to a specific project, project != null, BL exists
			Backlog backlog = projectService.findProjectByIdentifier(projectIdentifier, username).getBacklog(); //backlogRepository.findByProjectIdentifier(projectIdentifier);

			//set the bl to pt
			projectTask.setBacklog(backlog);
			//we want our project sequence to be like this: IDPRO-1  IDPRO-2  ...100 101
			Integer BacklogSequence = backlog.getPTSequence();
			// Update the BL SEQUENCE
			BacklogSequence++;

			backlog.setPTSequence(BacklogSequence);

			//Add Sequence to Project Task
			projectTask.setProjectSequence(projectIdentifier+"-"+BacklogSequence);
			projectTask.setProjectIdentifier(projectIdentifier);

			//INITIAL priority when priority null
			if(projectTask.getPriority()==null || projectTask.getPriority()==0){ //In the future we need  projectTask.getPriority() == 0 to handle the form
				projectTask.setPriority(3);
			}
			//INITIAL status when status is null
			if(projectTask.getStatus()==""|| projectTask.getStatus()==null){
				projectTask.setStatus("TO_DO");
			}

			return projectTaskRepository.save(projectTask);
	}

	public Iterable<ProjectTask> findBacklogId(String id, String username){

		projectService.findProjectByIdentifier(id, username);

		return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
	}

	public ProjectTask findPTByProjectSequence(String backlog_id, String pt_id, String username){

		//make sure we are searching on the right backlog
		projectService.findProjectByIdentifier(backlog_id, username);

		//make sure that the task exists
		ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);

		if(projectTask == null){
			throw new ProjectNotFoundException("ProjectTask " + pt_id + " not found");
		}

		//make sure that the backlog/project id in the path corresponds to the right project
		if(!projectTask.getProjectIdentifier().equals(backlog_id)){
			throw new ProjectNotFoundException("Project Task " + pt_id + " does not exist in project " + backlog_id);
		}

		return projectTask;
	}



	public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String backlog_id, String pt_id, String username){
		ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id, username);

		projectTask = updatedTask;

		return projectTaskRepository.save(projectTask);
		//Update project task

		//find existing project task

		//replace it with updated task

		//save update
	}

	public void deletePTByProjectSequence(String backlog_id, String pt_id, String username){
		ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id, username);

		projectTaskRepository.delete(projectTask);
	}

}
