package team6.java.ca;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.PostConstruct;
import team6.java.ca.repositories.*;
import team6.java.ca.entities.*;
import team6.java.ca.entities.Employee.EmpStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class Data2Test {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

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

	private Employee createEmployeeWithHashedPassword(String username, String plainPassword, String firstName,
			String lastName, String email, EmployeeType empType, LocalDate joinDate, EmpStatus empStatus,
			Employee manager, boolean isManager) {
		String hashedPassword = passwordEncoder.encode(plainPassword);
		return new Employee(username, hashedPassword, firstName, lastName, email, empType, joinDate,
				Employee.EmpStatus.ACTIVE, manager, isManager);
	}

	private Admin createAdminWithHashedPassword(String username, String plainPassword) {
		String hashedPassword = passwordEncoder.encode(plainPassword);
		return new Admin(username, hashedPassword);
	}

	@Test
	@PostConstruct
	@Transactional
	public void initData() {

		 compensationClaimRecordRepository.deleteAll();
		 employeeLeaveRecordRepository.deleteAll();
		 leaveEntitlementRepository.deleteAll(); leaveTypeRepository.deleteAll();
		 employeeRepository.deleteAll(); adminRepository.deleteAll();
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
		Admin admin1 = createAdminWithHashedPassword("admin1", "password");
		Admin admin2 = createAdminWithHashedPassword("admin2", "password");
		adminRepository.save(admin1);
		adminRepository.save(admin2);

		// Create Managers
		Employee manager1 = createEmployeeWithHashedPassword("johntan", "password", "John", "Tan",
				"johntan@example.com", professionalType, LocalDate.now(), Employee.EmpStatus.ACTIVE, null, true);
		Employee manager2 = createEmployeeWithHashedPassword("jameslee", "password", "James", "Lee",
				"jameslee@example.com", professionalType, LocalDate.now(), Employee.EmpStatus.ACTIVE, manager1, true);
		Employee manager3 = createEmployeeWithHashedPassword("alextan", "password", "Alex", "Tan",
				"alextan@example.com", professionalType, LocalDate.now(), Employee.EmpStatus.ACTIVE, manager1, true);
		Employee manager4 = createEmployeeWithHashedPassword("marylim", "password", "Mary", "Lim",
				"marylim@example.com", professionalType, LocalDate.now(), Employee.EmpStatus.ACTIVE, manager1, true);

		employeeRepository.save(manager1);
		employeeRepository.save(manager2);
		employeeRepository.save(manager3);
		employeeRepository.save(manager4);

		// Create Employees - 3 sub teams
		List<Employee> employees = new ArrayList<>();

		// Manager 2 (James Lee) team
		employees.add(createEmployeeWithHashedPassword("thetsoe", "password", "Thet", "Soe", "thetsoe@example.com",	professionalType, LocalDate.now(), Employee.EmpStatus.ACTIVE, manager2, false));
		employees.add(createEmployeeWithHashedPassword("sophiechen", "password", "Sophie", "Chen", "sophiechen@example.com", professionalType, LocalDate.now(), Employee.EmpStatus.ACTIVE, manager2, false));
		employees.add(createEmployeeWithHashedPassword("yongshengsoh", "password", "Yongsheng", "Soh","yongshengsoh@example.com", professionalType, LocalDate.now(), Employee.EmpStatus.ACTIVE, manager2, false));

		// Manager 3 (Alex Tan) team
		employees.add(createEmployeeWithHashedPassword("ziyang", "password", "Ziyang", "Zhao", "ziyang@example.com", professionalType, LocalDate.now(), Employee.EmpStatus.ACTIVE, manager3, false));
		employees.add(createEmployeeWithHashedPassword("raysun", "password", "Ray", "Sun", "raysun@example.com",professionalType, LocalDate.now(), Employee.EmpStatus.ACTIVE, manager3, false));
		employees.add(createEmployeeWithHashedPassword("spiderman", "password", "Spider", "Man", "spiderman@example.com", professionalType, LocalDate.now(), Employee.EmpStatus.ACTIVE, manager3, false));

		// Manager 4 (Mary Lim) team
		employees.add(createEmployeeWithHashedPassword("zeyulin", "password", "Zeyu", "Lin", "zeyulin@example.com",	professionalType, LocalDate.now(), Employee.EmpStatus.ACTIVE, manager4, false));
		employees.add(createEmployeeWithHashedPassword("ingyinmay", "password", "Ingyin", "May", "ingyinmay@example.com", professionalType, LocalDate.now(), Employee.EmpStatus.ACTIVE, manager4, false));
		employees.add(createEmployeeWithHashedPassword("thomasedison", "password", "Thomas", "Edison", "thomasedison@example.com", professionalType, LocalDate.now(), Employee.EmpStatus.ACTIVE, manager4, false));

		employeeRepository.saveAll(employees);

		// Create Compensation Claim Records
		List<CompensationClaimRecord> compensationClaimRecords = new ArrayList<>();

		// Manager 2 claims (James Lee)
		compensationClaimRecords.add(new CompensationClaimRecord(employees.get(1), manager1, 2, CompensationClaimRecord.ClaimStatus.Pending, "James Overtime work"));

		// Manager 3 claims (Alex Tan)
		compensationClaimRecords.add(new CompensationClaimRecord(employees.get(2), manager1, 2,	CompensationClaimRecord.ClaimStatus.Approved, "Alex Overtime work"));

		// Thet's claims
		compensationClaimRecords.add(new CompensationClaimRecord(employees.get(4), manager2, 1,	CompensationClaimRecord.ClaimStatus.Pending, "Thet Overtime work"));
		compensationClaimRecords.add(new CompensationClaimRecord(employees.get(4), manager2, 0.5, CompensationClaimRecord.ClaimStatus.Approved, "Project completion bonus"));
		compensationClaimRecords.add(new CompensationClaimRecord(employees.get(4), manager2, 2,	CompensationClaimRecord.ClaimStatus.Rejected, "Travel expenses"));

		// Sophie's claims
		compensationClaimRecords.add(new CompensationClaimRecord(employees.get(5), manager2, 1.5, CompensationClaimRecord.ClaimStatus.Pending, "Sophie Overtime work"));
		compensationClaimRecords.add(new CompensationClaimRecord(employees.get(5), manager2, 1,	CompensationClaimRecord.ClaimStatus.Approved, "Project completion bonus"));
		compensationClaimRecords.add(new CompensationClaimRecord(employees.get(5), manager2, 3, CompensationClaimRecord.ClaimStatus.Rejected, "Travel expenses"));

		// YS claims
		compensationClaimRecords.add(new CompensationClaimRecord(employees.get(6), manager2, 0.5, CompensationClaimRecord.ClaimStatus.Pending, "YS Overtime work"));
		compensationClaimRecords.add(new CompensationClaimRecord(employees.get(6), manager2, 2,	CompensationClaimRecord.ClaimStatus.Approved, "Project completion bonus"));
		compensationClaimRecords.add(new CompensationClaimRecord(employees.get(6), manager2, 2.5, CompensationClaimRecord.ClaimStatus.Rejected, "Travel expenses"));

		// Ziyang claims
		compensationClaimRecords.add(new CompensationClaimRecord(employees.get(7), manager3, 1.5, CompensationClaimRecord.ClaimStatus.Pending, "Ziyang Overtime work"));
		compensationClaimRecords.add(new CompensationClaimRecord(employees.get(7), manager3, 0.5, CompensationClaimRecord.ClaimStatus.Approved, "Project completion bonus"));

		// Zeyu claims
		compensationClaimRecords.add(new CompensationClaimRecord(employees.get(10), manager4, 1.5, CompensationClaimRecord.ClaimStatus.Pending, "Zeyu Overtime work"));
		compensationClaimRecords.add(new CompensationClaimRecord(employees.get(10), manager4, 0.5, CompensationClaimRecord.ClaimStatus.Approved, "Project completion bonus"));

		compensationClaimRecordRepository.saveAll(compensationClaimRecords);

		// Create Employee Leave Records with compensation leave
		List<EmployeeLeaveRecord> employeeLeaveRecords = new ArrayList<>();

		// Manager 2 leaves (James Lee)
		employeeLeaveRecords.add(new EmployeeLeaveRecord(LocalDate.now().minusDays(7), LocalDate.now().plusDays(8), false, employees.get(1), annualLeave, EmployeeLeaveRecord.Status.Approved, manager1, null, "Family vacation", false, "Contact me via email"));

		// Manager 3 (Alex Tan)
		employeeLeaveRecords.add(new EmployeeLeaveRecord(LocalDate.now().minusDays(3), LocalDate.now().minusDays(2), true, employees.get(2), compensationLeave, EmployeeLeaveRecord.Status.Applied, manager1, null, "Compensation leave", false, "Contact me via phone"));

		// Manager 4 (Mary Lim)
		employeeLeaveRecords.add(new EmployeeLeaveRecord(LocalDate.now().minusDays(4), LocalDate.now().minusDays(1), true, employees.get(3), medicalLeave, EmployeeLeaveRecord.Status.Applied, manager1, null, "Sick leave, injury", false, "Contact me via phone"));
		
		// Thet leaves
		employeeLeaveRecords.add(new EmployeeLeaveRecord(LocalDate.now().minusDays(7), LocalDate.now().plusDays(3), false, employees.get(4), annualLeave, EmployeeLeaveRecord.Status.Approved, manager2, null, "Family vacation", false, "Contact me via email"));
		employeeLeaveRecords.add(new EmployeeLeaveRecord(LocalDate.now().minusDays(1), LocalDate.now(), false, employees.get(4), medicalLeave, EmployeeLeaveRecord.Status.Approved, manager2, null, "Sick leave", false, "Contact me via email"));
		
		// Sophie leaves
		employeeLeaveRecords.add(new EmployeeLeaveRecord(LocalDate.now().minusDays(2), LocalDate.now().plusDays(3), false, employees.get(5), annualLeave, EmployeeLeaveRecord.Status.Approved, manager2, null, "Annual leave", false, "Contact me via email"));
		employeeLeaveRecords.add(new EmployeeLeaveRecord(LocalDate.now().minusDays(2), LocalDate.now().minusDays(1), false, employees.get(5), medicalLeave, EmployeeLeaveRecord.Status.Approved, manager2, null, "Sick leave", false, "Contact me via email"));

		// YS leaves
		employeeLeaveRecords.add(new EmployeeLeaveRecord(LocalDate.now().minusDays(10), LocalDate.now().minusDays(5), false, employees.get(6), annualLeave, EmployeeLeaveRecord.Status.Approved, manager2, null, "Annual leave", false, "Contact me via email"));
		employeeLeaveRecords.add(new EmployeeLeaveRecord(LocalDate.now().minusDays(2), LocalDate.now().minusDays(1), false, employees.get(6), compensationLeave, EmployeeLeaveRecord.Status.Approved, manager2, null, "Compensation leave", false, "Contact me via email"));

		employeeLeaveRecordRepository.saveAll(employeeLeaveRecords);
	}

}
