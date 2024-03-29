package com.marcbengsch.ppmtool.web;

import com.marcbengsch.ppmtool.domain.Project;
import com.marcbengsch.ppmtool.services.MapValidationErrorService;
import com.marcbengsch.ppmtool.services.ProjectService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
	private
	MapValidationErrorService mapValidationErrorService;

	@PostMapping("")
	public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult result, Principal principal){

		ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
		if(errorMap!=null) return errorMap;

		Project project1 = projectService.saveOrUpdateProject(project, principal.getName());
		return new ResponseEntity<Project>(project, HttpStatus.CREATED);
	}

	@GetMapping("/{projectId}")
	public ResponseEntity<?> getProjectById(@PathVariable String projectId, Principal principal){

		Project project = projectService.findProjectByIdentifier(projectId, principal.getName());

		return new ResponseEntity<Project>(project, HttpStatus.OK);
	}


	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Bearer JWT Token", paramType = "header")
	})
	@GetMapping("/all")
	public Iterable<Project> getAllProjects(Principal principal){return projectService.findAllByProjects(principal.getName());}


	@DeleteMapping("/{projectId}")
	public ResponseEntity<?> deleteproject(@PathVariable String projectId, Principal principal){
		projectService.deleteProjectByIdentifier(projectId, principal.getName());

		return new ResponseEntity<>("Project with ID: " + projectId + " was deleted", HttpStatus.OK);
	}
}
