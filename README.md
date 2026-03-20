 # Fitness Tracker API

Backend REST API for tracking workouts and runs with authentication, built using Spring Boot and secured with JWT.

 ## Features

User registration & authentication (JWT-based)

Track workouts and runs

Add exercises to workouts

Automatic calorie calculation:

 Runs → based on distance & pace

 Workouts → sum of exercise calories

Update & delete data

Fully tested controllers using MockMvc

## Tech Stack

Java 24

Spring Boot

Spring Security

JWT Authentication

Maven

PostgreSQL

MockMvc (testing)

Authentication

This API uses JWT (JSON Web Token).

## Endpoints:

POST /auth/register — register new user

POST /auth/login — login and receive JWT

 Include token in requests:

Authorization: Bearer <your_token>
 API Endpoints
 ### Users

GET /users/{username} — get user by username

GET /users — get all users

PATCH /users — update current user

### Workouts
POST /users/workouts — create workout

GET /users/workouts/{id} — get workout by id

GET /users/workouts — get all user workouts

PATCH /users/workouts/{id} — update workout

DELETE /users/workouts/{id} — delete workout

### Exercises (inside workout)

POST /users/workouts/{workoutId}/exercises

GET /users/workouts/{workoutId}/exercises/{id}

GET /users/workouts/{workoutId}/exercises

PATCH /users/workouts/{workoutId}/exercises/{id}

DELETE /users/workouts/{workoutId}/exercises/{id}
### Runs

POST /users/runs — create run

GET /users/runs/{id} — get run by id

GET /users/runs — get all runs

PATCH /users/runs/{id} — update run

DELETE /users/runs/{id} — delete run

### Business Logic
Calories Calculation

Run calories

Based on:

distance (km)

average pace

Workout calories

Sum of all exercise calories inside workout

### How to Run (Without Docker)
1. Clone repository
   git clone <your-repo-url>
   cd fitness-tracker
2. Configure database

Create PostgreSQL database and update:

```
spring.datasource.url=jdbc:postgresql://localhost:5432/fitness_tracker
spring.datasource.username=your_username
spring.datasource.password=your_password
```
3. Run application
``` 
mvn spring-boot:run
```
   

 ###  How to Run (With Docker)
   (You’ll add Docker later — this section is ready for you)
1. Build and run
``` 
docker-compose up --build
```
2. App will be available at:
```  
http://localhost:8080

```

### Author
Anastasiia Karpishenko