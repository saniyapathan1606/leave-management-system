package team6.java.ca.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import team6.java.ca.entities.EmployeeLeaveRecord;
import team6.java.ca.entities.EmployeeLeaveRecord.Status;
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
public class ManagerLeaveController {

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

	@GetMapping("/manager/leave/approve/{id}")
	public String managerApproveLeave(@PathVariable("id") Long leaveId) {
		EmployeeLeaveRecord leaveRecord = lrService.findByLeaveId(leaveId);
		if (leaveRecord != null) {
			leaveRecord.setStatus(Status.Approved);
			lrService.createEmployeeLeaveRecord(leaveRecord);
		}
		return "redirect:/team/pending";
	}

	@GetMapping("/manager/leave/reject/{id}")
	public String managerRejectLeave(@PathVariable("id") Long leaveId) {
		EmployeeLeaveRecord leaveRecord = lrService.findByLeaveId(leaveId);
		if (leaveRecord != null) {
			leaveRecord.setStatus(Status.Rejected);
			lrService.createEmployeeLeaveRecord(leaveRecord);
		}
		return "redirect:/team/pending";
	}
}
