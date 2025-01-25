package team6.java.ca.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import team6.java.ca.entities.CompensationClaimRecord;
import team6.java.ca.entities.CompensationClaimRecord.ClaimStatus;
import team6.java.ca.entities.Employee;
import team6.java.ca.serviceImpls.EmployeeServiceImpl;
import team6.java.ca.services.CompensationClaimRecordService;

@Controller
@RequestMapping("compensation")
public class CompensationClaimController {
	@Autowired
	private CompensationClaimRecordService ccrService;

	@Autowired
	private EmployeeServiceImpl empServImp;

	// temporary method to show all claims first
	// later need to differentiate between employee, manager views
	// this should be for indiviudal claim history

	@GetMapping("/claim/view/{id}")
	public String viewClaim(@PathVariable("id") long claimId, Model model, HttpServletRequest request) {
		// Retrieve current viewer
		String curr_username = (String) request.getSession().getAttribute("username");
		Employee curr_viewer = empServImp.findEmployeeByUsername(curr_username);

		// Find claim by ID
		CompensationClaimRecord curr_claim = ccrService.findClaimByClaimId(claimId);
		if (curr_claim == null) {
			return "redirect:/home";
		}

		Employee curr_appr_mgr = curr_claim.getApproveManager();
		Employee curr_claimant = curr_claim.getEmployee();

		model.addAttribute("claimstatus", curr_claim.getStatus().toString());

		// Check if viewer is claimant or manager
		if (curr_viewer.equals(curr_claimant)) {
			model.addAttribute("userRole", "EMPLOYEE");
		} else if (curr_viewer.equals(curr_appr_mgr)) {
			model.addAttribute("userRole", "MANAGER");
		} else {
			return "redirect:/home"; // Unauthorized access
		}

		model.addAttribute("claim", curr_claim);
		model.addAttribute("pagetitle", "Claim Details");
		return "claim-details";
	}

	@GetMapping("/claim/cancel/{id}")
	public String cancelClaim(@PathVariable("id") long claimId) {
		CompensationClaimRecord curr_claim = ccrService.findClaimByClaimId(claimId);
		if (curr_claim != null) {
			curr_claim.setStatus(ClaimStatus.Cancelled);
			ccrService.updateClaimStatus(claimId, curr_claim.getStatus());
		}
		return "redirect:/team/pending";
	}

	@GetMapping("/claim/approve/{id}")
	public String approveClaim(@PathVariable("id") long claimId) {
		CompensationClaimRecord curr_claim = ccrService.findClaimByClaimId(claimId);
		if (curr_claim != null) {
			curr_claim.setStatus(ClaimStatus.Approved);
			ccrService.updateClaimStatus(claimId, curr_claim.getStatus());
		}
		return "redirect:/team/pending";
	}

	// For standalone page for multi-action javascript way
//	@PostMapping("/claim/reject/{id}")
//	public String rejectClaim(@PathVariable("id") long claimId) {
//		CompensationClaimRecord curr_claim = ccrService.findClaimByClaimId(claimId);
//		if (curr_claim != null) {
//			curr_claim.setStatus(ClaimStatus.Rejected);
//			ccrService.updateClaimStatus(claimId, curr_claim.getStatus());
//		}
//		return "redirect:/compensation/claim/view" + claimId;
//	}

	@GetMapping("/claim/reject/{id}")
	public String rejectClaim(@PathVariable("id") long claimId) {
		CompensationClaimRecord curr_claim = ccrService.findClaimByClaimId(claimId);
		if (curr_claim != null) {
			curr_claim.setStatus(ClaimStatus.Rejected);
			ccrService.updateClaimStatus(claimId, curr_claim.getStatus());
		}
		return "redirect:/team/pending";
	}

	@GetMapping("/create")
	public String createClaim(Model model) {
		model.addAttribute("claim", new CompensationClaimRecord());
		model.addAttribute("pagetitle", "Create a new compensation claim");
		return "create-claim";
	}

	@PostMapping("/create")
	public String saveClaim(@ModelAttribute CompensationClaimRecord inCCR, HttpServletRequest request) {
		String curr_username = (String) request.getSession().getAttribute("username");
		Employee curr_emp = empServImp.findEmployeeByUsername(curr_username);
		inCCR.setEmployee(curr_emp);
		Employee mgr = curr_emp.getManager();
		if (mgr != null) {
			inCCR.setApproveManager(curr_emp.getManager());
		} else {
			inCCR.setApproveManager(null);
		}

		inCCR.setStatus(ClaimStatus.Pending);
		ccrService.createClaim(inCCR);
		return "redirect:/home";
	}

}
