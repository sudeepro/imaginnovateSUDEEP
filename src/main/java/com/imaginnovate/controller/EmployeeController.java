package com.imaginnovate.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.imaginnovate.entity.EmployeeEntity;
import com.imaginnovate.service.EmployeeService;

import jakarta.validation.Valid;

@RestController
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@PostMapping
	public ResponseEntity<?> addEmployee(@Valid @RequestBody EmployeeEntity employee) {
		try {
			EmployeeEntity savedEmployee = employeeService.addEmployee(employee);
			return ResponseEntity.ok(savedEmployee);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/{id}/tax")
	public ResponseEntity<?> getEmployeeTax(@PathVariable Long id, @RequestParam int financialYear) {
		Optional<EmployeeEntity> optionalEmployee = employeeService.findEmployeeById(id);

		if (optionalEmployee.isPresent()) {
			EmployeeEntity employee = optionalEmployee.get();
			double yearlySalary = employeeService.calculateYearlySalary(employee, financialYear);
			double tax = employeeService.calculateTax(yearlySalary);
			double cess = employeeService.calculateCess(yearlySalary);

			Map<String, Object> response = new HashMap<>();
			response.put("employeeId", employee.getId());
			response.put("firstName", employee.getFirstName());
			response.put("lastName", employee.getLastName());
			response.put("yearlySalary", yearlySalary);
			response.put("taxAmount", tax);
			response.put("cessAmount", cess);

			return ResponseEntity.ok(response);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
