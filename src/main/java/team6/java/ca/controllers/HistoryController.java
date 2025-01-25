package team6.java.ca.controllers;

import java.io.PrintWriter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import team6.java.ca.entities.CompensationClaimRecord;
import team6.java.ca.entities.Employee;
import team6.java.ca.entities.EmployeeLeaveRecord;
import team6.java.ca.serviceImpls.CompensationClaimRecordServiceImpl;
import team6.java.ca.serviceImpls.EmployeeLeaveRecordServiceImpl;

@Controller
@RequestMapping("history")
public class HistoryController {
	@Autowired
	private EmployeeLeaveRecordServiceImpl lrService;

	@Autowired
	private CompensationClaimRecordServiceImpl crService;

	// Actual methods

	@GetMapping("/view")
	public String viewHistory(HttpSession sessionObj, Model model) {
		Employee employee = (Employee) sessionObj.getAttribute("employee");
		model.addAttribute("pagetitle", "My History");

		List<EmployeeLeaveRecord> myleaves = lrService.findAllByUserIdOrderByDate(employee.getUserId());
		System.out.println(myleaves.size());
		model.addAttribute("totalleave", myleaves.size());
		model.addAttribute("myleaves", myleaves);

		List<CompensationClaimRecord> myclaims = crService.findAllByUserId(employee.getUserId());
		System.out.println(myclaims.size());
		model.addAttribute("totalclaim", myclaims.size());
		model.addAttribute("myclaims", myclaims);

		return "view-history";
	}

	@GetMapping("/leaves/download-csv")
	public void downloadCSV(HttpServletResponse response, HttpSession sessionObj) throws Exception {
		response.setContentType("text/csv");
		response.setHeader("Content-Disposition", "attachment; filename=employee_leave_records.csv");

		Employee employee = (Employee) sessionObj.getAttribute("employee");
		List<EmployeeLeaveRecord> leaveRecords = lrService.findAllByUserIdOrderByDate(employee.getUserId());
		;

		try (PrintWriter writer = response.getWriter()) {
			writer.println("Employee ID,Employee Name,Leave Type,Start Date,End Date,Status");
			for (EmployeeLeaveRecord record : leaveRecords) {
				writer.println(
						record.getEmployee() + "," + record.getEmployee().getFullName() + "," + record.getLeaveType()
								+ "," + record.getLeaveDate() + "," + record.getEndDate() + "," + record.getStatus());
			}
		}
	}

}
