package team6.java.ca.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import team6.java.ca.entities.Employee;
import team6.java.ca.services.EmployeeService;

@Component
public class EmployeeValidator implements Validator {
	@Autowired
	private EmployeeService employeeService;

	@Override
	public boolean supports(Class<?> clazz) {
		return Employee.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// reject the repeated user name
		Employee employee = (Employee) target;
		String username = employee.getUsername();
		if (employeeService.existsByUsername(username)) {
			errors.rejectValue("username", "username.exists", "Username already exists");

		}
		String email = employee.getEmail();
		if (employeeService.existsByEmail(email)) {
			errors.rejectValue("email", "email.exists", "Email already used");

		}
	}

}
