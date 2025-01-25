package team6.java.ca;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import team6.java.ca.entities.EmployeeLeaveRecord;

@SpringBootTest
public class EmployeeLeaveRecordTest {
	
	@Test
	public void testSetId()
	{
		EmployeeLeaveRecord eLR = new EmployeeLeaveRecord();
		eLR.setLeaveId(1L);
		eLR.setLeaveDate(LocalDate.now());
		assertEquals(1L, eLR.getLeaveId());	
		assertEquals(LocalDate.now(), eLR.getLeaveDate());
	}

}
