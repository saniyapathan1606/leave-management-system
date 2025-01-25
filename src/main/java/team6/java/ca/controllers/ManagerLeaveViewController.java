package team6.java.ca.controllers;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import team6.java.ca.entities.Employee;
import team6.java.ca.entities.EmployeeLeaveRecord;
import team6.java.ca.entities.EmployeeLeaveRecord.Status;
import team6.java.ca.entities.LeaveType;
import team6.java.ca.serviceImpls.EmployeeLeaveRecordServiceImpl;
import team6.java.ca.serviceImpls.EmployeeServiceImpl;
import team6.java.ca.serviceImpls.LeaveTypeServiceImpl;
import team6.java.ca.serviceImpls.LeaveUtilServiceImpl;
import team6.java.ca.serviceImpls.PublicHolidayServiceImpl;
import team6.java.ca.services.EmployeeLeaveRecordService;
import team6.java.ca.services.EmployeeService;
import team6.java.ca.services.LeaveTypeService;
import team6.java.ca.services.LeaveUtilService;
import team6.java.ca.services.PublicHolidayService;

@Controller
@RequestMapping("/manager")
public class ManagerLeaveViewController {

	@Autowired
	private EmployeeLeaveRecordService lrService;

	@Autowired
	private EmployeeService eService;

	@Autowired
	private LeaveTypeService lService;

	@Autowired
	private LeaveUtilService lutilService;

	@Autowired
	private PublicHolidayService pService;

	@Autowired
	public void setPublicHolidayService(PublicHolidayServiceImpl pService) {
		this.pService = pService;
	}

	@Autowired
	public void setLeaveRecordService(EmployeeLeaveRecordServiceImpl lrserviceImpl) {
		lrService = lrserviceImpl;
	}

	@Autowired
	public void setEmployeeService(EmployeeServiceImpl eserviceImpl) {
		eService = eserviceImpl;
	}

	@Autowired
	public void setLeaveTypeService(LeaveTypeServiceImpl ltserviceimpl) {
		lService = ltserviceimpl;
	}

	@Autowired
	public void setLeaveUtilService(LeaveUtilServiceImpl lutilServiceImpl) {
		lutilService = lutilServiceImpl;
	}

	@GetMapping("/check/{managerId}")
	public String viewMyTeam(@PathVariable("managerId") Long id,
			@RequestParam(value = "dateDropdown", required = false) String startDate, Model model) {
		Employee dummyManager = eService.findEmployeeByUserId(id);
		model.addAttribute("user", dummyManager);

		LocalDate today;
		if (startDate != null && !startDate.isEmpty()) {
			today = LocalDate.parse(startDate + "-01");
		} else {
			today = LocalDate.now().withDayOfMonth(1);
		}
		YearMonth yearMonth = YearMonth.of(today.getYear(), today.getMonth());
		int daysInMonth = yearMonth.lengthOfMonth();
		LocalDate endOfMonth = yearMonth.atEndOfMonth();

		List<EmployeeLeaveRecord> targetEmployeeLeaveRecords = lrService.findAllByLeaveDateBetweenAndApproveMgrId(today,
				endOfMonth, id);
		List<LeaveType> leaveTypes = lService.findAll();

		List<Integer> days = new ArrayList<>();
		List<String> weekdays = new ArrayList<>();
		for (int day = 1; day <= daysInMonth; day++) {
			days.add(day);
			LocalDate date = LocalDate.of(today.getYear(), today.getMonth(), day);
			weekdays.add(date.getDayOfWeek().toString());
		}

		model.addAttribute("days", days);
		model.addAttribute("weekdays", weekdays);
		model.addAttribute("targetEmployeeLeaveRecords", targetEmployeeLeaveRecords);
		model.addAttribute("leaveTypes", leaveTypes);
		model.addAttribute("year", today.getYear());
		model.addAttribute("month", today.getMonthValue());

		List<Employee> subordinates = eService.findAllEmployeesOfMgr(id);
		if (subordinates == null) {
			subordinates = new ArrayList<Employee>();
		}

		List<EmployeeLeaveRecord> leaveRecordsUnderCurrentManager = lrService.findAllLeaveRecordsOfMgr(id);
		if (leaveRecordsUnderCurrentManager == null) {
			leaveRecordsUnderCurrentManager = new ArrayList<EmployeeLeaveRecord>();
		}

		List<EmployeeLeaveRecord> approvedLeaveRecordsUnderCurrentManager = lrService
				.findApprovedLeaveRecordByManagerEmpId(id);
		if (approvedLeaveRecordsUnderCurrentManager == null) {
			approvedLeaveRecordsUnderCurrentManager = new ArrayList<EmployeeLeaveRecord>();
		}

		List<EmployeeLeaveRecord> pendingLeaveRecordsUnderCurrentManager = lrService
				.findPendingLeaveRecordByManagerEmpId(id);
		if (pendingLeaveRecordsUnderCurrentManager == null) {
			pendingLeaveRecordsUnderCurrentManager = new ArrayList<EmployeeLeaveRecord>();
		}

		Map<String, List<EmployeeLeaveRecord>> leaveRecordsByType = new HashMap<>();
		Map<String, Integer> pendingLeaveRecordsByType = new HashMap<>();
		Map<Long, Double> leaveRecordWorkingDaysMap = new HashMap<>();
		Map<Long, String> leaveRecordHolidayWeekendMap = new HashMap<>();

		for (LeaveType leaveType : leaveTypes) {
			List<EmployeeLeaveRecord> leaveRecords = lrService
					.findAllLeaveRecordsOfLeaveTypeUnderManager(leaveType.getLeaveTypeId(), id);
			leaveRecordsByType.put(leaveType.getLeaveTypeName(), leaveRecords);

			int pendingCount = 0;
			for (EmployeeLeaveRecord record : leaveRecords) {
				if (Status.Applied.equals(record.getStatus())) {
					pendingCount++;
				}
				double workingDays = lutilService.calculateActualLeaveDays(record);
				int weekendDays = lutilService.getNumberOfWeekendDaysInLeaveRange(record.getLeaveDate(),
						record.getEndDate());
				int publicHolidays = pService.getNumberOfPublicHolidaysInRange(record.getLeaveDate(),
						record.getEndDate());
				leaveRecordWorkingDaysMap.put(record.getLeaveId(), workingDays);
				leaveRecordHolidayWeekendMap.put(record.getLeaveId(), publicHolidays + "/" + weekendDays);
			}
			pendingLeaveRecordsByType.put(leaveType.getLeaveTypeName(), pendingCount);
		}

		model.addAttribute("subordinates", subordinates);
		model.addAttribute("leaveRecordTakers", leaveRecordsUnderCurrentManager);
		model.addAttribute("approvedLeaveRecordTakers", approvedLeaveRecordsUnderCurrentManager);
		model.addAttribute("pendingLeaveRecordTakers", pendingLeaveRecordsUnderCurrentManager);
		model.addAttribute("leaveRecordsByType", leaveRecordsByType);
		model.addAttribute("pendingLeaveRecordsByType", pendingLeaveRecordsByType);
		model.addAttribute("leaveRecordWorkingDaysMap", leaveRecordWorkingDaysMap);
		model.addAttribute("leaveRecordHolidayWeekendMap", leaveRecordHolidayWeekendMap);

		return "view-my-team";
	}
}
