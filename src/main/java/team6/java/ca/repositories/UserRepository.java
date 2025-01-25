package team6.java.ca.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import team6.java.ca.entities.User;



public interface UserRepository extends JpaRepository<User, Long> {
	@Query("SELECT u FROM User u")
	List<User> findAllUsers();
	
	@Query("SELECT u FROM User u WHERE u.userId = :userId")
    User findUserByUserId(@Param("userId") Long userId);
	
	@Query("SELECT u FROM User u WHERE u.username = :username")
	User findUserByUsername(@Param("username") String username);
	
	//check whether that username exists or not
	@Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.username = :username")
    boolean existsByUsername(@Param("username") String username);
}

