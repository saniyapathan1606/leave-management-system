package team6.java.ca;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import team6.java.ca.entities.Admin;
import team6.java.ca.repositories.AdminRepository;




@SpringBootTest
public class AdminRepositoryTest {
	@Autowired
	private AdminRepository adminRepository;
	
	@Test
	public void testNewAdmin() {
		Admin a1 = new Admin("admin1", "admin123");
		adminRepository.save(a1);
		
		assertThat(a1.getUsername()).isEqualTo("admin1");
	}
	
	//adding more admin and test all remaining admin repo methods
	@Test
	public void testAll()
	{
		//first delete all
		adminRepository.deleteAll();
		
		Admin a1 = new Admin("admin1", "admin123");
		adminRepository.save(a1);
		
		Admin a2 = new Admin("admin2", "admin123");
		adminRepository.save(a2);
		Admin a3 = new Admin("admin3", "admin123");
		
		adminRepository.save(a3);
		Admin a4 = new Admin("admin4", "admin123");
		
		adminRepository.save(a4);
		
		
		
		//test findAdminById
		assertEquals("admin2", adminRepository.findAdminByUserId(2L).getUsername());
		
	}
	
	
}
