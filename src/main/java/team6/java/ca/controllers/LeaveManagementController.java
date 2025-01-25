package team6.java.ca.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import team6.java.ca.entities.Employee;
import team6.java.ca.entities.EmployeeLeaveRecord;
import team6.java.ca.entities.EmployeeLeaveRecord.Status;
import team6.java.ca.services.EmployeeLeaveRecordService;
import team6.java.ca.services.EmployeeService;
import team6.java.ca.services.LeaveEntitlementService;
import team6.java.ca.services.LeaveTypeService;
import team6.java.ca.services.LeaveUtilService;

@Controller
public class LeaveManagementController {

	private static final Logger logger = LoggerFactory.getLogger(LeaveManagementController.class);

	@Autowired
	private EmployeeLeaveRecordService elService;

	@Autowired
	private EmployeeService eService;

	@Autowired
	private LeaveTypeService ltService;

	@Autowired
	private LeaveEntitlementService leService;

	@Autowired
	private LeaveUtilService luService;

	@GetMapping("/leavemgmt/create")
	public String createLeaveRecord(Model model, HttpSession sessionObj) {

		long empId = 4L;
		Employee currentEmployee = eService.findEmployeeByUserId(empId);

		EmployeeLeaveRecord leaveRecord = new EmployeeLeaveRecord();
		leaveRecord.setEmployee(currentEmployee);
		leaveRecord.setApproveManager(currentEmployee.getManager());

		model.addAttribute("empLeaveRecord", leaveRecord);
		model.addAttribute("coveringEmployees", eService.findAllExceptCurrentOne(empId));
		model.addAttribute("leaveTypes", ltService.findAll());
		model.addAttribute("pagetitle", "Apply Leave");

		return "leave-mgmt-create";
	}

	@PostMapping("/leavemgmt/create")
	public String saveLeaveRecord(@ModelAttribute("empLeaveRecord") EmployeeLeaveRecord empLeaveRecord, Model model) {

		long empId = 4L;
		Employee currentEmployee = eService.findEmployeeByUserId(empId);

		empLeaveRecord.setEmployee(currentEmployee);
		empLeaveRecord.setApproveManager(currentEmployee.getManager());
		empLeaveRecord.setStatus(EmployeeLeaveRecord.Status.Applied);

		elService.createEmployeeLeaveRecord(empLeaveRecord);
		model.addAttribute("successMessage", "Leave record saved successfully!");
		return "redirect:/history/view";
	}

	@GetMapping("/leavemgmt/update/{id}")
	public String showUpdateForm(@PathVariable("id") long id, Model model) {
		EmployeeLeaveRecord leaveRecord = elService.findEmployeeLeaveRecordById(id);
		if (leaveRecord == null) {
			throw new IllegalArgumentException("Invalid leave record Id:" + id);
		}

		long empId = 4L;
		Employee currentEmployee = eService.findEmployeeByUserId(empId);

		if (currentEmployee == null) {
			throw new IllegalArgumentException("No employee found with ID: " + empId);
		}

		model.addAttribute("currentEmployee", currentEmployee);
		model.addAttribute("empLeaveRecord", leaveRecord);
		model.addAttribute("coveringEmployees",
				eService.findAllExceptCurrentOne(leaveRecord.getEmployee().getUserId()));
		model.addAttribute("leaveTypes", ltService.findAll());
		model.addAttribute("pagetitle", "Update Leave Record");

		return "leave-mgmt-update";
	}

	@PostMapping("/leavemgmt/update")
	public String updateLeaveRecord(@ModelAttribute("empLeaveRecord") EmployeeLeaveRecord empLeaveRecord,
			@RequestParam("currentEmployeeId") Long currentEmployeeId, Model model) {

		Employee currentEmployee = eService.findEmployeeByUserId(currentEmployeeId);
		if (currentEmployee == null) {
			throw new IllegalArgumentException("No employee found with ID: " + currentEmployeeId);
		}

		empLeaveRecord.setEmployee(currentEmployee);
		empLeaveRecord.setApproveManager(currentEmployee.getManager());
		empLeaveRecord.setStatus(EmployeeLeaveRecord.Status.Updated);
		elService.createEmployeeLeaveRecord(empLeaveRecord);

		return "redirect:/home";
	}

	@GetMapping("/leavemgmt/cancel/{id}")
	public String cancel(@PathVariable("id") Long leaveId) {
		EmployeeLeaveRecord empLeaveRecord = elService.findEmployeeLeaveRecordById(leaveId);
		empLeaveRecord.setStatus(Status.Cancelled);
		elService.createEmployeeLeaveRecord(empLeaveRecord);
		return "redirect:/home";
	}

	@GetMapping("/leavemgmt/delete/{id}")
	public String delete(@PathVariable("id") Long leaveId) {
		EmployeeLeaveRecord empLeaveRecord = elService.findEmployeeLeaveRecordById(leaveId);
		empLeaveRecord.setStatus(Status.Deleted);
		elService.createEmployeeLeaveRecord(empLeaveRecord);
		return "redirect:/home";
	}

}
