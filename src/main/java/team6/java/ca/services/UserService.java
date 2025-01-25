package team6.java.ca.services;

import java.time.LocalDate;
import java.util.List;

import team6.java.ca.entities.Employee;
import team6.java.ca.entities.EmployeeType;
import team6.java.ca.entities.User;



public interface UserService {
	List<User> findAllUsers();
	User findUserByUserId(long userId);
	User findUserByUsername(String username);
	
	User createAdmin(String username, String password);
	
	User createEmployee(String username, String password, String firstName, String lastName, String email,
            EmployeeType empType, LocalDate joinDate, Employee.EmpStatus empStatus,
            Employee manager, boolean isManager);
	
    User updateUser(long userId, User user);
    void deleteUser(long userId);
    boolean existsByUsername(String username);
}

