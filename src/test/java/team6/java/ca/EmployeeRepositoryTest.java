package team6.java.ca;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import team6.java.ca.entities.Employee;
import team6.java.ca.entities.EmployeeType;
import team6.java.ca.repositories.EmployeeRepository;
import team6.java.ca.repositories.EmployeeTypeRepository;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeTypeRepository employeeTypeRepository;

    @Test
    @Transactional
    @Rollback(false)
    public void testFindByLastnameOrFirstname() {
    	employeeRepository.deleteAll();
        Employee employee = new Employee("testuser1", "password", "John", "Doe", "john.doe@example.com", null, LocalDate.now(), Employee.EmpStatus.ACTIVE, null, false);
        employeeRepository.save(employee);

        List<Employee> employees = employeeRepository.findByLastnameOrFirstname("John");

        assertThat(employees).isNotEmpty();
        assertThat(employees.get(0).getFirstName()).isEqualTo("John");
    }

    @Test
    @Transactional
    @Rollback(false)
    public void testFindEmployeesUnderMgr() {
    	employeeRepository.deleteAll();
        Employee manager = new Employee("manageruser", "password", "Manager", "User", "manager.user@example.com", null, LocalDate.now(), Employee.EmpStatus.ACTIVE, null, true);
        employeeRepository.save(manager);

        Employee employee = new Employee("testuser2", "password", "Jane", "Doe", "jane.doe@example.com", null, LocalDate.now(), Employee.EmpStatus.ACTIVE, manager, false);
        employeeRepository.save(employee);

        List<Employee> employees = employeeRepository.findEmployeesUnderMgr(manager.getUserId());

        assertThat(employees).isNotEmpty();
        assertThat(employees.get(0).getManager().getUserId()).isEqualTo(manager.getUserId());
    }

    @Test
    @Transactional
    @Rollback(false)
    public void testFindAllEmployees() {
    	employeeRepository.deleteAll();
    	Employee employee = new Employee("testuser1", "password", "John", "Doe", "john.doe@example.com", null, LocalDate.now(), Employee.EmpStatus.ACTIVE, null, false);
        employeeRepository.save(employee);
        List<Employee> employees = employeeRepository.findAllEmployees();

        assertThat(employees).isNotEmpty();
    }

    @Test
    @Transactional
    @Rollback(false)
    public void testFindAllManagers() {
    	employeeRepository.deleteAll();
        Employee manager = new Employee("manageruser2", "password", "Alice", "Smith", "alice.smith@example.com", null, LocalDate.now(), Employee.EmpStatus.ACTIVE, null, true);
        employeeRepository.save(manager);

        List<Employee> managers = employeeRepository.findAllManagers();

        assertThat(managers).isNotEmpty();
        assertThat(managers.get(0).isManager()).isTrue();
    }

    @Test
    @Transactional
    @Rollback(false)
    public void testFindEmployeeByUserId() {
    	employeeRepository.deleteAll();
        Employee employee = new Employee("testuser3", "password", "Bob", "Johnson", "bob.johnson@example.com", null, LocalDate.now(), Employee.EmpStatus.ACTIVE, null, false);
        employeeRepository.save(employee);

        Employee foundEmployee = employeeRepository.findEmployeeByUserId(employee.getUserId());

        assertThat(foundEmployee).isNotNull();
        assertThat(foundEmployee.getUserId()).isEqualTo(employee.getUserId());
    }

    @Test
    @Transactional
    @Rollback(false)
    public void testFindEmployeeByUsername() {
    	employeeRepository.deleteAll();
        Employee employee = new Employee("testuser4", "password", "Charlie", "Brown", "charlie.brown@example.com", null, LocalDate.now(), Employee.EmpStatus.ACTIVE, null, false);
        employeeRepository.save(employee);

        Employee foundEmployee = employeeRepository.findEmployeeByUsername(employee.getUsername());

        assertThat(foundEmployee).isNotNull();
        assertThat(foundEmployee.getUsername()).isEqualTo(employee.getUsername());
    }

    @Test
    @Transactional
    @Rollback(false)
    public void testExistsByUsername() {
    	employeeRepository.deleteAll();
        Employee employee = new Employee("testuser5", "password", "David", "Miller", "david.miller@example.com", null, LocalDate.now(), Employee.EmpStatus.ACTIVE, null, false);
        employeeRepository.save(employee);

        boolean exists = employeeRepository.existsByUsername(employee.getUsername());

        assertThat(exists).isTrue();
    }

    @Test
    @Transactional
    @Rollback(false)
    public void testFindEmailByUserId() {
    	employeeRepository.deleteAll();
        Employee employee = new Employee("testuser6", "password", "Eve", "Taylor", "eve.taylor@example.com", null, LocalDate.now(), Employee.EmpStatus.ACTIVE, null, false);
        employeeRepository.save(employee);

        String email = employeeRepository.findEmailByUserId(employee.getUserId());

        assertThat(email).isEqualTo(employee.getEmail());
    }

    @Test
    @Transactional
    @Rollback(false)
    public void testFindEmployeesByEmpType() {
    	employeeTypeRepository.deleteAll();
    	employeeRepository.deleteAll();
    	
        EmployeeType empType = new EmployeeType("Professional");
        employeeTypeRepository.save(empType);

        Employee employee = new Employee("testuser7", "password", "Frank", "Clark", "frank.clark@example.com", empType, LocalDate.now(), Employee.EmpStatus.ACTIVE, null, false);
        employeeRepository.save(employee);

        List<Employee> employees = employeeRepository.findEmployeesByEmpType(empType.getEmpTypeId());

        assertThat(employees).isNotEmpty();
        assertThat(employees.get(0).getEmpType().getEmpTypeId()).isEqualTo(empType.getEmpTypeId());
    }
}

