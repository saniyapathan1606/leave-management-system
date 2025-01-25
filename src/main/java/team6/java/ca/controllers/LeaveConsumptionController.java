package team6.java.ca.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import team6.java.ca.entities.Employee;
import team6.java.ca.entities.EmployeeLeaveRecord;
import team6.java.ca.services.EmployeeLeaveRecordService;
import team6.java.ca.services.EmployeeService;
import team6.java.ca.services.LeaveUtilService;

@CrossOrigin
@RestController
@RequestMapping("/leaveconsumption")
public class LeaveConsumptionController {

	@Autowired
	private EmployeeLeaveRecordService elService;

	@Autowired
	private EmployeeService eService;

	@Autowired
	private LeaveUtilService luService;

	@GetMapping("/getUsedLeaveQty")
	public double getUsedLeaveQty(HttpSession sessionObj) {
		Employee employee = (Employee) sessionObj.getAttribute("employee");

		List<EmployeeLeaveRecord> records = elService.findAllLeaveRecordsOfEmployee(employee.getUserId());

		double total = 0;
		for (EmployeeLeaveRecord erc : records) {
			total += luService.calculateActualLeaveDays(erc);
		}

		return total;

	}

}
