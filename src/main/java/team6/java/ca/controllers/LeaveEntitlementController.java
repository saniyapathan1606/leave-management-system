package team6.java.ca.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import team6.java.ca.entities.LeaveEntitlement;
import team6.java.ca.services.LeaveEntitlementService;

@Controller
@RequestMapping("/admin/entitlement")
public class LeaveEntitlementController {

	@Autowired
	private LeaveEntitlementService leaveEntitlementService;

	@GetMapping("/list")
	public String entitlementListPage(Model model) {
		List<LeaveEntitlement> entitlementList = leaveEntitlementService.findAllLeaveEntitlements();
		model.addAttribute("entitlementList", entitlementList);
		model.addAttribute("pagetitle", "Leave Entitlement List");
		return "admin-leaveEntitlement";
	}

	@GetMapping("/edit/{id}")
	public String showEditForm(@PathVariable("id") long id, Model model) {
		LeaveEntitlement entitlement = leaveEntitlementService.findLeaveEntitlementById(id);
		model.addAttribute("entitlement", entitlement);
		model.addAttribute("pagetitle", "Edit Leave Entitlement");
		return "leaveEntitlement-edit-form";
	}

	@PostMapping("/edit/{id}")
	public String updateEntitlement(@PathVariable("id") long id,
			@Valid @ModelAttribute("entitlement") LeaveEntitlement entitlement, BindingResult result, Model model) {
		if (result.hasErrors()) {
			LeaveEntitlement originalEntitlement = leaveEntitlementService.findLeaveEntitlementById(id);
			model.addAttribute("entitlement", originalEntitlement);
			model.addAttribute("customErrorMessage", "The input number should be 0-60.");

			return "leaveEntitlement-edit-form";
		}

		leaveEntitlementService.updateLeaveEntitlement(id, entitlement);
		return "redirect:/admin/entitlement/list";
	}

	@GetMapping("/delete/{leaveEntId}")
	public String deleteEntitlement(@PathVariable Long leaveEntId) {
		leaveEntitlementService.deleteLeaveEntitlement(leaveEntId);
		return "redirect:/admin/entitlement/list";
	}

	// creating the csv file
	@GetMapping("/export")
	public void exportToCSV(HttpServletResponse response) throws IOException {
		String filename = "leave_entitlements.csv";

		response.setContentType("text/csv");
		response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"");

		List<LeaveEntitlement> entitlements = leaveEntitlementService.findAllLeaveEntitlements();

		try (PrintWriter writer = response.getWriter();
				CSVPrinter csvPrinter = new CSVPrinter(writer,
						CSVFormat.DEFAULT.withHeader("ID", "Employee Type", "Leave Type", "Entitlement Quantity"))) {

			for (LeaveEntitlement entitlement : entitlements) {
				csvPrinter.printRecord(entitlement.getLeaveEntId(), entitlement.getEmpType().getEmpTypeName(),
						entitlement.getLeaveType().getLeaveTypeName(), entitlement.getEntitlementQty());
			}

		}

	}

}
