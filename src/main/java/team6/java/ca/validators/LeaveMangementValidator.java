package team6.java.ca.validators;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import team6.java.ca.entities.Employee;
import team6.java.ca.entities.EmployeeLeaveRecord;
import team6.java.ca.entities.LeaveType;
import team6.java.ca.services.EmployeeLeaveRecordService;
import team6.java.ca.services.LeaveEntitlementService;
import team6.java.ca.services.LeaveTypeService;
import team6.java.ca.services.LeaveUtilService;
import team6.java.ca.services.PublicHolidayService;

@Component
public class LeaveMangementValidator implements Validator{
	
	@Autowired
	private LeaveUtilService luService;
	
	@Autowired
	private PublicHolidayService phService;
	
	@Autowired
	private EmployeeLeaveRecordService elService;
	
	@Autowired
	private LeaveTypeService ltService;
	
	@Autowired
	private LeaveEntitlementService letService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return EmployeeLeaveRecord.class.isAssignableFrom(clazz);
	}


	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		EmployeeLeaveRecord empLeaveRecord = (EmployeeLeaveRecord) target;
		LocalDate leaveDate = empLeaveRecord.getLeaveDate();
		LocalDate endDate = empLeaveRecord.getEndDate();
		
		Duration duration = Duration.between(leaveDate, endDate);
		double days = duration.toDays();
		
		LocalDate today = LocalDate.now();
		
		if(!(leaveDate == null) && !(endDate == null))
		{
			if(leaveDate.isBefore(today))
			{
				errors.rejectValue("leaveDate", "error.leaveDatePast", "Leave date must be future date");
				
			}
			
			if(leaveDate.isAfter(endDate))
			{
				errors.rejectValue("endDate", "error.order", "Dates should be chronologically increasing order");
				
			}
			
			if(!isWorkingDay(leaveDate) || !isWorkingDay(endDate))
			{
				errors.rejectValue("leaveDate", "error.leaveDateWorkingDay", "Start date must be a working day");
				errors.rejectValue("endDate", "error.endDateWorkingDay", "End date must be a working day");
				
			}
		}
		
		Employee employee = empLeaveRecord.getEmployee();
		
		List<EmployeeLeaveRecord> records = elService.findAllLeaveRecordsOfEmployee(employee.getUserId());
		
		boolean isOverLapped = isOverLapped(records, empLeaveRecord);
		
		if(isOverLapped)
		{
			errors.rejectValue("leaveQty", "error.leaveQtyOverlapped", "Leave period is overlapped");
		}
		
		if(empLeaveRecord.getLeaveType().getLeaveTypeName().equalsIgnoreCase("Annual"))
		{
			if(employee != null)
			{
				LeaveType annualType = ltService.findLeaveTypeByName("Annual");

				double currentEmployeeConsumedLeaveDateQty = getUsedLeaveQty(employee);
					
				double currentEmployeePermittedLeaveDateQty = letService.findLeaveEntitlementByEmpTypeIdAndLeaveTypeId(employee.getEmpType().getEmpTypeId(),annualType.getLeaveTypeId()).getEntitlementQty();
				
				double leftQty = currentEmployeePermittedLeaveDateQty - currentEmployeeConsumedLeaveDateQty;
				
				if(days > leftQty)
				{
					errors.rejectValue("leaveQty", "error.leaveQtyExceeed", "Annual leave days exceed");
				}
				
			}
		}
		
		

		
		
	}
	
	
	
	
	
	private boolean isOverLapped(List<EmployeeLeaveRecord> currentEmployeeExistingLeaveRecords, EmployeeLeaveRecord newLeaveRecord) {
        LocalDate startNewDate = newLeaveRecord.getLeaveDate();
        LocalDate endNewDate = newLeaveRecord.getEndDate(); // Fixed to use the end date of the new leave record

        for (EmployeeLeaveRecord elr : currentEmployeeExistingLeaveRecords) {
            LocalDate startExistingCurrentDate = elr.getLeaveDate();
            LocalDate endExistingCurrentDate = elr.getEndDate();

            // Check if the new leave record overlaps with the existing leave record
            if ((startNewDate.isBefore(endExistingCurrentDate) || startNewDate.equals(endExistingCurrentDate)) &&
                (endNewDate.isAfter(startExistingCurrentDate) || endNewDate.equals(startExistingCurrentDate))) {
                return true;
            }
        }

        return false;
    }
	
	
	private double getUsedLeaveQty(Employee employee)
	{
		
		List<EmployeeLeaveRecord> records = elService.findAllLeaveRecordsOfEmployee(employee.getUserId());
	
		double total = 0;
		for(EmployeeLeaveRecord erc : records)
		{
			total +=  calculateActualLeaveDays(erc);
		}
		
		return total;
				                	
	}
	
	private double calculateActualLeaveDays(EmployeeLeaveRecord leaveRecord)
	{
		return luService.calculateActualLeaveDays(leaveRecord);
	}
	
	
	private boolean isPublicHoliday(LocalDate currentDate) {
		return phService.isEqualToHoliday(currentDate);
	}
	
	private boolean isWorkingDay(LocalDate currentDate)
	{
		return luService.isWorkingDay(currentDate);
	}


	
	
	
	

}
