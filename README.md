# Auth Email Verification App

This project is a full-stack application designed to demonstrate a user authentication and account activation system. It includes a frontend built with HTML, JavaScript, and CSS, and a backend powered by Java and Spring Boot. The application allows users to sign up, receive an activation email, and activate their account.

## Features

- **User Registration**: Users can sign up by providing their name, email, phone number, password, and role.
- **Account Activation**: After registration, an activation email is sent to the user's email address with a unique token. The user must click on the link in the email to activate their account.
- **Login**: Users can log in after activating their account. The application checks the credentials and the account status.
- **Resend Activation Link**: Users who haven't activated their account can request a new activation link.

## Technologies Used

### Frontend:
- **HTML/CSS**: For structuring and styling the web pages.
- **JavaScript**: For handling form submissions and managing user interactions.

### Backend:
- **Java**: The primary language for the backend logic.
- **Spring Boot**: Used for creating RESTful web services.
- **Jakarta Mail**: For sending emails to users.
- **H2 Database**: An in-memory database for development and testing.

## Project Structure

- `src/main/java/com/mako/authApp`: Contains the Java source code for the backend, including controllers, services, and entities.
- `src/main/resources/templates`: Holds the HTML files used for the user interface.
- `static/css`: Contains CSS files for styling the frontend.
- `static/js`: Contains JavaScript files for frontend functionality.

## Setup Instructions

### 1. Clone the repository:
   git clone https://github.com/Makondelela/auth-email-verification.git
   cd auth-email-verification
### 2. Setup email and Database
    Navigate to applications.properties
    replace yourEmailAdress with Email address
    replace yourPassword with actual app-specific password for Gmail[to do this you should first generate an app specific password
                                                                      in your gmail portal under 2 step authentication]
    Update the database username, password, and database name with your actual credentials.
### 3. Backend Setup:
   - Ensure you have Java gradle installed.
   - Run the application using gradle:
   ./gradlew bootRun
### 3. Frontend Setup:
   -Open http://localhost:8080/index.html in your preferred browser.
   -The application should be accessible through the frontend interface.

### API Endpoints:
   POST /signup: Registers a new user and sends an activation email.
   GET /activate: Activates the user's account based on the token provided.
   POST /request-new-activation: Resends the activation link if the account is not yet activated.
   POST /login: Authenticates the user.

