package team6.java.ca.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import team6.java.ca.entities.PublicHoliday;
import team6.java.ca.services.PublicHolidayService;

@CrossOrigin
@Controller
@RequestMapping("/public-holidays")
public class PublicHolidayController {
	@Autowired
	private PublicHolidayService publicHolidayService;

	@GetMapping
	public String getAllPublicHolidays(Model model) {
		List<PublicHoliday> publicHolidays = publicHolidayService.getAllPublicHolidays();

		if (publicHolidays == null) {
			publicHolidays = new ArrayList<>();
		}

		model.addAttribute("publicHolidays", publicHolidays);
		return "public-holidays";
	}

	@GetMapping("/add")
	public String showAddPublicHolidayForm(Model model) {
		model.addAttribute("publicHoliday", new PublicHoliday());
		return "add-public-holiday";
	}

	@PostMapping("/add")
	public String addPublicHoliday(@ModelAttribute PublicHoliday publicHoliday) {
		publicHolidayService.savePublicHoliday(publicHoliday);
		return "redirect:/public-holidays";
	}

	@GetMapping("/delete/{id}")
	public String deletePublicHoliday(@PathVariable Long id) {
		publicHolidayService.deletePublicHoliday(id);
		return "redirect:/public-holidays";
	}

	@ResponseBody
	@GetMapping("/getHolidays")
	public List<PublicHoliday> getAllPublicHolidays() {
		return publicHolidayService.getAllPublicHolidays();
	}

}
