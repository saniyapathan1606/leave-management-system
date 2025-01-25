package team6.java.ca;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import team6.java.ca.entities.CompensationClaimRecord;
import team6.java.ca.entities.CompensationClaimRecord.ClaimStatus;
import team6.java.ca.entities.Employee;
import team6.java.ca.entities.EmployeeType;
import team6.java.ca.repositories.CompensationClaimRecordRepository;
import team6.java.ca.repositories.EmployeeRepository;
import team6.java.ca.repositories.EmployeeTypeRepository;

@SpringBootTest
@Transactional
public class CompensationClaimRecordRepositoryTest {

    @Autowired
    private CompensationClaimRecordRepository compensationClaimRecordRepository;
    
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeTypeRepository employeeTypeRepository;
    
    public void deletionMethod()
    {
    	compensationClaimRecordRepository.deleteAll();
    	employeeRepository.deleteAll();
    	employeeTypeRepository.deleteAll();
    	
    }
    
    
    @Test
    public void testCreateAndFindClaimRecord() {
    	deletionMethod();
        EmployeeType employeeType = new EmployeeType("Developer");
        
        employeeTypeRepository.save(employeeType);

        Employee employee = new Employee("thet", "password", "Thet", "Soe", "thet.soe@gmail.com",
                employeeType, LocalDate.now(), Employee.EmpStatus.ACTIVE, null, false);
        
        employeeRepository.save(employee);

        Employee manager = new Employee("invoker", "password", "Invoker", "Karl", "invoker.karl@hotmail.com",
                employeeType, LocalDate.now(), Employee.EmpStatus.ACTIVE, null, true);
        
        employeeRepository.save(manager);

        CompensationClaimRecord claimRecord = new CompensationClaimRecord(employee, manager, 5.0, ClaimStatus.Pending, "Business trip");
        compensationClaimRecordRepository.save(claimRecord);

        CompensationClaimRecord foundClaimRecord = compensationClaimRecordRepository.findClaimByClaimId(claimRecord.getClaimId());

        assertThat(foundClaimRecord).isNotNull();
        assertThat(foundClaimRecord.getEmployee()).isEqualTo(employee);
        assertThat(foundClaimRecord.getApproveManager()).isEqualTo(manager);
        assertThat(foundClaimRecord.getClaimQty()).isEqualTo(5.0);
        assertThat(foundClaimRecord.getStatus()).isEqualTo(ClaimStatus.Pending);
        assertThat(foundClaimRecord.getRemarks()).isEqualTo("Business trip");
    }

    @Test
    public void testFindAllClaimRecords() {
        List<CompensationClaimRecord> claimRecords = compensationClaimRecordRepository.findAllClaimRecords();
        assertThat(claimRecords).isNotNull();
    }

    @Test
    public void testFindAllClaimRecordsOfEmployee() {
    	deletionMethod();
        EmployeeType employeeType = new EmployeeType("Developer");
        employeeTypeRepository.save(employeeType);

        Employee employee = new Employee("thet", "password", "Thet", "Soe", "thet.soe@gmail.com",
                employeeType, LocalDate.now(), Employee.EmpStatus.ACTIVE, null, false);
        
        employeeRepository.save(employee);


        Employee manager = new Employee("invoker", "password", "Invoker", "Karl", "invoker.karl@hotmail.com",
                employeeType, LocalDate.now(), Employee.EmpStatus.ACTIVE, null, true);
        
        employeeRepository.save(manager);

        CompensationClaimRecord claimRecord = new CompensationClaimRecord(employee, manager, 5.0, ClaimStatus.Pending, "Business trip");
        compensationClaimRecordRepository.save(claimRecord);

        List<CompensationClaimRecord> claimRecords = compensationClaimRecordRepository.findAllClaimRecordsOfEmployee(employee);
        assertThat(claimRecords).isNotNull();
        assertThat(claimRecords).isNotEmpty();
    }

    @Test
    public void testFindAllClaimRecordsOfMgr() {
    	deletionMethod();
        EmployeeType employeeType = new EmployeeType("Developer");
        
        employeeTypeRepository.save(employeeType);
        Employee employee = new Employee("thet", "password", "Thet", "Soe", "thet.soe@gmail.com",
                employeeType, LocalDate.now(), Employee.EmpStatus.ACTIVE, null, false);
        
        employeeRepository.save(employee);

        Employee manager = new Employee("invoker", "password", "Invoker", "Karl", "invoker.karl@hotmail.com",
                employeeType, LocalDate.now(), Employee.EmpStatus.ACTIVE, null, true);
        
        employeeRepository.save(manager);

        CompensationClaimRecord claimRecord = new CompensationClaimRecord(employee, manager, 5.0, ClaimStatus.Pending, "Business trip");
        compensationClaimRecordRepository.save(claimRecord);

        List<CompensationClaimRecord> claimRecords = compensationClaimRecordRepository.findAllClaimRecordsOfMgr(manager);
        assertThat(claimRecords).isNotNull();
        assertThat(claimRecords).isNotEmpty();
    }

    @Test
    public void testFindAllClaimRecordsByStatus() {
    	deletionMethod();
        EmployeeType employeeType = new EmployeeType("Developer");
        employeeTypeRepository.save(employeeType);

        Employee employee = new Employee("thet", "password", "Thet", "Soe", "thet.soe@gmail.com",
                employeeType, LocalDate.now(), Employee.EmpStatus.ACTIVE, null, false);
        employeeRepository.save(employee);

        Employee manager = new Employee("invoker", "password", "Invoker", "Karl", "invoker.karl@hotmail.com",
                employeeType, LocalDate.now(), Employee.EmpStatus.ACTIVE, null, true);
        employeeRepository.save(manager);

        CompensationClaimRecord claimRecord = new CompensationClaimRecord(employee, manager, 5.0, ClaimStatus.Pending, "Business trip");
        compensationClaimRecordRepository.save(claimRecord);

        List<CompensationClaimRecord> claimRecords = compensationClaimRecordRepository.findAllClaimRecordsByStatus(ClaimStatus.Pending);
        assertThat(claimRecords).isNotNull();
        assertThat(claimRecords).isNotEmpty();
    }

    @Test
    public void testExistsById() {
    	deletionMethod();
        EmployeeType employeeType = new EmployeeType("Developer");
        employeeTypeRepository.save(employeeType);
        
        Employee employee = new Employee("thet", "password", "Thet", "Soe", "thet.soe@gmail.com",
                employeeType, LocalDate.now(), Employee.EmpStatus.ACTIVE, null, false);
        employeeRepository.save(employee);
        

        Employee manager = new Employee("invoker", "password", "Invoker", "Karl", "invoker.karl@hotmail.com",
                employeeType, LocalDate.now(), Employee.EmpStatus.ACTIVE, null, true);
        employeeRepository.save(manager);
        
        CompensationClaimRecord claimRecord = new CompensationClaimRecord(employee, manager, 5.0, ClaimStatus.Pending, "Business trip");
        compensationClaimRecordRepository.save(claimRecord);

        boolean exists = compensationClaimRecordRepository.existsById(claimRecord.getClaimId());
        assertThat(exists).isTrue();
    }
}
