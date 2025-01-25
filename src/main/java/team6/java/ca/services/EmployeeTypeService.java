package team6.java.ca.services;

import team6.java.ca.entities.Employee;
import team6.java.ca.entities.EmployeeType;
import team6.java.ca.entities.LeaveEntitlement;

import java.util.List;

public interface EmployeeTypeService {
	// Retrieve EmployeeType by id
    EmployeeType getEmpTypeById(long empTypeId);

    // Retrieve EmployeeType name by id
    String getEmpTypeNameById(long empTypeId);

    // Retrieve LeaveEntitlement list by EmployeeType id
    List<LeaveEntitlement> getLeaveEntitlementByEmpTypeId(long empTypeId);

    // Retrieve LeaveEntitlement list by EmployeeType name
    List<LeaveEntitlement> getLeaveEntitlementByEmpTypeName(String empTypeName);

    // Retrieve Employees by EmployeeType id
    List<Employee> getEmployeesByEmpTypeId(long empTypeId);

    // Retrieve Employees by EmployeeType name
    List<Employee> getEmployeesByEmpTypeName(String empTypeName);
    
    // Add a new EmployeeType
    EmployeeType addEmployeeType(EmployeeType employeeType);

    // Update an existing EmployeeType
    EmployeeType updateEmployeeType(long empTypeId, EmployeeType employeeType);

    // Delete an EmployeeType by id
    void deleteEmployeeType(long empTypeId);

    // List all EmployeeTypes
    List<EmployeeType> getAllEmployeeTypes();
}
