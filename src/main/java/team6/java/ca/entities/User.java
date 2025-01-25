package team6.java.ca.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="user_type")
@Table(name = "Users")  // Add the table annotation to the root class
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userid")
    private long userId;

    @Column(name = "username")
    @Size(min=2, max=20, message="Username must be 2-20 characters")
    private String username;

 

	@Column(name = "password")
	@Size(min=3, max=150, message="Password must be 6-30 characters")
    private String password;


    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    @Override
 	public String toString() {
 		return "User [userId=" + userId + ", username=" + username + ", password=" + password + "]";
 	}
  
}
