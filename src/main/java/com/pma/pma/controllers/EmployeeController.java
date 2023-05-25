package com.pma.pma.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pma.pma.dao.EmployeeRepository;
import com.pma.pma.entities.Employee;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
	
	@Autowired
	EmployeeRepository empRepo;
	
	@GetMapping
	public String displayEmployees(Model model) {
		List<Employee> employees = empRepo.findAll();
		model.addAttribute("employees", employees);
		return "employees/list-employees";
	}
	
	@GetMapping("/new")
	public String displayEmployeeForm(Model model) {
		
		Employee anEmployee = new Employee();
		model.addAttribute("employee", anEmployee);
		return "employees/new-employee";
	}
	
	@PostMapping("/save")
	public String createEmployee(Employee employee, Model model) {
		// this should handel saving to the database...
		empRepo.save(employee);
		
		// use a redirect to prevent duplicate submissions
		return "redirect:/employees/new";
		
	}
	
	@GetMapping("/delete")
	public String deleteEmployee(@RequestParam("id") Long id) {
		empRepo.deleteById(id);
		return "redirect:/employees";
	}
	
	@GetMapping("/update")
    public String displayUpdateForm(@RequestParam("id") Long id, Model model) {
        Employee employee = empRepo.findById(id).orElse(null);
        model.addAttribute("employee", employee);
        return "employees/update-employee";
    }

    @PostMapping("/update")
    public String updateEmployee(Employee employee, Model model) {
        empRepo.save(employee);
        return "redirect:/employees";
    }
}
