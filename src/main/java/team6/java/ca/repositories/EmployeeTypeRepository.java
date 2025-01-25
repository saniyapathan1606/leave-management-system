package team6.java.ca.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import team6.java.ca.entities.Employee;
import team6.java.ca.entities.EmployeeType;
import team6.java.ca.entities.LeaveEntitlement;



public interface EmployeeTypeRepository extends JpaRepository<EmployeeType, Long> {

    // GET EMPTYPE OBJECT FROM emptype id
    @Query("SELECT e FROM EmployeeType e WHERE e.empTypeId = :empTypeId")
    EmployeeType getEmpTypeById(@Param("empTypeId") long empTypeId);
    
    // Get employee type name from employee type id
    @Query("SELECT e.empTypeName FROM EmployeeType e WHERE e.empTypeId = :empTypeId")
    String getEmpTypeNameById(@Param("empTypeId") long empTypeId);
    
    // Get leaveEntitlement belongs to emptype (from empType id)
    @Query("SELECT e.leaveEntitlements FROM EmployeeType e WHERE e.empTypeId = :empTypeId")
    List<LeaveEntitlement> getLeaveEntitlementByEmpTypeId(@Param("empTypeId") long empTypeId);
    
    // Get leaveEntitlement belongs to emptype (from empType name)
    @Query("SELECT e.leaveEntitlements FROM EmployeeType e WHERE e.empTypeName = :empTypeName")
    List<LeaveEntitlement> getLeaveEntitlementByEmpTypeName(@Param("empTypeName") String empTypeName);
    
    // Get Employees belong to empType (from empType id)
    @Query("SELECT e.employees FROM EmployeeType e WHERE e.empTypeId = :empTypeId")
    List<Employee> getEmployeesByEmpTypeId(@Param("empTypeId") long empTypeId);
    
    // Get Employees belong to empType (from empType name)
    @Query("SELECT e.employees FROM EmployeeType e WHERE e.empTypeName = :empTypeName")
    List<Employee> getEmployeesByEmpTypeName(@Param("empTypeName") String empTypeName);
}
