package team6.java.ca.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import team6.java.ca.entities.Admin;



public interface AdminRepository extends JpaRepository<Admin, Long>{
	@Query("SELECT a FROM Admin a")
	List<Admin> findAllAdmin();
	
	@Query("SELECT a FROM Admin a WHERE a.userId = :userId")
    Admin findAdminByUserId(@Param("userId") Long userId);
	
	@Query("SELECT a FROM Admin a WHERE a.username = :username")
    Admin findAdminByUsername(@Param("username") String username);
	
	@Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM Admin a WHERE a.username = :username")
    boolean existsByUsername(@Param("username") String username);
	
}