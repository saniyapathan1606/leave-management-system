package team6.java.ca.services;
import java.util.List;
import java.time.LocalDate;

import team6.java.ca.entities.PublicHoliday;


public interface PublicHolidayService {
	
	List<PublicHoliday> getAllPublicHolidays();
	List<PublicHoliday> getPublicHolidaysInRange(LocalDate startDate, LocalDate endDate);
	PublicHoliday savePublicHoliday(PublicHoliday publicHoliday);
	void deletePublicHoliday(Long id);
	
	List<String> getAllPublicHolidaysName();
	
	int getNumberOfPublicHolidaysInRange(LocalDate startDate, LocalDate endDate);
	
	boolean isEqualToHoliday(LocalDate currentDate);



}
