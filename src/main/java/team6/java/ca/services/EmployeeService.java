package team6.java.ca.services;

import java.util.List;

import team6.java.ca.entities.Employee;

public interface EmployeeService {

	List<Employee> findEmployeeByName(String name);

	List<Employee> findAllEmployees();

	List<Employee> findAllManagers();

	List<Employee> findAllEmployeesOfMgr(Long managerId);

	Employee findEmployeeByUserId(Long userId);

	Employee findEmployeeByUsername(String username);

	boolean existsByUsername(String username);

	String findEmailByUserId(Long userId);

	List<Employee> findEmployeesByEmpType(Long empTypeId);

	Employee createEmployee(Employee employee);

	Employee updateEmployee(Long userId, Employee employee);

	void deleteEmployee(Long userId);

	Employee findManagerByEmpId(Long empId);

	void changeManager(long wishingEmployeeId, long preferManagerId);

	void save(Employee e);

	List<Employee> findAllByIsManagerTrueAndUserIdNot(Long empId);

	void reassignSubordinates(Long managerId, Employee newManager);

	boolean existsByEmail(String email);

	List<Employee> findAllExceptCurrentOne(Long userId);
}
