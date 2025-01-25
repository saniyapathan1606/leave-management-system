package team6.java.ca.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;

@Entity
public class LeaveEntitlement {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "leave_ent_id", length = 50)
	private long leaveEntId;

	@ManyToOne // owning side to EmployeeType's empTypeId
	private EmployeeType empType;

	@ManyToOne // owning side to LeaveType's leaveTypeId
	private LeaveType leaveType;

	@Column(name = "ent_qty")
	@DecimalMax(value = "60.0", message = "The quantity cannot exceed 60")
	@DecimalMin(value = "0.0", message = "The quantity cannot below 0")
	private double entitlementQty;

	public LeaveEntitlement() {
	}

	public LeaveEntitlement(EmployeeType empType, LeaveType leaveType, double entitlementQty) {
		this.empType = empType;
		this.leaveType = leaveType;
		this.entitlementQty = entitlementQty;
	}

	public long getLeaveEntId() {
		return leaveEntId;
	}

	public void setLeaveEntId(long leaveEntId) {
		this.leaveEntId = leaveEntId;
	}

	public EmployeeType getEmpType() {
		return empType;
	}

	public void setEmpType(EmployeeType empType) {
		this.empType = empType;
	}

	public LeaveType getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(LeaveType leaveType) {
		this.leaveType = leaveType;
	}

	public double getEntitlementQty() {
		return entitlementQty;
	}

	public void setEntitlementQty(double entitlementQty) {
		this.entitlementQty = entitlementQty;
	}

	@Override
	public String toString() {
		return "LeaveEntitlement [leaveEntId=" + leaveEntId + ", empTypeId="
				+ (empType != null ? empType.getEmpTypeId() : null) + ", leaveTypeId="
				+ (leaveType != null ? leaveType.getLeaveTypeId() : null) + ", entitlementQty=" + entitlementQty + "]";
	}

}
