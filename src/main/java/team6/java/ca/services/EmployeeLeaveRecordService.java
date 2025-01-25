package team6.java.ca.services;

import java.time.LocalDate;
import java.util.List;

import team6.java.ca.entities.EmployeeLeaveRecord;

public interface EmployeeLeaveRecordService {

	EmployeeLeaveRecord findEmployeeLeaveRecordById(Long leaveId);

	List<EmployeeLeaveRecord> findAllLeaveRecordsOfEmployee(Long userId);

	List<EmployeeLeaveRecord> findAllLeaveRecordsOfLeaveType(Long leaveTypeId);

	List<EmployeeLeaveRecord> findAllLeaveRecordsOfMgr(Long managerId);

	List<EmployeeLeaveRecord> findAllLeaveRecordsByStatus(EmployeeLeaveRecord.Status status);

	List<EmployeeLeaveRecord> findAllByLeaveDateBetween(LocalDate startDate, LocalDate endDate);

	List<EmployeeLeaveRecord> findAllByCoveringEmployee(Long coveringEmployeeId);

	EmployeeLeaveRecord createEmployeeLeaveRecord(EmployeeLeaveRecord employeeLeaveRecord);

	EmployeeLeaveRecord updateEmployeeLeaveRecord(Long leaveId, EmployeeLeaveRecord employeeLeaveRecord);

	void deleteEmployeeLeaveRecord(Long leaveId);

	boolean existsByLeaveId(Long leaveId);

	List<EmployeeLeaveRecord> findEmployeeLeaveRecordsByEmpId(Long empId);

	List<EmployeeLeaveRecord> findPendingLeaveRecordByManagerEmpId(long mgrId);

	List<EmployeeLeaveRecord> findApprovedLeaveRecordByManagerEmpId(long mgrId);

	List<EmployeeLeaveRecord> findAllLeaveRecordsOfLeaveTypeUnderManager(Long leaveTypeId, Long managerId);

	EmployeeLeaveRecord findByLeaveId(Long id);

	List<EmployeeLeaveRecord> findAllByLeaveDateBetweenAndApproveMgrId(LocalDate startDate, LocalDate endDate,
			Long managerId);

	List<EmployeeLeaveRecord> findAllByUserIdOrderByDate(long userId);

	List<EmployeeLeaveRecord> findByMgrIdnStatus(long mgrId, EmployeeLeaveRecord.Status status);
}
