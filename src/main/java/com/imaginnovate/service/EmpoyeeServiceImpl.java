package com.imaginnovate.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.imaginnovate.entity.EmployeeEntity;
import com.imaginnovate.repository.EmployeeRepository;


public class EmpoyeeServiceImpl implements EmployeeService {
	
	@Autowired
	private EmployeeRepository repo;

	@Override
	public EmployeeEntity addEmployee(EmployeeEntity employee) {
		   if (employee.getFirstName() == null || employee.getLastName() == null || employee.getEmail() == null ||
		            employee.getPhoneNumbers() == null || employee.getPhoneNumbers().isEmpty() ||
		            employee.getDoj() == null || employee.getSalary() == null) {
		            throw new IllegalArgumentException("All fields are mandatory");
		        }
		       
		        return repo.save(employee);
	}
	 public Optional<EmployeeEntity> findEmployeeById(Long id) {
	        return repo.findById(id);
	    }
	@Override
	public double calculateTax(double yearlySalary) {
		if (yearlySalary <= 250000) {
			return 0;
		} else if (yearlySalary <= 500000) {
			return (yearlySalary - 250000) * 0.05;
		} else if (yearlySalary <= 1000000) {
			return (yearlySalary - 500000) * 0.1 + 12500;
		} else {
			return (yearlySalary - 1000000) * 0.2 + 62500;
		}
	}

	@Override
	public double calculateYearlySalary(EmployeeEntity employee, int financialYear) {
		LocalDate startOfYear = LocalDate.of(financialYear, 4, 1);
        LocalDate endOfYear = LocalDate.of(financialYear + 1, 3, 31);
        LocalDate doj = employee.getDoj();

        if (doj.isAfter(endOfYear)) {
            return 0;
        }

        LocalDate effectiveStart = doj.isBefore(startOfYear) ? startOfYear : doj;
        long monthsWorked = ChronoUnit.MONTHS.between(effectiveStart.withDayOfMonth(1), endOfYear.withDayOfMonth(1));
        long daysWorkedInFirstMonth = ChronoUnit.DAYS.between(effectiveStart, effectiveStart.withDayOfMonth(effectiveStart.lengthOfMonth()));
        double lossOfPay = employee.getSalary() / 30 * (30 - daysWorkedInFirstMonth);

        return (monthsWorked * employee.getSalary()) - lossOfPay;
	}

	public double calculateCess(double yearlySalary) {
		if (yearlySalary > 2500000) {
			return (yearlySalary - 2500000) * 0.02;
		}
		return 0;
	}

}
