package team6.java.ca.entities;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("admin")
public class Admin extends User {

	@Column(name = "userid") // Ensure this matches the column name in the 'Users' table
	private long userId;

	// constructors
	public Admin() {
		super();
	}

	public Admin(String username, String password) {
		super(username, password);
	}

	// getters and setters, toString

	@Override
	public String toString() {
		return "Admin [toString()=" + super.toString() + "]";
	}

}
