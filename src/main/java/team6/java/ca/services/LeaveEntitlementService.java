package team6.java.ca.services;

import java.util.List;

import team6.java.ca.entities.LeaveEntitlement;

public interface LeaveEntitlementService {
    List<LeaveEntitlement> findAllLeaveEntitlements();
    LeaveEntitlement findLeaveEntitlementById(long leaveEntId);
    List<LeaveEntitlement> findLeaveEntitlementsByEmpTypeId(long empTypeId);
    List<LeaveEntitlement> findLeaveEntitlementsByLeaveTypeId(long leaveTypeId);
    LeaveEntitlement findLeaveEntitlementByEmpTypeIdAndLeaveTypeId(long empTypeId, long leaveTypeId);
    LeaveEntitlement createLeaveEntitlement(LeaveEntitlement leaveEntitlement);
    LeaveEntitlement updateLeaveEntitlement(long leaveEntId, LeaveEntitlement leaveEntitlement);
    void deleteLeaveEntitlement(long leaveEntId);
    void save(LeaveEntitlement leaveEntitlement);
}