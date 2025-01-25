package team6.java.ca.entities;

import java.util.List;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class LeaveType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long leaveTypeId;

	@Column(name = "leave_type_name")
	private String leaveTypeName;
	
	@OneToMany(mappedBy = "leaveType") // inverse side to LeaveEntitlement's leaveType
	private List<LeaveEntitlement> leaveEntitlements;
	
	@OneToMany(mappedBy = "leaveType") // inverse side to EmployeeLeaveRecord's leaveType
	private List<EmployeeLeaveRecord> empLeaveRecords;

	public LeaveType() {
	}

	public LeaveType(String leaveTypeName) {
		this.leaveTypeName = leaveTypeName;
	}
	
	// getters, setters, toString
	public long getLeaveTypeId() {
		return leaveTypeId;
	}

	public void setLeaveTypeId(long leaveTypeId) {
		this.leaveTypeId = leaveTypeId;
	}

	public String getLeaveTypeName() {
		return leaveTypeName;
	}

	public void setLeaveTypeName(String leaveTypeName) {
		this.leaveTypeName = leaveTypeName;
	}

	public List<LeaveEntitlement> getLeaveEntitlements() {
		return leaveEntitlements;
	}

	public void setLeaveEntitlements(List<LeaveEntitlement> leaveEntitlements) {
		this.leaveEntitlements = leaveEntitlements;
	}

	public List<EmployeeLeaveRecord> getEmpLeaveRecords() {
		return empLeaveRecords;
	}

	public void setEmpLeaveRecords(List<EmployeeLeaveRecord> empLeaveRecords) {
		this.empLeaveRecords = empLeaveRecords;
	}

	@Override
    public String toString() {
        return "LeaveType [leaveTypeId=" + leaveTypeId + ", leaveTypeName=" + leaveTypeName + "]";
    }
	
}
