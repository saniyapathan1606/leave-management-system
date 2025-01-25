package team6.java.ca.controllers;

import java.io.IOException;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletResponse;
import team6.java.ca.entities.EmployeeType;
import team6.java.ca.entities.LeaveEntitlement;
import team6.java.ca.entities.LeaveType;
import team6.java.ca.serviceImpls.EmployeeTypeServiceImpl;
import team6.java.ca.serviceImpls.LeaveEntitlementServiceImpl;
import team6.java.ca.serviceImpls.LeaveTypeServiceImpl;
import team6.java.ca.services.EmployeeService;

@Controller
@RequestMapping("/admin/leavetype")
public class LeaveTypeController {
	@Autowired
	private LeaveTypeServiceImpl leaveTypeService;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private LeaveEntitlementServiceImpl leaveentitlementService;

	@Autowired
	private EmployeeTypeServiceImpl ETserviceimpl;

	@GetMapping
	public String listLeaveTypes(Model model) {
		model.addAttribute("pagetitle", "Manage Leave Type");
		model.addAttribute("leaveTypes", leaveTypeService.findAll());
		model.addAttribute("leaveType", new LeaveType());
		model.addAttribute("employees", employeeService.findAllEmployees());
		return "admin-leavetype";
	}

	@PostMapping
	public String saveLeaveType(@ModelAttribute("leaveType") LeaveType leaveType, Model model) {
		if (leaveTypeService.existsByTypename(leaveType.getLeaveTypeName())) {
			model.addAttribute("leaveTypes", leaveTypeService.findAll());
			model.addAttribute("leaveType", new LeaveType());
			model.addAttribute("employees", employeeService.findAllEmployees());
			model.addAttribute("TnameError", "Type Name Duplicate!");
			return "admin-leavetype";
		}
		leaveTypeService.createLeaveType(leaveType);
		List<EmployeeType> ET1 = ETserviceimpl.getAllEmployeeTypes();
		for (EmployeeType Et : ET1) {
			LeaveEntitlement let = new LeaveEntitlement(Et, leaveType, 0.0);
			leaveentitlementService.createLeaveEntitlement(let);
		}
		return "redirect:/admin/leavetype";
	}

	@GetMapping("/generate-csv")
	public void generateCSV(HttpServletResponse response) throws IOException {
		response.setContentType("text/csv");
		response.setHeader("Content-Disposition", "attachment; file=leavetypes.csv");

		List<LeaveType> lTypes = leaveTypeService.findAllLeaveTypes();

		try (CSVPrinter csvPrinter = new CSVPrinter(response.getWriter(),
				CSVFormat.DEFAULT.withHeader("Leave Type Id", "Leave Type Name"))) {
			for (LeaveType lt : lTypes) {
				csvPrinter.printRecord(lt.getLeaveTypeId(), lt.getLeaveTypeName());
			}
		}
	}

}
