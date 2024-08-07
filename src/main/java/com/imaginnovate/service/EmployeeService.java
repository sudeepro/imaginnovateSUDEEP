package com.imaginnovate.service;

import java.util.Optional;

import com.imaginnovate.entity.EmployeeEntity;

public interface EmployeeService {

	public EmployeeEntity addEmployee(EmployeeEntity employee);

	public double calculateTax(double yearlySalary);

	public double calculateYearlySalary(EmployeeEntity employee, int financialYear);

	public double calculateCess(double yearlySalary);

	public Optional<EmployeeEntity> findEmployeeById(Long id);

}
