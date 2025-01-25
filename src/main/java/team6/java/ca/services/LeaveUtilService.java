package team6.java.ca.services;

import team6.java.ca.entities.EmployeeLeaveRecord;
import java.time.LocalDate;

public interface LeaveUtilService {
	
	double calculateActualLeaveDays(EmployeeLeaveRecord leaveRecord);
	int getNumberOfWeekendDaysInLeaveRange(LocalDate startDate, LocalDate endDate);
	
	boolean isWorkingDay(LocalDate currentDate);
	

}
