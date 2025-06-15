City Counter App

This is a full-stack application that allows users to enter a letter and returns the number of cities beginning with that letter. It fetches city data from the OpenWeatherMap sample API.

Features

Java 17 Spring Boot backend

React frontend

Fetches city data from OpenWeatherMap

Requirements

Java 17+

Node.js and npm

axios

Maven (for building the backend)

Project Structure
citycounter/
├── citycounter-ui/            # Spring Boot project
│   └── src/main/...    	   # Backend source code
├── citycounter-backend/       # React project
│   └── src/...         # Frontend source code
└── README.md

Setup Instructions

1. Clone the repository

git clone https://github.com/your-username/city-counter-app.git
cd city-counter-app

2. Backend Setup (Java Spring Boot)

cd backend
mvn clean install
mvn spring-boot:run

The backend server will start at: http://localhost:8080

3. Frontend Setup (React)

cd ../frontend
npm install
npm start

The React app will start at: http://localhost:3000

4. Using the App

Enter a single letter (e.g., y or z) into the input field.

Click the "Get City Count" button.

The number of cities starting with that letter will be displayed.

Example Queries

y → 1 (Only Yafran)

z → 3 (Zuwarah, Zawiya, Zlitan)

Notes

The city data is fetched from the OpenWeatherMap sample API.




