## City Counter App

This is a full-stack application that allows users to enter a letter and returns the number of cities beginning with that letter. It fetches city data from the OpenWeatherMap sample API.

Features

 * Java 17 Spring Boot backend

 * React frontend

 * Fetches city data from OpenWeatherMap

 * Requirements

 * Java 17+

 * Node.js and npm

 * axios

Maven (for building the backend)

Project Structure
```
citycounter/   
    citycounter-ui/          # Spring Boot project  
        src/main/...         # Backend source code  
    citycounter-backend/     # React project   
        src/...              # Frontend source code  
    README.md  
```
Setup Instructions

1. Clone the repository
git clone https://github.com/ysivaji27/citycounter.git

2. Backend Setup (Java Spring Boot)<br>
cd citycounter-backend <br>
mvn clean install <br>
mvn spring-boot:run <br>

    The backend server will start at: http://localhost:8080

3. Frontend Setup (React) <br>
cd ../citycounter-ui <br>
npm install <br>
npm start <br>

   The React app will start at: http://localhost:3000

5. Using the App

    Enter a single letter (e.g., y or z) into the input field. <br>

    Click the "Get City Count" button. <br>

    The number of cities starting with that letter will be displayed. <br>

    Example Queries <br>

    y → 1 (Only Yafran) <br>

    z → 3 (Zuwarah, Zawiya, Zlitan) <br>

Notes:- <br>
    The city data is fetched from the OpenWeatherMap sample API. <br>

API Reference URLs 
  * API DOCS URL : http://localhost:8080/v3/api-docs<br>
  * Swagger URL : http://localhost:8080/swagger-ui/index.html<br>

## Sample UI and postman screenshots for reference 

<img width="1290" alt="Screenshot 2025-06-15 at 19 36 34" src="https://github.com/user-attachments/assets/84fdfa5b-6ed1-4daa-bfe6-38c8532c3ff6" />

<img width="1290" alt="Screenshot 2025-06-15 at 19 34 43" src="https://github.com/user-attachments/assets/f2badbc0-40e3-4959-83dc-4002fbb52d2f" />





