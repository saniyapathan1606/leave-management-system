package team6.java.ca.serviceImpls;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import team6.java.ca.entities.CompensationClaimRecord;
import team6.java.ca.entities.CompensationClaimRecord.ClaimStatus;
import team6.java.ca.entities.Employee;
import team6.java.ca.repositories.CompensationClaimRecordRepository;
import team6.java.ca.services.CompensationClaimRecordService;

@Service
@Transactional(readOnly = true)
public class CompensationClaimRecordServiceImpl implements CompensationClaimRecordService {
	@Autowired
	private CompensationClaimRecordRepository ccrRepo;

	@Override
	public List<CompensationClaimRecord> findAllClaimRecords() {
		return ccrRepo.findAll();
	}

	@Override
	public List<CompensationClaimRecord> findAllClaimRecordsOfEmployee(Employee emp) {
		return ccrRepo.findAllClaimRecordsOfEmployee(emp);
	}

	@Override
	public List<CompensationClaimRecord> findAllClaimRecordsOfMgr(Employee mgr) {
		return ccrRepo.findAllClaimRecordsOfMgr(mgr);
	}

	@Override
	public List<CompensationClaimRecord> findAllClaimRecordsByStatus(ClaimStatus status) {
		return ccrRepo.findAllClaimRecordsByStatus(status);
	}

	@Override
	public List<CompensationClaimRecord> findCompensationLeaveRecordsByEmpId(Long empId) {
		return ccrRepo.findCompensationLeaveRecordsByEmpId(empId);
	}

	@Transactional(readOnly = false)
	@Override
	public CompensationClaimRecord createClaim(CompensationClaimRecord ccr) {
		return ccrRepo.save(ccr);
	}

	@Transactional(readOnly = false)
	@Override
	public CompensationClaimRecord updateClaim(long claimId, CompensationClaimRecord ccr) {
		CompensationClaimRecord existingClaim = ccrRepo.findClaimByClaimId(claimId);
		if (existingClaim != null) {
			existingClaim.setEmployee(ccr.getEmployee());
			existingClaim.setApproveManager(ccr.getApproveManager());
			existingClaim.setClaimQty(ccr.getClaimQty());
			existingClaim.setStatus(ccr.getStatus());
			existingClaim.setRemarks(ccr.getRemarks());
			existingClaim.setLastUpdateTime(LocalDateTime.now());
			return ccrRepo.save(existingClaim);
		}
		return null;
	}

	@Transactional(readOnly = false)
	@Override
	public void deleteClaim(long claimId) {
		ccrRepo.deleteById(claimId);
	}

	@Override
	public CompensationClaimRecord findClaimByClaimId(long claimId) {
		return ccrRepo.findClaimByClaimId(claimId);
	}

	@Override
	public boolean existsById(long claimId) {
		return ccrRepo.existsById(claimId);
	}

	@Transactional(readOnly = false)
	@Override
	public void updateClaimStatus(long claimId, CompensationClaimRecord.ClaimStatus status) {
		ccrRepo.updateClaimStatus(claimId, status);
	}

	@Override
	public List<CompensationClaimRecord> findAllByUserId(long userId) {
		return ccrRepo.findAllByUserId(userId);
	}

	@Override
	public List<CompensationClaimRecord> findByMgrIdnStatus(long mgrId, CompensationClaimRecord.ClaimStatus status) {
		return ccrRepo.findByMgrIdnStatus(mgrId, status);
	}

}