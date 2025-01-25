package team6.java.ca.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import team6.java.ca.entities.CompensationClaimRecord;
import team6.java.ca.entities.Employee;
import team6.java.ca.entities.EmployeeLeaveRecord;
import team6.java.ca.serviceImpls.CompensationClaimRecordServiceImpl;
import team6.java.ca.serviceImpls.EmployeeLeaveRecordServiceImpl;
import team6.java.ca.serviceImpls.EmployeeServiceImpl;
import team6.java.ca.serviceImpls.LeaveTypeServiceImpl;
import team6.java.ca.serviceImpls.LeaveUtilServiceImpl;
import team6.java.ca.serviceImpls.PublicHolidayServiceImpl;

@Controller
@RequestMapping("/team")
public class TeamController {

	@Autowired
	private EmployeeLeaveRecordServiceImpl lrService;

	@Autowired
	private EmployeeServiceImpl eService;

	@Autowired
	private LeaveTypeServiceImpl lService;

	@Autowired
	private LeaveUtilServiceImpl lutilService;

	@Autowired
	private PublicHolidayServiceImpl pService;

	@Autowired
	private CompensationClaimRecordServiceImpl crService;

	@GetMapping("/pending")
	public String viewTeamPending(HttpSession sessionObj, Model model) {
		Employee manager = (Employee) sessionObj.getAttribute("employee");
		model.addAttribute("pagetitle", "Pending");

		List<EmployeeLeaveRecord> pendingleaves = lrService.findByMgrIdnStatus(manager.getUserId(),
				EmployeeLeaveRecord.Status.Applied);
		model.addAttribute("totalpendingleave", pendingleaves.size());
		model.addAttribute("pendingleaves", pendingleaves);

		List<CompensationClaimRecord> pendingclaims = crService.findByMgrIdnStatus(manager.getUserId(),
				CompensationClaimRecord.ClaimStatus.Pending);
		model.addAttribute("totalpendingclaim", pendingclaims.size());
		model.addAttribute("pendingclaims", pendingclaims);

		return "view-pending";
	}

	@GetMapping("/claims")
	public String viewClaimsTeam(Model model, HttpServletRequest request) {
		String curr_username = (String) request.getSession().getAttribute("username");
		Employee curr_emp = eService.findEmployeeByUsername(curr_username);
		if (curr_emp.isManager()) {
			model.addAttribute("claims", crService.findAllClaimRecordsOfMgr(curr_emp));
			model.addAttribute("pagetitle", "My Team Claim Overview");
			return "team-claims";
		}
		return "redirect:/history/view";
	}

}