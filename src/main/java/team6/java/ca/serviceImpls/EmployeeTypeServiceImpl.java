package team6.java.ca.serviceImpls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import team6.java.ca.entities.Employee;
import team6.java.ca.entities.EmployeeType;
import team6.java.ca.entities.LeaveEntitlement;
import team6.java.ca.repositories.EmployeeTypeRepository;
import team6.java.ca.services.EmployeeTypeService;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeTypeServiceImpl implements EmployeeTypeService {

    @Autowired
    private EmployeeTypeRepository employeeTypeRepository;

    @Override
    public EmployeeType getEmpTypeById(long empTypeId) {
        return employeeTypeRepository.findById(empTypeId).orElse(null);
    }

    @Override
    public String getEmpTypeNameById(long empTypeId) {
        return employeeTypeRepository.getEmpTypeNameById(empTypeId);
    }

    @Override
    public List<LeaveEntitlement> getLeaveEntitlementByEmpTypeId(long empTypeId) {
        return employeeTypeRepository.getLeaveEntitlementByEmpTypeId(empTypeId);
    }

    @Override
    public List<LeaveEntitlement> getLeaveEntitlementByEmpTypeName(String empTypeName) {
        return employeeTypeRepository.getLeaveEntitlementByEmpTypeName(empTypeName);
    }

    @Override
    public List<Employee> getEmployeesByEmpTypeId(long empTypeId) {
        return employeeTypeRepository.getEmployeesByEmpTypeId(empTypeId);
    }

    @Override
    public List<Employee> getEmployeesByEmpTypeName(String empTypeName) {
        return employeeTypeRepository.getEmployeesByEmpTypeName(empTypeName);
    }

    @Override
    public EmployeeType addEmployeeType(EmployeeType employeeType) {
        return employeeTypeRepository.save(employeeType);
    }

    @Override
    public EmployeeType updateEmployeeType(long empTypeId, EmployeeType employeeType) {
        Optional<EmployeeType> existingEmployeeType = employeeTypeRepository.findById(empTypeId);
        if (existingEmployeeType.isPresent()) {
            EmployeeType et = existingEmployeeType.get();
            et.setEmpTypeName(employeeType.getEmpTypeName());
            et.setLeaveEntitlements(employeeType.getLeaveEntitlements());
            et.setEmployees(employeeType.getEmployees());
            return employeeTypeRepository.save(et);
        }
        return null;
    }

    @Override
    public void deleteEmployeeType(long empTypeId) {
        employeeTypeRepository.deleteById(empTypeId);
    }

    @Override
    public List<EmployeeType> getAllEmployeeTypes() {
        return employeeTypeRepository.findAll();
    }
    
    
}
