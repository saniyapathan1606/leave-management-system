package team6.java.ca.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import team6.java.ca.entities.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	@Query("SELECT e FROM Employee e WHERE e.firstName LIKE %:name% OR e.lastName LIKE %:name%")
	List<Employee> findByLastnameOrFirstname(@Param("name") String name);

	@Query("SELECT e FROM Employee e WHERE e.manager.userId = :managerId")
	List<Employee> findEmployeesUnderMgr(@Param("managerId") Long managerId);

	@Query("SELECT e FROM Employee e")
	List<Employee> findAllEmployees();

	@Query("SELECT e.manager.userId = :pMgrId FROM Employee e WHERE e.userId = :wEmpId")
	void changeManager(@Param("wEmpId") long wishingEmployeeId, @Param("pMgrId") long preferManagerId);

	@Query("SELECT e FROM Employee e WHERE e.isManager = true")
	List<Employee> findAllManagers();

	@Query("SELECT e FROM Employee e WHERE e.userId = :userId")
	Employee findEmployeeByUserId(@Param("userId") Long userId);

	@Query("SELECT e FROM Employee e WHERE e.username = :username")
	Employee findEmployeeByUsername(@Param("username") String username);

	@Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM Employee e WHERE e.username = :username")
	boolean existsByUsername(@Param("username") String username);

	@Query("SELECT e.email FROM Employee e WHERE e.userId = :userId")
	String findEmailByUserId(@Param("userId") Long userId);

	@Query("SELECT e FROM Employee e WHERE e.empType.empTypeId = :empTypeId")
	List<Employee> findEmployeesByEmpType(@Param("empTypeId") Long empTypeId);

	@Query("SELECT e.manager FROM Employee e WHERE e.userId = :empId AND e.isManager = true")
	Employee findManagerByEmpId(@Param("empId") Long empId);

	@Query("SELECT e FROM Employee e WHERE e.isManager = true AND e.userId != :userId")
	List<Employee> findAllByIsManagerTrueAndUserIdNot(@Param("userId") Long userId);

	@Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM Employee e WHERE e.email = :email")
	boolean existsByEmail(@Param("email") String email);

	@Query("SELECT e FROM Employee e WHERE e.userId != :userId")
	List<Employee> findAllExceptCurrentOne(@Param("userId") Long userId);

}
