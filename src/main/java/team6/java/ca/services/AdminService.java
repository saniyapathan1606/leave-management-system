package team6.java.ca.services;

import java.util.List;

import team6.java.ca.entities.Admin;



public interface AdminService {
	List<Admin> findAllAdmin();
	Admin findAdminByUserId(long userId);
	Admin findAdminByUsername(String username);
	
	Admin createAdmin(String username, String password);
    Admin updateAdmin(long userId, Admin admin);
    void deleteAdmin(long userId);
    boolean existsByUsername(String username);
    

}
