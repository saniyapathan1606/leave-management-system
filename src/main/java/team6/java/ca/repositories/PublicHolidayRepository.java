package team6.java.ca.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import team6.java.ca.entities.PublicHoliday;

import java.time.LocalDate;
import java.util.List;

public interface PublicHolidayRepository extends JpaRepository<PublicHoliday, Long> {
    List<PublicHoliday> findAllByDateBetween(LocalDate startDate, LocalDate endDate);
    
    @Query("SELECT p.name FROM PublicHoliday p")
    List<String> getAllPublicHolidaysName();
}
