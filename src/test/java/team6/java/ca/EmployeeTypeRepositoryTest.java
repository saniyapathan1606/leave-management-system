package team6.java.ca;



import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import team6.java.ca.entities.Employee;
import team6.java.ca.entities.EmployeeType;
import team6.java.ca.entities.LeaveEntitlement;
import team6.java.ca.repositories.EmployeeTypeRepository;


@SpringBootTest
public class EmployeeTypeRepositoryTest {

    @Autowired
    private EmployeeTypeRepository employeeTypeRepository;

    @Test
    public void testGetEmpTypeById() {
    	
    	employeeTypeRepository.deleteAll();
        EmployeeType employeeType = new EmployeeType("Professional");
        employeeTypeRepository.save(employeeType);

    
        EmployeeType found = employeeTypeRepository.getEmpTypeById(employeeType.getEmpTypeId());

    
        assertThat(found).isNotNull();
        assertThat(found.getEmpTypeName()).isEqualTo(employeeType.getEmpTypeName());
    }

    @Test
    public void testGetEmpTypeNameById() {
    	employeeTypeRepository.deleteAll();
        EmployeeType employeeType = new EmployeeType("Adminstrative");
        employeeTypeRepository.save(employeeType);

       
        String empTypeName = employeeTypeRepository.getEmpTypeNameById(employeeType.getEmpTypeId());

   
        assertThat(empTypeName).isEqualTo("Adminstrative");
    }

    @Test
    public void testGetLeaveEntitlementByEmpTypeId() {
    	employeeTypeRepository.deleteAll();
        EmployeeType employeeType = new EmployeeType("Contractor");
        employeeTypeRepository.save(employeeType);

     
        List<LeaveEntitlement> leaveEntitlements = employeeTypeRepository.getLeaveEntitlementByEmpTypeId(employeeType.getEmpTypeId());

        
        assertThat(leaveEntitlements).isEmpty(); 
    }

    @Test
    public void testGetLeaveEntitlementByEmpTypeName() {
    	employeeTypeRepository.deleteAll();
        EmployeeType employeeType = new EmployeeType("Intern");
        employeeTypeRepository.save(employeeType);

       
        List<LeaveEntitlement> leaveEntitlements = employeeTypeRepository.getLeaveEntitlementByEmpTypeName("Intern");

   
        assertThat(leaveEntitlements).isEmpty(); 
    }

    @Test
    public void testGetEmployeesByEmpTypeId() {
    	employeeTypeRepository.deleteAll();
        EmployeeType employeeType = new EmployeeType("Temporary");
        employeeTypeRepository.save(employeeType);

    
        List<Employee> employees = employeeTypeRepository.getEmployeesByEmpTypeId(employeeType.getEmpTypeId());

      
        assertThat(employees).isEmpty(); 
    }

    @Test
    public void testGetEmployeesByEmpTypeName() {
    	employeeTypeRepository.deleteAll();
        EmployeeType employeeType = new EmployeeType("Seasonal");
        employeeTypeRepository.save(employeeType);

       
        List<Employee> employees = employeeTypeRepository.getEmployeesByEmpTypeName("Seasonal");

    
        assertThat(employees).isEmpty(); 
    }
    

    @Test
    public void testInsertData() {
        employeeTypeRepository.deleteAll();
        EmployeeType professional = new EmployeeType("Professional");
        EmployeeType administrative = new EmployeeType("Administrative");

        employeeTypeRepository.save(professional);
        employeeTypeRepository.save(administrative);

        List<EmployeeType> employeeTypes = employeeTypeRepository.findAll();
        assertThat(employeeTypes).hasSize(2);
        assertThat(employeeTypes).extracting(EmployeeType::getEmpTypeName).containsExactlyInAnyOrder("Professional", "Administrative");
    }
}
