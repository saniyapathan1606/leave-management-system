package team6.java.ca.serviceImpls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import team6.java.ca.entities.LeaveEntitlement;
import team6.java.ca.repositories.LeaveEntitlementRepository;
import team6.java.ca.services.LeaveEntitlementService;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class LeaveEntitlementServiceImpl implements LeaveEntitlementService {

    @Autowired
    private LeaveEntitlementRepository leaveEntitlementRepo;

    @Override
    public List<LeaveEntitlement> findAllLeaveEntitlements() {
        return leaveEntitlementRepo.findAll();
    }

    @Override
    public LeaveEntitlement findLeaveEntitlementById(long leaveEntId) {
        return leaveEntitlementRepo.findById(leaveEntId).orElse(null);
    }

    @Override
    public List<LeaveEntitlement> findLeaveEntitlementsByEmpTypeId(long empTypeId) {
        return leaveEntitlementRepo.findByEmpTypeId(empTypeId);
    }

    @Override
    public List<LeaveEntitlement> findLeaveEntitlementsByLeaveTypeId(long leaveTypeId) {
        return leaveEntitlementRepo.findByLeaveTypeId(leaveTypeId);
    }

    @Override
    public LeaveEntitlement findLeaveEntitlementByEmpTypeIdAndLeaveTypeId(long empTypeId, long leaveTypeId) {
        return leaveEntitlementRepo.findByEmpTypeIdAndLeaveTypeId(empTypeId, leaveTypeId);
    }

    @Transactional(readOnly = false)
    @Override
    public LeaveEntitlement createLeaveEntitlement(LeaveEntitlement leaveEntitlement) {
        return leaveEntitlementRepo.save(leaveEntitlement);
    }

    @Transactional(readOnly = false)
    @Override
    public LeaveEntitlement updateLeaveEntitlement(long leaveEntId, LeaveEntitlement leaveEntitlement) {
        LeaveEntitlement existingLeaveEntitlement = leaveEntitlementRepo.findById(leaveEntId).orElse(null);
        if (existingLeaveEntitlement != null) {
        	
            if (leaveEntitlement.getLeaveEntId() != 0) {
                existingLeaveEntitlement.setLeaveEntId(leaveEntitlement.getLeaveEntId());
            }
            if (leaveEntitlement.getEmpType() != null) {
                existingLeaveEntitlement.setEmpType(leaveEntitlement.getEmpType());
            }
            if (leaveEntitlement.getLeaveType() != null) {
                existingLeaveEntitlement.setLeaveType(leaveEntitlement.getLeaveType());
            }
            if (leaveEntitlement.getEntitlementQty() != 0) {
                existingLeaveEntitlement.setEntitlementQty(leaveEntitlement.getEntitlementQty());
            }
            
            return leaveEntitlementRepo.save(existingLeaveEntitlement);
        }
        return null;
    }

    @Transactional(readOnly = false)
    @Override
    public void deleteLeaveEntitlement(long leaveEntId) {
        leaveEntitlementRepo.deleteById(leaveEntId);
    }
    
    @Override
    public void save(LeaveEntitlement leaveEntitlement)
    {
    	leaveEntitlementRepo.save(leaveEntitlement);
    }
}