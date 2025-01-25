package team6.java.ca.entities;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
@DiscriminatorValue("employee")
public class Employee extends User {

	public enum EmpStatus {
		ACTIVE, INACTIVE
	}

	@Column(name = "userid") // Ensure this matches the column name in the 'Users' table
	private long userId;

	@Column(name = "FirstName", length = 50)
	private String firstName;

	@Column(name = "LastName", length = 50)
	private String lastName;

	@Column(name = "email", length = 50)
	private String email;

	@ManyToOne
	@JoinColumn(name = "empType", referencedColumnName = "empTypeId")
	private EmployeeType empType;

	@Column(name = "JoinDate")
	private LocalDate joinDate;

	@Enumerated(EnumType.STRING)
	@Column(name = "EmpStatus", length = 25)
	private EmpStatus empStatus;

	@Column(name = "isManager")
	private boolean isManager;

	@ManyToOne
	@JoinColumn(name = "mgr_id", referencedColumnName = "userid")
	private Employee manager;

	@OneToMany(mappedBy = "manager", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, orphanRemoval = false)
	private List<Employee> subordinates;

	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CompensationClaimRecord> compensationClaimRecords;

	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<EmployeeLeaveRecord> employeeLeaveRecords;

	@OneToMany(mappedBy = "approveManager", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<EmployeeLeaveRecord> managerIdLeaveRecords;

	@OneToMany(mappedBy = "coveringEmployee", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<EmployeeLeaveRecord> coveringIcs;

	@OneToMany(mappedBy = "approveManager", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CompensationClaimRecord> managerIdClaimRecords;

	public Employee() {
		super();
	}

	public Employee(String username, String password, String firstName, String lastName, String email,
			EmployeeType empType, LocalDate joinDate, EmpStatus empStatus, Employee manager, boolean isManager) {
		super(username, password);
		this.firstName = firstName;
		this.lastName = lastName;
		this.empType = empType;
		this.setEmail(email);
		this.joinDate = joinDate;
		this.empStatus = empStatus;
		this.manager = manager;
		this.isManager = isManager;
	}

	// Getters and setters for all fields
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public EmployeeType getEmpType() {
		return empType;
	}

	public void setEmpType(EmployeeType empType) {
		this.empType = empType;
	}

	public LocalDate getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(LocalDate joinDate) {
		this.joinDate = joinDate;
	}

	public EmpStatus getEmpStatus() {
		return empStatus;
	}

	public void setEmpStatus(EmpStatus empStatus) {
		this.empStatus = empStatus;
	}

	public boolean isManager() {
		return isManager;
	}

	public void setManager(boolean isManager) {
		this.isManager = isManager;
	}

	public Employee getManager() {
		return manager;
	}

	public void setManager(Employee manager) {
		this.manager = manager;
	}

	public List<Employee> getSubordinates() {
		return subordinates;
	}

	public void setSubordinates(List<Employee> subordinates) {
		this.subordinates = subordinates;
	}

	public List<CompensationClaimRecord> getCompensationClaimRecords() {
		return compensationClaimRecords;
	}

	public void setCompensationClaimRecords(List<CompensationClaimRecord> compensationClaimRecords) {
		this.compensationClaimRecords = compensationClaimRecords;
	}

	public List<EmployeeLeaveRecord> getEmployeeLeaveRecords() {
		return employeeLeaveRecords;
	}

	public void setEmployeeLeaveRecords(List<EmployeeLeaveRecord> employeeLeaveRecords) {
		this.employeeLeaveRecords = employeeLeaveRecords;
	}

	public List<EmployeeLeaveRecord> getManagerIdLeaveRecords() {
		return managerIdLeaveRecords;
	}

	public void setManagerIdLeaveRecords(List<EmployeeLeaveRecord> managerIdLeaveRecords) {
		this.managerIdLeaveRecords = managerIdLeaveRecords;
	}

	public List<EmployeeLeaveRecord> getCoveringIcs() {
		return coveringIcs;
	}

	public void setCoveringIcs(List<EmployeeLeaveRecord> coveringIcs) {
		this.coveringIcs = coveringIcs;
	}

	public List<CompensationClaimRecord> getManagerIdClaimRecords() {
		return managerIdClaimRecords;
	}

	public void setManagerIdClaimRecords(List<CompensationClaimRecord> managerIdClaimRecords) {
		this.managerIdClaimRecords = managerIdClaimRecords;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullName() {
		return this.firstName + " " + this.lastName;
	}

	@Override
	public String toString() {
		return "Employee [userId=" + getUserId() + ", username=" + getUsername() + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", email=" + email + ", empType=" + empType.getEmpTypeName()
				+ ", joinDate=" + joinDate + ", empStatus=" + empStatus + ", isManager=" + isManager + "]";
	}

}
