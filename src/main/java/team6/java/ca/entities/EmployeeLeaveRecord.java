package team6.java.ca.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "EmployeeLeaveRecords")
public class EmployeeLeaveRecord {

//	@Column(name="StartDate", nullable=false)
//	private LocalDate start;

//	@Column(name = "LeaveQty", nullable = false)
//	private double leaveQty;

	public enum Status {
		Approved, Created, Applied, Cancelled, Deleted, Rejected, Archived, Updated
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "LeaveID", nullable = false)
	private Long leaveId;

	@Column(name = "LeaveDate")
	private LocalDate leaveDate;

	@Column(name = "EndDate", nullable = false)
	private LocalDate endDate;

	@Column(name = "HalfDay")
	private boolean halfDay;

	@ManyToOne // owning side to Employee's employeeLeaveRecords
	private Employee employee;

	@ManyToOne // owning side to LeaveType's empLeaveRecord
	private LeaveType leaveType;

	@Enumerated(EnumType.STRING)
	@Column(name = "Status", nullable = false, length = 50)
	private Status status;

	@ManyToOne // owning side to Employee's managerIdLeaveRecords
	@JoinColumn(name = "ApprMgrId", referencedColumnName = "userid")
	private Employee approveManager;

	@ManyToOne // owning side to Employee's coveringIcs
	@JoinColumn(name = "CoveringIC", referencedColumnName = "userid")
	private Employee coveringEmployee;

	@Column(name = "AddReason", columnDefinition = "TEXT")
	private String additionalReason;

	@Column(name = "Ovs")
	private boolean isOverseas;

	@Column(name = "ContactDetail", columnDefinition = "TEXT")
	private String contactDetail;

	public EmployeeLeaveRecord() {
	}

	public EmployeeLeaveRecord(LocalDate leaveDate, LocalDate endDate, boolean halfDay, Employee employee,
			LeaveType leaveType, Status status, Employee approveManager, Employee coveringEmployee,
			String additionalReason, boolean isOverseas, String contactDetail) {

		// this.leaveQty = leaveQty;
		this.setLeaveDate(leaveDate);
		this.setEndDate(endDate);
		this.setHalfDay(halfDay);
		this.setEmployee(employee);
		this.setLeaveType(leaveType);
		this.setStatus(status);
		this.setApproveManager(approveManager);
		this.setCoveringEmployee(coveringEmployee);
		this.setAdditionalReason(additionalReason);
		this.setOverseas(isOverseas);
		this.setContactDetail(contactDetail);
	}

	public Long getLeaveId() {
		return leaveId;
	}

	public void setLeaveId(Long leaveId) {
		this.leaveId = leaveId;
	}

	public LocalDate getLeaveDate() {
		return leaveDate;
	}

	public void setLeaveDate(LocalDate leaveDate) {
		this.leaveDate = leaveDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public LeaveType getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(LeaveType leaveType) {
		this.leaveType = leaveType;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Employee getApproveManager() {
		return approveManager;
	}

	public void setApproveManager(Employee approveManager) {
		this.approveManager = approveManager;
	}

	public Employee getCoveringEmployee() {
		return coveringEmployee;
	}

	public void setCoveringEmployee(Employee coveringEmployee) {
		this.coveringEmployee = coveringEmployee;
	}

	public String getAdditionalReason() {
		return additionalReason;
	}

	public void setAdditionalReason(String additionalReason) {
		this.additionalReason = additionalReason;
	}

	public boolean isOverseas() {
		return isOverseas;
	}

	public void setOverseas(boolean isOverseas) {
		this.isOverseas = isOverseas;
	}

	public String getContactDetail() {
		return contactDetail;
	}

	public void setContactDetail(String contactDetail) {
		this.contactDetail = contactDetail;
	}

	public boolean isHalfDay() {
		return halfDay;
	}

	public void setHalfDay(boolean halfDay) {
		this.halfDay = halfDay;
	}

	@Override
	public String toString() {
		return "EmployeeLeaveRecord [leaveId=" + leaveId + ", leaveDate=" + leaveDate + ", endDate=" + endDate
				+ ", halfDay=" + halfDay + ", employeeId=" + (employee != null ? employee.getUserId() : "None")
				+ ", leaveType=" + (leaveType != null ? leaveType.getLeaveTypeName() : "None") + ", status=" + status
				+ ", approveManagerId=" + (approveManager != null ? approveManager.getUserId() : "None")
				+ ", coveringEmployeeId=" + (coveringEmployee != null ? coveringEmployee.getUserId() : "None")
				+ ", additionalReason=" + additionalReason + ", isOverseas=" + isOverseas + ", contactDetail="
				+ contactDetail + "]";
	}
}
