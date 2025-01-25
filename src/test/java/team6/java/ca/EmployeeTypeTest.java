package team6.java.ca;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import team6.java.ca.entities.EmployeeType;

@SpringBootTest
public class EmployeeTypeTest {
	
	@Test
	public void setTestEmployeeType()
	{
		EmployeeType emp = new EmployeeType();
		emp.setEmpTypeName("Administrative");
		assertEquals("Administrative", emp.getEmpTypeName());
	}
	
	@Test
	public void getTestEmplyoeeTest()
	{
		EmployeeType emp = new EmployeeType("Professional");
		assertThat(emp.getEmpTypeName().equals("Professional"));
		
	}
}
