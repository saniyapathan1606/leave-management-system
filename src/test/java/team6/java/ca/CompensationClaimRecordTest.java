package team6.java.ca;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;

import team6.java.ca.entities.CompensationClaimRecord;


@SpringBootTest
public class CompensationClaimRecordTest {
	
	@Test
	public void testSetId()
	{
		CompensationClaimRecord ccr = new CompensationClaimRecord();
		ccr.setClaimId(0);
		assertEquals(0, ccr.getClaimId());
		
	}
}
