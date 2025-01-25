package team6.java.ca.serviceImpls;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import team6.java.ca.entities.EmployeeLeaveRecord;
import team6.java.ca.repositories.EmployeeLeaveRecordRepository;
import team6.java.ca.services.EmployeeLeaveRecordService;

@Service
@Transactional(readOnly = true)
public class EmployeeLeaveRecordServiceImpl implements EmployeeLeaveRecordService {

	@Autowired
	private EmployeeLeaveRecordRepository employeeLeaveRecordRepository;

	@Override
	public EmployeeLeaveRecord findEmployeeLeaveRecordById(Long leaveId) {
		return employeeLeaveRecordRepository.findEmployeeLeaveRecordById(leaveId);
	}

	@Override
	public List<EmployeeLeaveRecord> findAllLeaveRecordsOfEmployee(Long userId) {
		return employeeLeaveRecordRepository.findAllLeaveRecordsOfEmployee(userId);
	}

	@Override
	public List<EmployeeLeaveRecord> findAllLeaveRecordsOfLeaveType(Long leaveTypeId) {
		return employeeLeaveRecordRepository.findAllLeaveRecordsOfLeaveType(leaveTypeId);
	}

	@Override
	public List<EmployeeLeaveRecord> findAllLeaveRecordsOfMgr(Long managerId) {
		return employeeLeaveRecordRepository.findAllLeaveRecordsOfMgr(managerId);
	}

	@Override
	public List<EmployeeLeaveRecord> findAllLeaveRecordsByStatus(EmployeeLeaveRecord.Status status) {
		return employeeLeaveRecordRepository.findAllLeaveRecordsByStatus(status);
	}

	@Override
	public List<EmployeeLeaveRecord> findAllByLeaveDateBetween(LocalDate startDate, LocalDate endDate) {
		return employeeLeaveRecordRepository.findAllByLeaveDateBetween(startDate, endDate);
	}

	@Override
	public List<EmployeeLeaveRecord> findAllByCoveringEmployee(Long coveringEmployeeId) {
		return employeeLeaveRecordRepository.findAllByCoveringEmployee(coveringEmployeeId);
	}

	@Transactional(readOnly = false)
	@Override
	public EmployeeLeaveRecord createEmployeeLeaveRecord(EmployeeLeaveRecord employeeLeaveRecord) {
		return employeeLeaveRecordRepository.save(employeeLeaveRecord);
	}

	@Override
	public List<EmployeeLeaveRecord> findEmployeeLeaveRecordsByEmpId(Long empId) {
		return employeeLeaveRecordRepository.findAllLeaveRecordsOfEmployee(empId);
	}

	@Transactional(readOnly = false)
	@Override
	public EmployeeLeaveRecord updateEmployeeLeaveRecord(Long leaveId, EmployeeLeaveRecord employeeLeaveRecord) {
		Optional<EmployeeLeaveRecord> existingRecord = employeeLeaveRecordRepository.findById(leaveId);
		if (existingRecord.isPresent()) {
			EmployeeLeaveRecord existing = existingRecord.get();
			existing.setLeaveDate(employeeLeaveRecord.getLeaveDate());
			existing.setEndDate(employeeLeaveRecord.getEndDate());
			existing.setHalfDay(employeeLeaveRecord.isHalfDay());
			existing.setEmployee(employeeLeaveRecord.getEmployee());
			existing.setLeaveType(employeeLeaveRecord.getLeaveType());
			existing.setStatus(employeeLeaveRecord.getStatus());
			existing.setApproveManager(employeeLeaveRecord.getApproveManager());
			existing.setCoveringEmployee(employeeLeaveRecord.getCoveringEmployee());
			existing.setAdditionalReason(employeeLeaveRecord.getAdditionalReason());
			existing.setOverseas(employeeLeaveRecord.isOverseas());
			existing.setContactDetail(employeeLeaveRecord.getContactDetail());
			return employeeLeaveRecordRepository.save(existing);
		}
		return null;
	}

	@Transactional(readOnly = false)
	@Override
	public void deleteEmployeeLeaveRecord(Long leaveId) {
		employeeLeaveRecordRepository.deleteById(leaveId);
	}

	@Override
	public boolean existsByLeaveId(Long leaveId) {
		return employeeLeaveRecordRepository.existsByLeaveId(leaveId);
	}

	@Override
	public List<EmployeeLeaveRecord> findPendingLeaveRecordByManagerEmpId(long mgrId) {
		return employeeLeaveRecordRepository.findPendingLeaveRecordByManagerEmpId(mgrId);
	}

	@Override
	public List<EmployeeLeaveRecord> findApprovedLeaveRecordByManagerEmpId(long mgrId) {
		return employeeLeaveRecordRepository.findApprovedLeaveRecordByManagerEmpId(mgrId);
	}

	@Override
	public List<EmployeeLeaveRecord> findAllLeaveRecordsOfLeaveTypeUnderManager(Long leaveTypeId, Long managerId) {
		return employeeLeaveRecordRepository.findAllLeaveRecordsOfLeaveTypeUnderManager(leaveTypeId, managerId);
	}

	@Override
	public EmployeeLeaveRecord findByLeaveId(Long id) {
		return employeeLeaveRecordRepository.findByLeaveId(id);
	}

	@Override
	public List<EmployeeLeaveRecord> findAllByLeaveDateBetweenAndApproveMgrId(LocalDate startDate, LocalDate endDate,
			Long managerId) {
		return employeeLeaveRecordRepository.findAllByLeaveDateBetweenAndApproveMgrId(startDate, endDate, managerId);
	}

	@Override
	public List<EmployeeLeaveRecord> findAllByUserIdOrderByDate(long userId) {
		return employeeLeaveRecordRepository.findAllByUserIdOrderByDate(userId);
	}

	@Override
	public List<EmployeeLeaveRecord> findByMgrIdnStatus(long mgrId, EmployeeLeaveRecord.Status status) {
		return employeeLeaveRecordRepository.findByMgrIdnStatus(mgrId, status);
	}

}
