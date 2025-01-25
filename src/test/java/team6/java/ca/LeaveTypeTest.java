package team6.java.ca;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import team6.java.ca.entities.LeaveType;

@SpringBootTest
public class LeaveTypeTest {
	
	@Test
	public void setLeaveTypeTest() {
		LeaveType ltt = new LeaveType();
		ltt.setLeaveTypeName("maternal leave");
		assertThat(ltt.getLeaveTypeName().equals("maternal leave"));
	}

}
