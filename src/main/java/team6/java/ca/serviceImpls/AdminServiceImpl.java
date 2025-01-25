package team6.java.ca.serviceImpls;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import team6.java.ca.entities.Admin;
import team6.java.ca.repositories.AdminRepository;
import team6.java.ca.services.AdminService;


@Service
@Transactional(readOnly = true)
public class AdminServiceImpl implements AdminService {
	@Autowired
	private AdminRepository adminRepo;
	
	@Autowired
	 private PasswordEncoder passwordEncoder;
	
	@Override
	public List<Admin> findAllAdmin() {
		return adminRepo.findAllAdmin();
	}

	@Override
	public Admin findAdminByUserId(long userId) {
		return adminRepo.findAdminByUserId(userId);
	}

	@Override
	public Admin findAdminByUsername(String username) {
		return adminRepo.findAdminByUsername(username);
	}
	
	@Override
    @Transactional
    public Admin createAdmin(String username, String password) {
        String hashedPassword = passwordEncoder.encode(password);
        Admin admin = new Admin(username, hashedPassword);
        return adminRepo.save(admin);
    }

    @Override
    @Transactional
    public Admin updateAdmin(long userId, Admin admin) {
        Admin existingAdmin = adminRepo.findAdminByUserId(userId);
        if (existingAdmin != null) {
            existingAdmin.setUsername(admin.getUsername());
            existingAdmin.setPassword(admin.getPassword());
            return adminRepo.save(existingAdmin);
        }
        return null;
    }

    @Override
    @Transactional
    public void deleteAdmin(long userId) {
        adminRepo.deleteById(userId);
    }

    @Override
    public boolean existsByUsername(String username) {
        return adminRepo.existsByUsername(username);
    }

    

}
