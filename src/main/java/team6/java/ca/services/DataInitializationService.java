/*package team6.java.ca.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import team6.java.ca.entities.*;
import team6.java.ca.repositories.*;


@Service
public class DataInitializationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private CompensationClaimRecordRepository compensationClaimRecordRepository;

    @Autowired
    private EmployeeLeaveRecordRepository employeeLeaveRecordRepository;

    @Autowired
    private LeaveEntitlementRepository leaveEntitlementRepository;

    @Autowired
    private LeaveTypeRepository leaveTypeRepository;

    @Autowired
    private EmployeeTypeRepository employeeTypeRepository;
    
    @Autowired
    private PublicHolidayRepository publicHolidayRepository;

    
    @Transactional
    public void initData() {
    	
    	
        compensationClaimRecordRepository.deleteAll();
        employeeLeaveRecordRepository.deleteAll();
        leaveEntitlementRepository.deleteAll();
        leaveTypeRepository.deleteAll();
        employeeRepository.deleteAll();
        adminRepository.deleteAll();
        userRepository.deleteAll();
        
        PublicHoliday newYear = new PublicHoliday("New Year's Day", LocalDate.of(2024, 1, 1));
        PublicHoliday chineseNewYear1 = new PublicHoliday("Chinese New Year", LocalDate.of(2024, 2, 10));
        PublicHoliday chineseNewYear2 = new PublicHoliday("Chinese New Year", LocalDate.of(2024, 2, 11));
        PublicHoliday goodFriday = new PublicHoliday("Good Friday", LocalDate.of(2024, 3, 29));
        PublicHoliday hariRayaPuasa = new PublicHoliday("Hari Raya Puasa", LocalDate.of(2024, 4, 10));
        PublicHoliday labourDay = new PublicHoliday("Labour Day", LocalDate.of(2024, 5, 1));
        PublicHoliday vesakDay = new PublicHoliday("Vesak Day", LocalDate.of(2024, 5, 22));
        PublicHoliday hariRayaHaji = new PublicHoliday("Hari Raya Haji", LocalDate.of(2024, 6, 17));
        PublicHoliday nationalDay = new PublicHoliday("National Day", LocalDate.of(2024, 8, 9));
        PublicHoliday deepavali = new PublicHoliday("Deepavali", LocalDate.of(2024, 11, 1));
        PublicHoliday christmas = new PublicHoliday("Christmas Day", LocalDate.of(2024, 12, 25));

        // Save public holidays to the repository
        publicHolidayRepository.save(newYear);
        publicHolidayRepository.save(chineseNewYear1);
        publicHolidayRepository.save(chineseNewYear2);
        publicHolidayRepository.save(goodFriday);
        publicHolidayRepository.save(hariRayaPuasa);
        publicHolidayRepository.save(labourDay);
        publicHolidayRepository.save(vesakDay);
        publicHolidayRepository.save(hariRayaHaji);
        publicHolidayRepository.save(nationalDay);
        publicHolidayRepository.save(deepavali);
        publicHolidayRepository.save(christmas);
        
        

        // Create Employee Types
        EmployeeType adminType = new EmployeeType("Administrative");
        EmployeeType professionalType = new EmployeeType("Professional");
        employeeTypeRepository.save(adminType);
        employeeTypeRepository.save(professionalType);

        // Create Leave Types
        LeaveType annualLeave = new LeaveType("Annual");
        LeaveType medicalLeave = new LeaveType("Medical");
        LeaveType compensationLeave = new LeaveType("Compensation");
        leaveTypeRepository.save(annualLeave);
        leaveTypeRepository.save(medicalLeave);
        leaveTypeRepository.save(compensationLeave);

        // Create Leave Entitlements
        LeaveEntitlement adminAnnualLeave = new LeaveEntitlement(adminType, annualLeave, 14);
        LeaveEntitlement adminMedicalLeave = new LeaveEntitlement(adminType, medicalLeave, 60);
        LeaveEntitlement professionalAnnualLeave = new LeaveEntitlement(professionalType, annualLeave, 18);
        LeaveEntitlement professionalMedicalLeave = new LeaveEntitlement(professionalType, medicalLeave, 60);
        LeaveEntitlement professionalCompensationLeave = new LeaveEntitlement(professionalType, compensationLeave, 0);

        leaveEntitlementRepository.save(adminAnnualLeave);
        leaveEntitlementRepository.save(adminMedicalLeave);
        leaveEntitlementRepository.save(professionalAnnualLeave);
        leaveEntitlementRepository.save(professionalMedicalLeave);
        leaveEntitlementRepository.save(professionalCompensationLeave);

        // Create Admins
        Admin admin1 = new Admin("ingyinmay", "password");
        Admin admin2 = new Admin("zeyulin", "password");
        adminRepository.save(admin1);
        adminRepository.save(admin2);

        // Create Managers
        Employee manager1 = new Employee("ziyang", "password", "Zi", "Yang", "zi.yang@example.com", professionalType, LocalDate.now(), Employee.EmpStatus.ACTIVE, null, true);
        Employee manager2 = new Employee("thenur", "password", "Thenur", "Ray", "thenur.ray@example.com", professionalType, LocalDate.now(), Employee.EmpStatus.ACTIVE, manager1, true);
        Employee manager3 = new Employee("alextan", "password", "Alex", "Tan", "alex.tan@example.com", professionalType, LocalDate.now(), Employee.EmpStatus.ACTIVE, manager2, true);
        Employee manager4 = new Employee("johndoe", "password", "John", "Doe", "john.doe@example.com", professionalType, LocalDate.now(), Employee.EmpStatus.ACTIVE, manager3, true);
        Employee manager5 = new Employee("janesmith", "password", "Jane", "Smith", "jane.smith@example.com", professionalType, LocalDate.now(), Employee.EmpStatus.ACTIVE, manager2, true);
        Employee manager6 = new Employee("elizabeth", "password", "Elizabeth", "Brown", "elizabeth.brown@example.com", professionalType, LocalDate.now(), Employee.EmpStatus.ACTIVE, manager4, true);
        Employee manager7 = new Employee("michael", "password", "Michael", "Johnson", "michael.johnson@example.com", professionalType, LocalDate.now(), Employee.EmpStatus.ACTIVE, manager5, true);

        employeeRepository.save(manager1);
        employeeRepository.save(manager2);
        employeeRepository.save(manager3);
        employeeRepository.save(manager4);
        employeeRepository.save(manager5);
        employeeRepository.save(manager6);
        employeeRepository.save(manager7);

        // Create Employees
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("thetsoe", "password", "Thet", "Soe", "thet.soe@example.com", professionalType, LocalDate.now(), Employee.EmpStatus.ACTIVE, manager1, false));
        employees.add(new Employee("sophiechan", "password", "Sophie", "Chan", "sophie.chan@example.com", professionalType, LocalDate.now(), Employee.EmpStatus.ACTIVE, manager2, false));
        employees.add(new Employee("yongsheng", "password", "Yong", "Sheng", "yong.sheng@example.com", professionalType, LocalDate.now(), Employee.EmpStatus.ACTIVE, manager1, false));
        employees.add(new Employee("superman", "password", "Super", "Man", "super.man@example.com", professionalType, LocalDate.now(), Employee.EmpStatus.ACTIVE, manager2, false));
        employees.add(new Employee("ultraman", "password", "Ultra", "Man", "ultra.man@example.com", professionalType, LocalDate.now(), Employee.EmpStatus.ACTIVE, manager3, false));
        employees.add(new Employee("spiderman", "password", "Spider", "Man", "spider.man@example.com", professionalType, LocalDate.now(), Employee.EmpStatus.ACTIVE, manager3, false));
        employees.add(new Employee("wonderwoman", "password", "Wonder", "Woman", "wonder.woman@example.com", professionalType, LocalDate.now(), Employee.EmpStatus.ACTIVE, manager2, false));
        employees.add(new Employee("powerranger", "password", "Power", "Ranger", "power.ranger@example.com", professionalType, LocalDate.now(), Employee.EmpStatus.ACTIVE, manager2, false));
        employees.add(new Employee("thomasedison", "password", "Thomas", "Edison", "thomas.edison@example.com", professionalType, LocalDate.now(), Employee.EmpStatus.ACTIVE, manager5, false));
        employees.add(new Employee("david", "password", "David", "Backham", "david.backham@example.com", professionalType, LocalDate.now(), Employee.EmpStatus.ACTIVE, manager2, false));
        employees.add(new Employee("lionalmessi", "password", "Lional", "Messi", "lional.messi@example.com", professionalType, LocalDate.now(), Employee.EmpStatus.ACTIVE, manager2, false));
        employees.add(new Employee("davidvilla", "password", "David", "Villa", "david.villa@example.com", professionalType, LocalDate.now(), Employee.EmpStatus.ACTIVE, manager2, false));
        employees.add(new Employee("harrypotter", "password", "Harry", "Potter", "harry.potter@example.com", professionalType, LocalDate.now(), Employee.EmpStatus.ACTIVE, manager1, false));
        employees.add(new Employee("ronweasley", "password", "Ron", "Weasley", "ron.weasley@example.com", professionalType, LocalDate.now(), Employee.EmpStatus.ACTIVE, manager2, false));
        employees.add(new Employee("hermionegranger", "password", "Hermione", "Granger", "hermione.granger@example.com", professionalType, LocalDate.now(), Employee.EmpStatus.ACTIVE, manager3, false));
        employees.add(new Employee("dracomalfoy", "password", "Draco", "Malfoy", "draco.malfoy@example.com", professionalType, LocalDate.now(), Employee.EmpStatus.ACTIVE, manager4, false));
        employees.add(new Employee("nevillelongbottom", "password", "Neville", "Longbottom", "neville.longbottom@example.com", professionalType, LocalDate.now(), Employee.EmpStatus.ACTIVE, manager2, false));
        employees.add(new Employee("lunalovegood", "password", "Luna", "Lovegood", "luna.lovegood@example.com", professionalType, LocalDate.now(), Employee.EmpStatus.ACTIVE, manager1, false));
        employees.add(new Employee("ginnyweasley", "password", "Ginny", "Weasley", "ginny.weasley@example.com", professionalType, LocalDate.now(), Employee.EmpStatus.ACTIVE, manager2, false));
        employees.add(new Employee("fredweasley", "password", "Fred", "Weasley", "fred.weasley@example.com", professionalType, LocalDate.now(), Employee.EmpStatus.ACTIVE, manager3, false));
        employees.add(new Employee("georgeweasley", "password", "George", "Weasley", "george.weasley@example.com", professionalType, LocalDate.now(), Employee.EmpStatus.ACTIVE, manager2, false));
        employees.add(new Employee("cedricdiggory", "password", "Cedric", "Diggory", "cedric.diggory@example.com", professionalType, LocalDate.now(), Employee.EmpStatus.ACTIVE, manager2, false));
        employees.add(new Employee("hannahabbott", "password", "Hannah", "Abbott", "hannah.abbott@example.com", professionalType, LocalDate.now(), Employee.EmpStatus.ACTIVE, manager1, false));
        employees.add(new Employee("justinfinch", "password", "Justin", "Finch", "justin.finch@example.com", professionalType, LocalDate.now(), Employee.EmpStatus.ACTIVE, manager2, false));
        employees.add(new Employee("lavenderbrown", "password", "Lavender", "Brown", "lavender.brown@example.com", professionalType, LocalDate.now(), Employee.EmpStatus.ACTIVE, manager2, false));
        employees.add(new Employee("parvatipatil", "password", "Parvati", "Patil", "parvati.patil@example.com", professionalType, LocalDate.now(), Employee.EmpStatus.ACTIVE, manager2, false));
        employees.add(new Employee("padmapatil", "password", "Padma", "Patil", "padma.patil@example.com", professionalType, LocalDate.now(), Employee.EmpStatus.ACTIVE, manager1, false));
        employees.add(new Employee("chochang", "password", "Cho", "Chang", "cho.chang@example.com", professionalType, LocalDate.now(), Employee.EmpStatus.ACTIVE, manager2, false));
        employees.add(new Employee("deanthomas", "password", "Dean", "Thomas", "dean.thomas@example.com", professionalType, LocalDate.now(), Employee.EmpStatus.ACTIVE, manager1, false));
        employees.add(new Employee("seamusfinnigan", "password", "Seamus", "Finnigan", "seamus.finnigan@example.com", professionalType, LocalDate.now(), Employee.EmpStatus.ACTIVE, manager1, false));

        employeeRepository.saveAll(employees);

        // Create Compensation Claim Records
        List<CompensationClaimRecord> compensationClaimRecords = new ArrayList<>();
        compensationClaimRecords.add(new CompensationClaimRecord(employees.get(0), manager1, 10, CompensationClaimRecord.ClaimStatus.Pending, "Overtime work"));
        compensationClaimRecords.add(new CompensationClaimRecord(employees.get(1), manager2, 15, CompensationClaimRecord.ClaimStatus.Approved, "Project completion bonus"));
        compensationClaimRecords.add(new CompensationClaimRecord(employees.get(2), manager1, 20, CompensationClaimRecord.ClaimStatus.Rejected, "Travel expenses"));
        compensationClaimRecords.add(new CompensationClaimRecord(employees.get(3), manager2, 25, CompensationClaimRecord.ClaimStatus.Pending, "Overtime work"));
        compensationClaimRecords.add(new CompensationClaimRecord(employees.get(4), manager3, 30, CompensationClaimRecord.ClaimStatus.Approved, "Project completion bonus"));
        compensationClaimRecords.add(new CompensationClaimRecord(employees.get(5), manager3, 35, CompensationClaimRecord.ClaimStatus.Rejected, "Travel expenses"));
        compensationClaimRecords.add(new CompensationClaimRecord(employees.get(6), manager2, 40, CompensationClaimRecord.ClaimStatus.Pending, "Overtime work"));
        compensationClaimRecords.add(new CompensationClaimRecord(employees.get(7), manager2, 45, CompensationClaimRecord.ClaimStatus.Approved, "Project completion bonus"));
        compensationClaimRecords.add(new CompensationClaimRecord(employees.get(8), manager5, 50, CompensationClaimRecord.ClaimStatus.Rejected, "Travel expenses"));
        compensationClaimRecords.add(new CompensationClaimRecord(employees.get(9), manager2, 55, CompensationClaimRecord.ClaimStatus.Rejected, "Travel expenses"));
        compensationClaimRecords.add(new CompensationClaimRecord(employees.get(10), manager2, 60, CompensationClaimRecord.ClaimStatus.Pending, "Overtime work"));
        compensationClaimRecords.add(new CompensationClaimRecord(employees.get(11), manager2, 65, CompensationClaimRecord.ClaimStatus.Approved, "Project completion bonus"));
        compensationClaimRecords.add(new CompensationClaimRecord(employees.get(12), manager1, 70, CompensationClaimRecord.ClaimStatus.Pending, "Overtime work"));
        compensationClaimRecords.add(new CompensationClaimRecord(employees.get(13), manager2, 75, CompensationClaimRecord.ClaimStatus.Approved, "Project completion bonus"));
        compensationClaimRecords.add(new CompensationClaimRecord(employees.get(14), manager1, 80, CompensationClaimRecord.ClaimStatus.Rejected, "Travel expenses"));
        compensationClaimRecords.add(new CompensationClaimRecord(employees.get(15), manager2, 85, CompensationClaimRecord.ClaimStatus.Pending, "Overtime work"));
        compensationClaimRecords.add(new CompensationClaimRecord(employees.get(16), manager3, 90, CompensationClaimRecord.ClaimStatus.Approved, "Project completion bonus"));
        compensationClaimRecords.add(new CompensationClaimRecord(employees.get(17), manager3, 95, CompensationClaimRecord.ClaimStatus.Rejected, "Travel expenses"));
        compensationClaimRecords.add(new CompensationClaimRecord(employees.get(18), manager2, 100, CompensationClaimRecord.ClaimStatus.Pending, "Overtime work"));
        compensationClaimRecords.add(new CompensationClaimRecord(employees.get(19), manager2, 105, CompensationClaimRecord.ClaimStatus.Approved, "Project completion bonus"));
        compensationClaimRecords.add(new CompensationClaimRecord(employees.get(20), manager5, 110, CompensationClaimRecord.ClaimStatus.Rejected, "Travel expenses"));
        compensationClaimRecords.add(new CompensationClaimRecord(employees.get(21), manager2, 115, CompensationClaimRecord.ClaimStatus.Rejected, "Travel expenses"));
        compensationClaimRecords.add(new CompensationClaimRecord(employees.get(22), manager2, 120, CompensationClaimRecord.ClaimStatus.Pending, "Overtime work"));
        compensationClaimRecords.add(new CompensationClaimRecord(employees.get(23), manager2, 125, CompensationClaimRecord.ClaimStatus.Approved, "Project completion bonus"));
        compensationClaimRecordRepository.saveAll(compensationClaimRecords);

        // Create Employee Leave Records with compensation leave correlation
        List<EmployeeLeaveRecord> employeeLeaveRecords = new ArrayList<>();
        employeeLeaveRecords.add(new EmployeeLeaveRecord(LocalDate.now(), LocalDate.now().plusDays(30), false, employees.get(0), medicalLeave, EmployeeLeaveRecord.Status.Approved, manager1, null, "Family vacation", false, "Contact me via email"));
        employeeLeaveRecords.add(new EmployeeLeaveRecord(LocalDate.now().minusDays(15), LocalDate.now().minusDays(5), true, employees.get(1), compensationLeave, EmployeeLeaveRecord.Status.Applied, manager2, null, "Compensation leave", false, "Contact me via phone"));
        employeeLeaveRecords.add(new EmployeeLeaveRecord(LocalDate.now().minusDays(4), LocalDate.now().minusDays(2), true, employees.get(2), medicalLeave, EmployeeLeaveRecord.Status.Applied, manager1, null, "Medical checkup", false, "Contact me via phone"));
        employeeLeaveRecords.add(new EmployeeLeaveRecord(LocalDate.now().minusDays(20), LocalDate.now().minusDays(15), false, employees.get(3), annualLeave, EmployeeLeaveRecord.Status.Applied, manager2, null, "Family vacation", false, "Contact me via email"));
        employeeLeaveRecords.add(new EmployeeLeaveRecord(LocalDate.now().minusDays(25), LocalDate.now().minusDays(20), false, employees.get(4), compensationLeave, EmployeeLeaveRecord.Status.Applied, manager3, null, "Compensation leave", false, "Contact me via phone"));
        employeeLeaveRecords.add(new EmployeeLeaveRecord(LocalDate.now().minusDays(9), LocalDate.now().minusDays(7), true, employees.get(5), medicalLeave, EmployeeLeaveRecord.Status.Applied, manager3, null, "Medical checkup", false, "Contact me via phone"));
        employeeLeaveRecords.add(new EmployeeLeaveRecord(LocalDate.now().minusDays(11), LocalDate.now().minusDays(6), false, employees.get(6), annualLeave, EmployeeLeaveRecord.Status.Approved, manager2, null, "Family vacation", false, "Contact me via email"));
        employeeLeaveRecords.add(new EmployeeLeaveRecord(LocalDate.now().minusDays(21), LocalDate.now().minusDays(16), false, employees.get(7), compensationLeave, EmployeeLeaveRecord.Status.Approved, manager2, null, "Compensation leave", false, "Contact me via phone"));
        employeeLeaveRecords.add(new EmployeeLeaveRecord(LocalDate.now().minusDays(5), LocalDate.now().minusDays(3), true, employees.get(8), medicalLeave, EmployeeLeaveRecord.Status.Applied, manager5, null, "Medical checkup", false, "Contact me via phone"));
        employeeLeaveRecords.add(new EmployeeLeaveRecord(LocalDate.now().minusDays(12), LocalDate.now().minusDays(7), false, employees.get(9), annualLeave, EmployeeLeaveRecord.Status.Approved, manager2, null, "Family vacation", false, "Contact me via email"));
        employeeLeaveRecords.add(new EmployeeLeaveRecord(LocalDate.now().minusDays(18), LocalDate.now().minusDays(13), false, employees.get(10), compensationLeave, EmployeeLeaveRecord.Status.Applied, manager2, null, "Compensation leave", false, "Contact me via phone"));
        employeeLeaveRecords.add(new EmployeeLeaveRecord(LocalDate.now().minusDays(10), LocalDate.now().minusDays(4), true, employees.get(11), medicalLeave, EmployeeLeaveRecord.Status.Approved, manager2, null, "Medical checkup", false, "Contact me via phone"));
        employeeLeaveRecords.add(new EmployeeLeaveRecord(LocalDate.now().minusDays(30), LocalDate.now().minusDays(25), false, employees.get(12), annualLeave, EmployeeLeaveRecord.Status.Approved, manager1, null, "Family vacation", false, "Contact me via email"));
        employeeLeaveRecords.add(new EmployeeLeaveRecord(LocalDate.now().minusDays(35), LocalDate.now().minusDays(30), false, employees.get(13), compensationLeave, EmployeeLeaveRecord.Status.Applied, manager2, null, "Compensation leave", false, "Contact me via phone"));
        employeeLeaveRecords.add(new EmployeeLeaveRecord(LocalDate.now().minusDays(8), LocalDate.now().minusDays(6), true, employees.get(14), medicalLeave, EmployeeLeaveRecord.Status.Applied, manager1, null, "Medical checkup", false, "Contact me via phone"));
        employeeLeaveRecords.add(new EmployeeLeaveRecord(LocalDate.now().minusDays(40), LocalDate.now().minusDays(35), false, employees.get(15), annualLeave, EmployeeLeaveRecord.Status.Applied, manager2, null, "Family vacation", false, "Contact me via email"));
        employeeLeaveRecords.add(new EmployeeLeaveRecord(LocalDate.now().minusDays(45), LocalDate.now().minusDays(40), false, employees.get(16), compensationLeave, EmployeeLeaveRecord.Status.Applied, manager3, null, "Compensation leave", false, "Contact me via phone"));
        employeeLeaveRecords.add(new EmployeeLeaveRecord(LocalDate.now().minusDays(7), LocalDate.now().minusDays(5), true, employees.get(17), medicalLeave, EmployeeLeaveRecord.Status.Applied, manager3, null, "Medical checkup", false, "Contact me via phone"));
        employeeLeaveRecords.add(new EmployeeLeaveRecord(LocalDate.now().minusDays(11), LocalDate.now().minusDays(6), false, employees.get(18), annualLeave, EmployeeLeaveRecord.Status.Approved, manager2, null, "Family vacation", false, "Contact me via email"));
        employeeLeaveRecords.add(new EmployeeLeaveRecord(LocalDate.now().minusDays(21), LocalDate.now().minusDays(16), false, employees.get(19), compensationLeave, EmployeeLeaveRecord.Status.Approved, manager2, null, "Compensation leave", false, "Contact me via phone"));
        employeeLeaveRecords.add(new EmployeeLeaveRecord(LocalDate.now().minusDays(5), LocalDate.now().minusDays(3), true, employees.get(20), medicalLeave, EmployeeLeaveRecord.Status.Applied, manager5, null, "Medical checkup", false, "Contact me via phone"));
        employeeLeaveRecords.add(new EmployeeLeaveRecord(LocalDate.now().minusDays(12), LocalDate.now().minusDays(7), false, employees.get(21), annualLeave, EmployeeLeaveRecord.Status.Approved, manager2, null, "Family vacation", false, "Contact me via email"));
        employeeLeaveRecords.add(new EmployeeLeaveRecord(LocalDate.now().minusDays(18), LocalDate.now().minusDays(13), false, employees.get(22), compensationLeave, EmployeeLeaveRecord.Status.Applied, manager2, null, "Compensation leave", false, "Contact me via phone"));
        employeeLeaveRecords.add(new EmployeeLeaveRecord(LocalDate.now().minusDays(6), LocalDate.now().minusDays(4), true, employees.get(23), medicalLeave, EmployeeLeaveRecord.Status.Applied, manager2, null, "Medical checkup", false, "Contact me via phone"));
        employeeLeaveRecordRepository.saveAll(employeeLeaveRecords);
    }


}*/

