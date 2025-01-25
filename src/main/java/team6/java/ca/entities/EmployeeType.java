package team6.java.ca.entities;

import java.util.List;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class EmployeeType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="empTypeId")
	private long empTypeId;
	
	@Column(name = "emp_type_name")
	private String empTypeName;
	
	@OneToMany(mappedBy = "empType") // inverse side to LeaveEntitlement's empType
	private List<LeaveEntitlement> leaveEntitlements;
	
	@OneToMany(mappedBy = "empType") // inverse side to Employee's empType
	private List<Employee> employees;
	
	public EmployeeType() {}
	
	public EmployeeType(String empTypeName) {
		this.empTypeName = empTypeName;
	}

	public long getEmpTypeId() {
		return empTypeId;
	}

	public void setEmpTypeId(long empTypeId) {
		this.empTypeId = empTypeId;
	}

	public String getEmpTypeName() {
		return empTypeName;
	}

	public void setEmpTypeName(String empTypeName) {
		this.empTypeName = empTypeName;
	}

	public List<LeaveEntitlement> getLeaveEntitlements() {
		return leaveEntitlements;
	}

	public void setLeaveEntitlements(List<LeaveEntitlement> leaveEntitlements) {
		this.leaveEntitlements = leaveEntitlements;
	}
	
	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	@Override
    public String toString() {
        return "EmployeeType [empTypeId=" + empTypeId + ", empTypeName=" + empTypeName + ", leaveEntitlementCount="
                + (leaveEntitlements != null ? leaveEntitlements.size() : 0) + ", employeeCount="
                + (employees != null ? employees.size() : 0) + "]";
    }
	

}
