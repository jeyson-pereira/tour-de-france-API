# Tour de France API-REST
This is a [Spring Boot](https://spring.io/guides/gs/spring-boot/) application built using Maven. You can build a jar file and run it from the command line or using IDE like [IntelliJ IDEA](https://www.jetbrains.com/idea/)

### Context
One of the most important cycling events in the world is the Tour de France. As part of the technology team that supports the competition, you have been entrusted with the task of developing an application or service that allows the registration of the teams and their respective cyclists.

## Pre-requisites
* JAVA 11 should be installed
* This project uses [MongoDB](https://github.com/r2dbc/r2dbc-postgresql) please change the environment variables on `src/main/resources/application.properties`
  ![environment-variables](https://user-images.githubusercontent.com/64755135/179321180-8502f2e4-2239-44fe-8f19-1933b4527575.png)

## How to run
* Clone this repository on your directory...

* If you want use Maven command put it in a terminal in window (in the complete) directory:
```bash
./mvnw spring-boot:run
```

* otherwise use your IDE...

----

The server will start at <http://localhost:8080/api/v1/>

And the docs path is <http://localhost:8080/api/v1/api-docs/>

## API-docs
You can also use the Swagger-UI to test the application
![api-docs](https://user-images.githubusercontent.com/64755135/179320918-9594a5a5-c2f2-47e6-9d13-9945f20808d8.png)
```
1. GET /teams - Get All teams

2. POST /teams - To create a team

3. PUT /teams/{id} - Update a team

4. DELETE /teams/{id} - Delete a team by Id

5. GET /teams/country/{country} - Retrieve teams by country

6. GET /teams/{teamCode} - Retrieve a team by team code
```
```
1. GET /teams - Get All teams

2. POST /teams - To create a team

3. PUT /teams/{id} - Update a team

4. DELETE /teams/{id} - Delete a team by Id

5. GET /teams/country/{country} - Retrieve teams by country

6. GET /teams/{teamCode} - Retrieve a team by team code
```