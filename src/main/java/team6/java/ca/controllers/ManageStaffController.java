package team6.java.ca.controllers;

import java.io.IOException;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import team6.java.ca.entities.Employee;
import team6.java.ca.serviceImpls.EmployeeServiceImpl;
import team6.java.ca.services.EmployeeService;
import team6.java.ca.services.EmployeeTypeService;
import team6.java.ca.validators.EmployeeValidator;

@Controller
@RequestMapping("/admin")
public class ManageStaffController {

	@Autowired
	private EmployeeService eService;

	@Autowired
	private EmployeeTypeService ets;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private EmployeeValidator employeeValidator;

	@InitBinder
	private void initCourseBinder(WebDataBinder binder) {

		binder.addValidators(employeeValidator);
	}

	@Autowired
	private void setEmployeeService(EmployeeServiceImpl eService) {
		this.eService = eService;
	}

	@GetMapping("/managestaff")
	public String manageStaff(HttpSession session, Model model) {
		List<Employee> findAllEmployee = eService.findAllEmployees();
		model.addAttribute("employees", findAllEmployee);
		model.addAttribute("pagetitle", "Staff List");

		return "admin-managestaff";
	}

	@GetMapping("/managestaff/generate-csv")
	public void generateCSV(HttpServletResponse response) throws IOException {
		response.setContentType("text/csv");
		response.setHeader("Content-Disposition", "attachment; file=employees.csv");

		List<Employee> employees = eService.findAllEmployees();

		try (CSVPrinter csvPrinter = new CSVPrinter(response.getWriter(),
				CSVFormat.DEFAULT.withHeader("User ID", "Username", "First Name", "Last Name", "Email", "Employee Type",
						"Join Date", "Employee Status", "Is Manager", "Manager ID", "Subordinates"))) {
			for (Employee employee : employees) {
				csvPrinter.printRecord(employee.getUserId(), employee.getUsername(), employee.getFirstName(),
						employee.getLastName(), employee.getEmail(),
						employee.getEmpType() != null ? employee.getEmpType().getEmpTypeName() : "",
						employee.getJoinDate() != null ? employee.getJoinDate().toString() : "",
						employee.getEmpStatus() != null ? employee.getEmpStatus().name() : "", employee.isManager(),
						employee.getManager() != null ? employee.getManager().getUserId() : "",
						employee.getSubordinates() != null ? employee.getSubordinates().size() : 0);
			}
		}
	}

	@GetMapping("/employees/delete/{id}")
	public String deleteEmployeeForm(@PathVariable("id") Long id, Model model) {
		Employee employee = eService.findEmployeeByUserId(id);
		if (employee.isManager()) {
			List<Employee> subordinates = employee.getSubordinates();
			List<Employee> potentialManagers = eService.findAllByIsManagerTrueAndUserIdNot(id);
			model.addAttribute("employee", employee);
			model.addAttribute("subordinates", subordinates);
			model.addAttribute("potentialManagers", potentialManagers);
			model.addAttribute("pagetitle", "Delete Manager");

			return "delete-employee-form";

		} else {
			eService.deleteEmployee(id);
			return "redirect:/admin/managestaff";
		}
	}

	@PostMapping("/employees/delete/{id}/reassign")
	public String deleteEmployee(@PathVariable("id") Long id, @RequestParam("newManagerId") Long newManagerId) {
		Employee newManager = eService.findEmployeeByUserId(newManagerId);
		System.out.println(
				"Reassigning subordinates of manager with ID: " + id + " to new manager with ID: " + newManagerId);
		eService.reassignSubordinates(id, newManager);
		System.out.println("Subordinates reassigned. Deleting manager with ID: " + id);
		eService.deleteEmployee(id);
		System.out.println("Manager deleted.");
		return "redirect:/admin/managestaff";
	}

	@GetMapping("/employees/new")
	public String showNewEmployeeForm(Model model) {
		model.addAttribute("employee", new Employee());
		model.addAttribute("employeeTypes", ets.getAllEmployeeTypes());
		model.addAttribute("managers", eService.findAllManagers());
		model.addAttribute("pagetitle", "Add New Employee");

		return "add-employee-form";
	}

	@PostMapping("/employees/add")
	public String addEmployee(@Valid @ModelAttribute Employee employee, BindingResult result, Model model) {

		if (!result.hasErrors()) { // Check if previous validation has already found errors
			employeeValidator.validate(employee, result);
		}
		if (result.hasErrors()) {
			model.addAttribute("employeeTypes", ets.getAllEmployeeTypes());
			model.addAttribute("managers", eService.findAllManagers());

			return "add-employee-form";
		}
		employee.setPassword(passwordEncoder.encode(employee.getPassword()));
		eService.createEmployee(employee);

		return "redirect:/admin/managestaff";
	}

	@GetMapping("/employees/edit/{id}")
	public String editEmployeeForm(@PathVariable("id") Long id, Model model) {
		Employee employee = eService.findEmployeeByUserId(id);

		model.addAttribute("employee", employee);
		model.addAttribute("employeeTypes", ets.getAllEmployeeTypes());
		model.addAttribute("managers", eService.findAllManagers());
		model.addAttribute("pagetitle", "Update Employee");
		return "update-employee-form";
	}

	@PostMapping("/employees/edit")
	public String editEmployee(@ModelAttribute Employee employee) {

		Employee existingEmployee = eService.findEmployeeByUserId(employee.getUserId());

		existingEmployee.setEmail(employee.getEmail());
		existingEmployee.setEmpType(employee.getEmpType());
		existingEmployee.setEmpStatus(employee.getEmpStatus());
		existingEmployee.setManager(employee.getManager());

		eService.createEmployee(existingEmployee);

		return "redirect:/admin/managestaff";
	}

	@GetMapping("/hierarchy/edit/{empId}")
	public String showEditForm(@PathVariable("empId") long empId, Model model) {
		Employee employee = eService.findEmployeeByUserId(empId);
		List<Employee> managerEmployees = eService.findAllManagers();
		model.addAttribute("employee", employee);
		model.addAttribute("managerEmployees", managerEmployees);
		model.addAttribute("pagetitle", "Edit Hierachy");
		return "hierarchy-edit-form";
	}

	@PostMapping("/hierarchy/edit/mgrId")
	public String updateManager(@RequestParam("managerId") Long managerId, @RequestParam("empId") Long empId) {

		Employee currentEmployee = eService.findEmployeeByUserId(empId);
		Employee chosenManager = eService.findEmployeeByUserId(managerId);

		currentEmployee.setManager(chosenManager);

		eService.save(currentEmployee);

		return "redirect:/admin/managestaff";

	}

}
