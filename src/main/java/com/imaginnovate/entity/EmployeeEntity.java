package com.imaginnovate.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;


@Data
public class EmployeeEntity {

	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "First name is mandatory")
    private String firstName;
    
    @NotNull(message = "Last name is mandatory")
    private String lastName;
    
    @NotNull
    @Email(message = "Email should be valid")
    private String email;
    
    @NotEmpty(message = "Phone numbers are mandatory")
    @ElementCollection
    private List<@Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Phone number is invalid") String> phoneNumbers;
    
    @NotNull(message = "Date of joining is mandatory")
    private LocalDate doj;
    
    @NotNull(message = "Salary is mandatory")
    @Min(value = 0, message = "Salary must be positive")
    private Double salary;

}
