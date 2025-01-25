package team6.java.ca.serviceImpls;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import team6.java.ca.entities.LeaveType;
import team6.java.ca.repositories.LeaveTypeRepository;
import team6.java.ca.services.LeaveTypeService;

@Service
@Transactional(readOnly = true)
public class LeaveTypeServiceImpl implements LeaveTypeService {

	@Autowired
	private LeaveTypeRepository leaveTypeRepo;

	@Override
	public List<LeaveType> findAllLeaveTypes() {
		return leaveTypeRepo.findAll();
	}

	@Override
	public LeaveType findLeaveTypeById(long leaveTypeId) {
		return leaveTypeRepo.findById(leaveTypeId).orElse(null);
	}

	@Override
	public LeaveType findLeaveTypeByName(String leaveTypeName) {
		return leaveTypeRepo.findByLeaveTypeName(leaveTypeName);
	}

	@Override
	public String findLeaveTypeNameById(long leaveTypeId) {
		return leaveTypeRepo.findLeaveTypeNameById(leaveTypeId);
	}

	@Override
	public boolean existsByTypename(String leaveTypeName) {
		return leaveTypeRepo.existsByTypename(leaveTypeName);
	}

	@Transactional(readOnly = false)
	@Override
	public LeaveType createLeaveType(LeaveType leaveType) {
		return leaveTypeRepo.save(leaveType);
	}

	@Transactional(readOnly = false)
	@Override
	public LeaveType updateLeaveType(long leaveTypeId, LeaveType leaveType) {
		Optional<LeaveType> existingLeaveType = leaveTypeRepo.findById(leaveTypeId);
		if (existingLeaveType.isPresent()) {
			LeaveType lt = existingLeaveType.get();
			lt.setLeaveTypeName(leaveType.getLeaveTypeName());
			lt.setLeaveEntitlements(leaveType.getLeaveEntitlements());
			lt.setEmpLeaveRecords(leaveType.getEmpLeaveRecords());
			return leaveTypeRepo.save(lt);
		}
		return null;
	}

	@Transactional(readOnly = false)
	@Override
	public void deleteLeaveType(long leaveTypeId) {
		leaveTypeRepo.deleteById(leaveTypeId);
	}

	public List<LeaveType> findAll() {
		return leaveTypeRepo.findAll();
	}

}
