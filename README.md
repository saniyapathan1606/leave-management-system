# Leave Application Processing System

## Overview

The Leave Application Processing System (LAPS) is a robust Java-based solution designed to streamline the management of employee leave requests, public holidays, compensation claims, and related processes. Built as a university project, it leverages Spring Boot and Hibernate to deliver a secure, scalable, and user-friendly experience.

## Key Features

- **Public Holiday Management**: Manage public holidays with ease.
- **Employee Management**: Handle employee details and hierarchy, including managers and subordinates.
- **Leave Entitlements**: Configure leave entitlements based on employee type and role.
- **Compensation Claims**: Track and manage employee compensation claims.
- **Leave Records**: Maintain a detailed history of employee leave requests and approvals.
- **Authentication**: Secure user authentication powered by Spring Security.
- **Pre-populated Data**: Initialize application with predefined public holidays, employees, leave types, and entitlements.
- **Testing Framework**: Includes JUnit-based test cases for data initialization and application logic.

## Technologies Used

- **Java 17**
- **Spring Boot 3.3.0**
  - Spring Data JPA
  - Spring Security
  - Spring Web
  - Thymeleaf: Templating engine
- **Hibernate ORM 6.5.2**: For database interactions.
- **HikariCP**: Connection pooling for efficient database access.
- **JUnit 5.10.2**: Unit testing framework.
- **MySQL**: Relational database management system.
- **Maven**: Dependency management and build tool.

## Setup and Usage

### Prerequisites

- **Java 17** installed.
- **Maven** installed.
- **MySQL** database configured.

### Steps to Run

1. Clone the repository:

   ```bash
   git clone <repository_url>
   cd team6_java_ca_final
   ```

2. Configure the database: Update `application.properties` with your MySQL database credentials:

   ```properties
   spring.datasource.url=jdbc:mysql://<host>:<port>/<database>
   spring.datasource.username=<username>
   spring.datasource.password=<password>
   ```

3. Build the project:

   ```bash
   mvn clean install
   ```
4.	Populate testing data: Run the DataInitializationTest class to populate the database with testing data. You can do this by running the test in     your IDE (e.g., IntelliJ IDEA) or using Maven from the command line:

    ```bash
    mvn test -Dtest=DataInitializationTest
    ```
5. Run the application:

   ```bash
   mvn spring-boot:run
   ```

6. Access the application: Open your browser and navigate to `http://localhost:8080`.

## Testing

Run the test cases using JUnit to ensure all features work as expected:

```bash
mvn test
```




