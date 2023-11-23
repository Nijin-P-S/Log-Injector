
#      LOG INGECTOR

## Objective

Develop a log ingestor system that can efficiently handle vast volumes of log data, and offer a simple interface for querying this data using full-text search or specific field filters.

## Tech Stack Used
- Java with SpringBoot (For Backend)
- RabbitMQ (For asynchronous writing to DB)
- HTML, CSS, JS (For Frontend)
- MongoDB as database

## System Architecture

![Logo](https://i.postimg.cc/Z5BBKy67/System-Design.png)

## Input JSON Format
![InputJSON](https://i.postimg.cc/DwcbJK8H/Screenshot-2023-11-19-at-8-16-33-PM.png)

## Functionalities
- Scalable enough to handle high volumes of logs efficiently(Could handle 1000s of logEntry in a second, tested using Jmeter).
- Can query the logs using filters
  - level
  - message
  - resourceId
  - timestamp
  - traceId
  - spanId
  - commit
  - metadata.parentResourceId
- Also can query using normal text in specific format, examples are:
  * Find all logs with the level set to "error"
  * Search for logs with the message containing the term "Failed to connect".
  * Retrieve all logs related to resourceId "server-1234".
  * Filter logs between the timestamp "2023-09-10T00:00:00Z" and "2023-09-15T23:59:59Z"."



## HOW TO SETUP IN YOUR LOCAL SYSTEM

### Step 1 : Clone the git repo 
- Make sure you have git installed in your local system
- After that run the command "git clone https://github.com/dyte-submissions/november-2023-hiring-Nijin-P-S.git"
![GitClone](https://i.postimg.cc/Qd6ptW4v/Git-clone.png)

### Step 2 : Now open the folder you cloned, and go to the backend folder
- You can navigate to backend folder using "cd ./backend"
- Check the contents inside the folder using "ls" command
![CDCommand](https://i.postimg.cc/tRtnJx7D/ray-so-export-1.png)

### Step 3 : Run the docker compose file
- Inside the backend folder, you will see "docker-compose.yml" file
- Make sure you have docker installed, and docker daemon is up and running
- Now from the terminal, run the command "docker compose up -d" to start the mongodb and rabbitmq in detached
![DockerCompose](https://i.postimg.cc/mDVRfB23/ray-so-export.png)

### Step 4 : Starting up the microservices
- Using any of the IDE (IntelliJ prefered), start the EurekaServerApplication.java, then LogApplication.java, and then DbApplication.java
- Now the backend server is up and running

### Step 5 : Running the frontend
- Navigate to the frontend folder inside the root folder
- Open the "index.html" file through browser
- Then you will be getting an front end page like the one below
![FrontPage](https://i.postimg.cc/DwQMY5zL/Screenshot-2023-11-19-at-8-50-11-PM.png)

- Now you can insert logs, search logs through filters and also even you can search through simple text sentences, how cool is that!!!.



## Next Steps
- Can make the frontend application be developed using some well known frameworks
- Can include the security features including authentication and authorization using Spring Security in the backend
- Should think of an approach to remove the Eureka server in between the microservices, as the microservices are dependent on Eureka server for communication, and so if Eureka server goes down, the communication and system goes down.


## Demonstration of how to setup and working of Application


https://github.com/dyte-submissions/november-2023-hiring-Nijin-P-S/assets/101330853/6030beda-587b-4b8b-be9a-d18b3ce815bd






