package team6.java.ca.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import team6.java.ca.entities.LeaveEntitlement;



public interface LeaveEntitlementRepository extends JpaRepository<LeaveEntitlement, Long>{
	
	@Query("SELECT le FROM LeaveEntitlement le WHERE le.empType.empTypeId = :empTypeId")
    List<LeaveEntitlement> findByEmpTypeId(@Param("empTypeId") long empTypeId);

    @Query("SELECT le FROM LeaveEntitlement le WHERE le.leaveType.leaveTypeId = :leaveTypeId")
    List<LeaveEntitlement> findByLeaveTypeId(@Param("leaveTypeId") long leaveTypeId);

    @Query("SELECT le FROM LeaveEntitlement le WHERE le.empType.empTypeId = :empTypeId AND le.leaveType.leaveTypeId = :leaveTypeId")
    LeaveEntitlement findByEmpTypeIdAndLeaveTypeId(@Param("empTypeId") long empTypeId, @Param("leaveTypeId") long leaveTypeId);
    
    

}