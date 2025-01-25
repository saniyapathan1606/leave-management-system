package team6.java.ca.serviceImpls;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import team6.java.ca.entities.Admin;
import team6.java.ca.entities.Employee;
import team6.java.ca.entities.EmployeeType;
import team6.java.ca.entities.User;
import team6.java.ca.repositories.UserRepository;

@Service
public class UserServiceImpl {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createAdmin(String username, String password) {
        String hashedPassword = passwordEncoder.encode(password);
        Admin admin = new Admin(username, hashedPassword);
        return userRepository.save(admin);
    }

    public User createEmployee(String username, String password, String firstName, String lastName, String email,
                               EmployeeType empType, LocalDate joinDate, Employee.EmpStatus empStatus,
                               Employee manager, boolean isManager) {
        String hashedPassword = passwordEncoder.encode(password);
        Employee employee = new Employee(username, hashedPassword, firstName, lastName, email, empType, joinDate,
                                         empStatus, manager, isManager);
        return userRepository.save(employee);
    }

    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Transactional
    public User updateUser(long userId, User user) {
        User existingUser = userRepository.findUserByUserId(userId);
        if (existingUser != null) {
            existingUser.setUsername(user.getUsername());

            // Hash the password before updating
            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
            }

            return userRepository.save(existingUser);
        }
        return null;
    }
}

