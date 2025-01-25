package team6.java.ca.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import team6.java.ca.entities.EmployeeLeaveRecord;
import team6.java.ca.entities.EmployeeLeaveRecord.Status;

public interface EmployeeLeaveRecordRepository extends JpaRepository<EmployeeLeaveRecord, Long> {

	@Query("SELECT e FROM EmployeeLeaveRecord e WHERE e.employee.userId = :userId")
	List<EmployeeLeaveRecord> findAllLeaveRecordsOfEmployee(@Param("userId") Long userId);

	@Query("SELECT e FROM EmployeeLeaveRecord e WHERE e.leaveType.leaveTypeId = :leaveTypeId")
	List<EmployeeLeaveRecord> findAllLeaveRecordsOfLeaveType(@Param("leaveTypeId") Long leaveTypeId);

	@Query("SELECT e FROM EmployeeLeaveRecord e WHERE e.approveManager.userId = :managerId")
	List<EmployeeLeaveRecord> findAllLeaveRecordsOfMgr(@Param("managerId") Long managerId);

	@Query("SELECT e FROM EmployeeLeaveRecord e WHERE e.status = :leavestatus")
	List<EmployeeLeaveRecord> findAllLeaveRecordsByStatus(@Param("leavestatus") Status status);

	@Query("SELECT e FROM EmployeeLeaveRecord e WHERE e.leaveId = :leaveId")
	EmployeeLeaveRecord findEmployeeLeaveRecordById(@Param("leaveId") Long leaveId);

	@Query("SELECT e FROM EmployeeLeaveRecord e WHERE e.leaveDate BETWEEN :startDate AND :endDate")
	List<EmployeeLeaveRecord> findAllByLeaveDateBetween(@Param("startDate") LocalDate startDate,
			@Param("endDate") LocalDate endDate);

	@Query("SELECT e FROM EmployeeLeaveRecord e WHERE e.coveringEmployee.userId = :coveringEmployeeId")
	List<EmployeeLeaveRecord> findAllByCoveringEmployee(@Param("coveringEmployeeId") Long coveringEmployeeId);

	@Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM EmployeeLeaveRecord e WHERE e.leaveId = :leaveId")
	boolean existsByLeaveId(@Param("leaveId") Long leaveId);

	@Query("SELECT elr FROM EmployeeLeaveRecord elr WHERE elr.approveManager.userId = :mgrId AND elr.status = 'Applied'")
	List<EmployeeLeaveRecord> findPendingLeaveRecordByManagerEmpId(@Param("mgrId") long mgrId);

	@Query("SELECT elr FROM EmployeeLeaveRecord elr WHERE elr.approveManager.userId = :mgrId AND elr.status = 'Approved'")
	List<EmployeeLeaveRecord> findApprovedLeaveRecordByManagerEmpId(@Param("mgrId") long mgrId);

	@Query("SELECT e FROM EmployeeLeaveRecord e WHERE e.leaveType.leaveTypeId = :leaveTypeId AND e.approveManager.userId = :mgrId")
	List<EmployeeLeaveRecord> findAllLeaveRecordsOfLeaveTypeUnderManager(@Param("leaveTypeId") Long leaveTypeId,
			@Param("mgrId") Long managerId);

	@Query("SELECT e FROM EmployeeLeaveRecord e WHERE e.leaveId = :id")
	EmployeeLeaveRecord findByLeaveId(@Param("id") Long id);

	@Query("SELECT e FROM EmployeeLeaveRecord e WHERE e.leaveDate BETWEEN :startDate AND :endDate AND e.approveManager.userId = :mgrId")
	List<EmployeeLeaveRecord> findAllByLeaveDateBetweenAndApproveMgrId(@Param("startDate") LocalDate startDate,
			@Param("endDate") LocalDate endDate, @Param("mgrId") Long managerId);

	@Query("SELECT elr from EmployeeLeaveRecord elr Where elr.employee.userId = :userId Order By elr.leaveDate DESC")
	public List<EmployeeLeaveRecord> findAllByUserIdOrderByDate(@Param("userId") long userId);

	@Query("SELECT elr from EmployeeLeaveRecord elr where elr.employee.manager.userId = :mgrId AND elr.status= :status Order By elr.leaveDate DESC")
	public List<EmployeeLeaveRecord> findByMgrIdnStatus(@Param("mgrId") long mgrId,
			@Param("status") EmployeeLeaveRecord.Status status);

}
