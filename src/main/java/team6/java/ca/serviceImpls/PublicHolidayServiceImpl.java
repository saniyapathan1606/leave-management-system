package team6.java.ca.serviceImpls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import team6.java.ca.entities.PublicHoliday;

import team6.java.ca.repositories.PublicHolidayRepository;
import team6.java.ca.services.PublicHolidayService;

import java.time.LocalDate;
import java.util.List;

@Service
public class PublicHolidayServiceImpl implements PublicHolidayService {
    @Autowired
    private PublicHolidayRepository publicHolidayRepository;

    @Override
    public List<PublicHoliday> getAllPublicHolidays() {
        return publicHolidayRepository.findAll();
    }

    @Override
    public List<PublicHoliday> getPublicHolidaysInRange(LocalDate startDate, LocalDate endDate) {
        return publicHolidayRepository.findAllByDateBetween(startDate, endDate);
    }

    @Override
    public PublicHoliday savePublicHoliday(PublicHoliday publicHoliday) {
        return publicHolidayRepository.save(publicHoliday);
    }

    @Override
    public void deletePublicHoliday(Long id) {
        publicHolidayRepository.deleteById(id);
    }

    @Override
    public List<String> getAllPublicHolidaysName() {
        return publicHolidayRepository.getAllPublicHolidaysName();
    }

    @Override
    public int getNumberOfPublicHolidaysInRange(LocalDate startDate, LocalDate endDate) {
        return publicHolidayRepository.findAllByDateBetween(startDate, endDate).size();
    }
    
    @Override
    public boolean isEqualToHoliday(LocalDate currentDate)
    {
    	if(getAllPublicHolidays().contains(currentDate))
    	{
    		return true;
    	}
    	
    	return false;
    }
    
   

 
}
