# Spring Boot and Keycloak Integration Documentation

## Overview

This documentation provides step-by-step instructions to set up and run the Spring Boot application integrated with Keycloak for authentication. Follow the steps below to get started.

## Prerequisites

- Java Development Kit (JDK) 11 or later
- Maven
- Docker and Docker Compose

## Steps to Run the Application

### 1. Clone the Project

Clone the repository to your local machine:
<br>
Copy
```bash
git clone https://github.com/DouglaNyabasa/springBoot-auth-with-keyClock-integration.git
```

### 2. Reload Maven Dependencies

Navigate to the project directory and reload Maven dependencies:

Copy
cd <your-project-directory>

```bash
mvn clean install
```
##

### 3. Start Keycloak and PostgreSQL Containers

Run the following command to start the Keycloak and PostgreSQL containers using Docker Compose:

Copy
```bash
docker-compose -f postgres-docker-compose.yml up
```

### 4. Access Keycloak

Open your web browser and go to the following URL to access the Keycloak admin console:

Copy
```bash
http://localhost:9082
```

### 5. Create a Realm

Log in to Keycloak using the admin credentials.
Click on Add Realm.
Enter the realm name as spring and save.

### 6. Create a Default User

Navigate to the Users section in the Keycloak admin console.
Click on Add User.
Fill in the user details:
<br>
Username: testuser@gmail.com
<br>
Email: testuser@gmail.com
<br>
First Name: Test
<br>
Last Name: User
<br>
Set the password or credentials:
Check the option to Temporary to remove the temporary status.
<br>
 Save the user.

 ### 7. Obtain Access Token
Log in to Keycloak to obtain an access token using the following endpoint:

Copy
```bash
http://localhost:9082/realms/spring/protocol/openid-connect/token
```
Request Parameters
<br>
client_id: spring-app
<br>
grant_type: password
<br>
username: testuser@gmail.com (or your chosen username)
<br>
password: yourpassword

### 8. Configure the Client

Navigate to the Clients section.
<br>
Click on Create.
<br>
Fill in the client details:
<br>
Client ID: spring-app
<br>
Access Type: confidential
<br>
Click on Save.
<br>
Configure the client:
<br>
Set Direct Access Grants Enabled to ON.
<br>
Navigate to the Service Accounts tab and set the Client Role:
<br>
Select admin-cli and assign manage-user permission.
<br>
Copy the Client Secret and paste it into the application.yml file in your Spring Boot application.



### 9. Test Endpoints
Now that you have the access token, you can test all the endpoints of your Spring Boot application using tools like Postman or cURL.
