package team6.java.ca.services;

import java.util.List;

import team6.java.ca.entities.LeaveType;

public interface LeaveTypeService {
	List<LeaveType> findAllLeaveTypes();

	LeaveType findLeaveTypeById(long leaveTypeId);

	LeaveType findLeaveTypeByName(String leaveTypeName);

	String findLeaveTypeNameById(long leaveTypeId);

	LeaveType createLeaveType(LeaveType leaveType);

	LeaveType updateLeaveType(long leaveTypeId, LeaveType leaveType);

	void deleteLeaveType(long leaveTypeId);

	List<LeaveType> findAll();

	boolean existsByTypename(String leaveTypeName);
}