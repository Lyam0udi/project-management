package com.pma.pma.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pma.pma.dao.EmployeeRepository;
import com.pma.pma.dao.ProjectRepository;
import com.pma.pma.dto.ChartData;
import com.pma.pma.dto.EmployeeProject;
import com.pma.pma.entities.Project;

@Controller
public class HomeController {
	
	@Autowired
	ProjectRepository proRepo;
	@Autowired
	EmployeeRepository empRepo;
	
	@GetMapping("/")
	public String displayHome(Model model) throws JsonProcessingException {
		
		/// convert to Json
		Map<String, Object> map = new HashMap<>();
		
		// we are quering the database for projects
		List<Project> projects = proRepo.findAll();
		model.addAttribute("projectsList", projects);
		
		/// get project status
		List<ChartData> projectData = proRepo.getProjectStatus();
		
		
		// converting projectDatainto object json structure
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonString = objectMapper.writeValueAsString(projectData);
		
		model.addAttribute("projectStatusCnt", jsonString);
		
		// we are quering the database for employees
		List<EmployeeProject> employeesProjectCnt = empRepo.employeeProjects();
		model.addAttribute("employeesListProjectsCnt", employeesProjectCnt);
		
		return "main/home";
	}
	
    @GetMapping("/protechvitrine")
    public String showProTechVitrinePage() {
        return "main/protechvitrine";
    }
	

}
