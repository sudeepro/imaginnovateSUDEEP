package com.imaginnovate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imaginnovate.entity.EmployeeEntity;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long>{

}
