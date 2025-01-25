package team6.java.ca;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import jakarta.transaction.Transactional;
import team6.java.ca.entities.Employee;
import team6.java.ca.entities.EmployeeLeaveRecord;
import team6.java.ca.entities.LeaveType;
import team6.java.ca.repositories.EmployeeLeaveRecordRepository;
import team6.java.ca.repositories.EmployeeRepository;
import team6.java.ca.repositories.LeaveTypeRepository;


import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class EmployeeLeaveRecordRepositoryTest {

    @Autowired
    private EmployeeLeaveRecordRepository employeeLeaveRecordRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private LeaveTypeRepository leaveTypeRepository;
    
    private void deleteRecord()
    {
    	leaveTypeRepository.deleteAll();
    	employeeRepository.deleteAll();
    }

    @Test
    @Transactional
    public void testFindAllLeaveRecordsOfEmployee() {
    	
    	deleteRecord();

        Employee employee = new Employee("thet", "password", "Thet", "Soe", "thetsoe@example.com", null, LocalDate.now(), Employee.EmpStatus.ACTIVE, null, false);
        employeeRepository.save(employee);

        LeaveType leaveType = new LeaveType("Vacation");
        leaveTypeRepository.save(leaveType);

        EmployeeLeaveRecord leaveRecord = new EmployeeLeaveRecord(LocalDate.now(), LocalDate.now().plusDays(5), false, employee, leaveType, EmployeeLeaveRecord.Status.Applied, null, null, "Vacation", false, "N/A");
        employeeLeaveRecordRepository.save(leaveRecord);

        List<EmployeeLeaveRecord> leaveRecords = employeeLeaveRecordRepository.findAllLeaveRecordsOfEmployee(employee.getUserId());


        assertThat(leaveRecords).isNotEmpty();
        assertThat(leaveRecords.get(0).getEmployee().getUserId()).isEqualTo(employee.getUserId());
    }

    @Test
    @Transactional

    public void testFindAllLeaveRecordsOfLeaveType() {
    	deleteRecord();

        Employee employee = new Employee("thet", "password", "Thet", "Soe", "thetsoe@example.com", null, LocalDate.now(), Employee.EmpStatus.ACTIVE, null, false);
        employeeRepository.save(employee);

        LeaveType leaveType = new LeaveType("Sick Leave");
        leaveTypeRepository.save(leaveType);

        EmployeeLeaveRecord leaveRecord = new EmployeeLeaveRecord(LocalDate.now(), LocalDate.now().plusDays(3), false, employee, leaveType, EmployeeLeaveRecord.Status.Applied, null, null, "Sick Leave", false, "N/A");
        employeeLeaveRecordRepository.save(leaveRecord);

        
        List<EmployeeLeaveRecord> leaveRecords = employeeLeaveRecordRepository.findAllLeaveRecordsOfLeaveType(leaveType.getLeaveTypeId());

        assertThat(leaveRecords).isNotEmpty();
        assertThat(leaveRecords.get(0).getLeaveType().getLeaveTypeId()).isEqualTo(leaveType.getLeaveTypeId());
    }

    @Test
    @Transactional
    public void testFindAllLeaveRecordsOfMgr() {
    	
    	deleteRecord();

        Employee manager = new Employee("managerthet", "password", "Manager", "Thet", "managerthet.user@example.com", null, LocalDate.now(), Employee.EmpStatus.ACTIVE, null, true);
        employeeRepository.save(manager);

        Employee employee = new Employee("thet", "password", "Thet", "Soe", "thetsoe@example.com", null, LocalDate.now(), Employee.EmpStatus.ACTIVE, manager, false);
        employeeRepository.save(employee);

        LeaveType leaveType = new LeaveType("Personal Leave");
        leaveTypeRepository.save(leaveType);

        EmployeeLeaveRecord leaveRecord = new EmployeeLeaveRecord(LocalDate.now(), LocalDate.now().plusDays(2), false, employee, leaveType, EmployeeLeaveRecord.Status.Applied, manager, null, "Personal Leave", false, "N/A");
        employeeLeaveRecordRepository.save(leaveRecord);

      
        List<EmployeeLeaveRecord> leaveRecords = employeeLeaveRecordRepository.findAllLeaveRecordsOfMgr(manager.getUserId());

      
        assertThat(leaveRecords).isNotEmpty();
        assertThat(leaveRecords.get(0).getApproveManager().getUserId()).isEqualTo(manager.getUserId());
    }

    @Test
    @Transactional
    public void testFindAllLeaveRecordsByStatus() {
    	
    	deleteRecord();

        Employee employee = new Employee("thet", "password", "Thet", "Soe", "thetsoe@example.com", null, LocalDate.now(), Employee.EmpStatus.ACTIVE, null, false);
        employeeRepository.save(employee);

        LeaveType leaveType = new LeaveType("Maternity Leave");
        leaveTypeRepository.save(leaveType);

        EmployeeLeaveRecord leaveRecord = new EmployeeLeaveRecord(LocalDate.now(), LocalDate.now().plusDays(30), false, employee, leaveType, EmployeeLeaveRecord.Status.Approved, null, null, "Maternity Leave", false, "N/A");
        employeeLeaveRecordRepository.save(leaveRecord);

        List<EmployeeLeaveRecord> leaveRecords = employeeLeaveRecordRepository.findAllLeaveRecordsByStatus(EmployeeLeaveRecord.Status.Approved);

        
        assertThat(leaveRecords).isNotEmpty();
        assertThat(leaveRecords.get(0).getStatus()).isEqualTo(EmployeeLeaveRecord.Status.Approved);
    }

    @Test
    @Transactional
    public void testFindEmployeeLeaveRecordById() {
    	
    	deleteRecord();

        Employee employee = new Employee("thet", "password", "Thet", "Soe", "thetsoe@example.com", null, LocalDate.now(), Employee.EmpStatus.ACTIVE, null, false);
        employeeRepository.save(employee);

        LeaveType leaveType = new LeaveType("Annual Leave");
        leaveTypeRepository.save(leaveType);

        EmployeeLeaveRecord leaveRecord = new EmployeeLeaveRecord(LocalDate.now(), LocalDate.now().plusDays(10), false, employee, leaveType, EmployeeLeaveRecord.Status.Applied, null, null, "Annual Leave", false, "N/A");
        employeeLeaveRecordRepository.save(leaveRecord);

        EmployeeLeaveRecord foundRecord = employeeLeaveRecordRepository.findEmployeeLeaveRecordById(leaveRecord.getLeaveId());

     
        assertThat(foundRecord).isNotNull();
        assertThat(foundRecord.getLeaveId()).isEqualTo(leaveRecord.getLeaveId());
    }

    @Test
    @Transactional
    public void testFindAllByLeaveDateBetween() {
    	
    	deleteRecord();

        Employee employee = new Employee("thet", "password", "Thet", "Soe", "thetsoe@example.com", null, LocalDate.now(), Employee.EmpStatus.ACTIVE, null, false);
        employeeRepository.save(employee);

        LeaveType leaveType = new LeaveType("Casual Leave");
        leaveTypeRepository.save(leaveType);

        EmployeeLeaveRecord leaveRecord = new EmployeeLeaveRecord(LocalDate.now(), LocalDate.now().plusDays(5), false, employee, leaveType, EmployeeLeaveRecord.Status.Applied, null, null, "Casual Leave", false, "N/A");
        employeeLeaveRecordRepository.save(leaveRecord);

      
        List<EmployeeLeaveRecord> leaveRecords = employeeLeaveRecordRepository.findAllByLeaveDateBetween(LocalDate.now(), LocalDate.now().plusDays(5));

        assertThat(leaveRecords).isNotEmpty();
        assertThat(leaveRecords.get(0).getLeaveDate()).isEqualTo(leaveRecord.getLeaveDate());
    }

    @Test
    @Transactional
    public void testFindAllByCoveringEmployee() {
    	deleteRecord();

        Employee employee = new Employee("thet", "password", "Thet", "Soe", "thetsoe@example.com", null, LocalDate.now(), Employee.EmpStatus.ACTIVE, null, false);
        employeeRepository.save(employee);

        Employee coveringEmployee = new Employee("invoker", "password", "invoker", "karl", "invoker.karl@example.com", null, LocalDate.now(), Employee.EmpStatus.ACTIVE, null, false);
        employeeRepository.save(coveringEmployee);

        LeaveType leaveType = new LeaveType("Paternity Leave");
        leaveTypeRepository.save(leaveType);

        EmployeeLeaveRecord leaveRecord = new EmployeeLeaveRecord(LocalDate.now(), LocalDate.now().plusDays(7), false, employee, leaveType, EmployeeLeaveRecord.Status.Applied, null, coveringEmployee, "Paternity Leave", false, "N/A");
        employeeLeaveRecordRepository.save(leaveRecord);

        
        List<EmployeeLeaveRecord> leaveRecords = employeeLeaveRecordRepository.findAllByCoveringEmployee(coveringEmployee.getUserId());

        
        assertThat(leaveRecords).isNotEmpty();
        assertThat(leaveRecords.get(0).getCoveringEmployee().getUserId()).isEqualTo(coveringEmployee.getUserId());
    }

    @Test
    @Transactional
    public void testExistsByLeaveId() {
    	deleteRecord();

        Employee employee = new Employee("thet", "password", "Thet", "Soe", "thetsoe@example.com", null, LocalDate.now(), Employee.EmpStatus.ACTIVE, null, false);
        employeeRepository.save(employee);

        LeaveType leaveType = new LeaveType("Bereavement Leave");
        leaveTypeRepository.save(leaveType);

        EmployeeLeaveRecord leaveRecord = new EmployeeLeaveRecord(LocalDate.now(), LocalDate.now().plusDays(3), false, employee, leaveType, EmployeeLeaveRecord.Status.Applied, null, null, "Bereavement Leave", false, "N/A");
        employeeLeaveRecordRepository.save(leaveRecord);

  
        boolean exists = employeeLeaveRecordRepository.existsByLeaveId(leaveRecord.getLeaveId());

     
        assertThat(exists).isTrue();
    }
}

