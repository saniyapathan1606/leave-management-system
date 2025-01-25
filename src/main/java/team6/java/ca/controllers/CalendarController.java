package team6.java.ca.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import team6.java.ca.entities.EmployeeLeaveRecord;
import team6.java.ca.entities.LeaveType;
import team6.java.ca.services.EmployeeLeaveRecordService;
import team6.java.ca.services.LeaveTypeService;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CalendarController {

    @Autowired
    private EmployeeLeaveRecordService lrService;

    @Autowired
    private LeaveTypeService leaveTypeService;

    @GetMapping("/calendar/{startDate}")
    public String getCalendar(@PathVariable LocalDate startDate, Model model) {
        LocalDate today = startDate;
        YearMonth yearMonth = YearMonth.of(today.getYear(), today.getMonth());
        int daysInMonth = yearMonth.lengthOfMonth();
        LocalDate endOfMonth = yearMonth.atEndOfMonth();

        List<EmployeeLeaveRecord> targetEmployeeLeaveRecords = lrService.findAllByLeaveDateBetween(today, endOfMonth);
        List<LeaveType> leaveTypes = leaveTypeService.findAll(); // Fetch all leave types

        List<Integer> days = new ArrayList<>();
        List<String> weekdays = new ArrayList<>();
        for (int day = 1; day <= daysInMonth; day++) {
            days.add(day);
            LocalDate date = LocalDate.of(today.getYear(), today.getMonth(), day);
            weekdays.add(date.getDayOfWeek().toString());
        }

        model.addAttribute("days", days);
        model.addAttribute("weekdays", weekdays);
        model.addAttribute("targetEmployeeLeaveRecords", targetEmployeeLeaveRecords);
        model.addAttribute("leaveTypes", leaveTypes);
        model.addAttribute("year", today.getYear());
        model.addAttribute("month", today.getMonthValue());
        return "calendar";
    }
}

