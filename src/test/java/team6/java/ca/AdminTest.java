package team6.java.ca;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;

import team6.java.ca.entities.Admin;


@SpringBootTest
public class AdminTest {
	
	
	@Test
	public void testGetName()
	{
		Admin admin1 = new Admin();
		admin1.setUsername("admin1");
		
		//Get admin name
		String adminName = admin1.getUsername();
		assertEquals("admin1", adminName);
	}
	
	@Test
	public void testSetName()
	{
		Admin admin2 = new Admin();
		admin2.setUsername("admin2");
		assertEquals("admin2", admin2.getUsername());
	}
	
	

}
