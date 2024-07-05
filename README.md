# AppFireDemoApplication

A Java Spring Boot application that serves the purpose of interacting with the Jira REST API to fetch issues of specific types that have been updated since the start of the week.

## Features

Use Jira REST API to obtain data with the query
```
issuetype in (Bug, Documentation, Enhancement) and updated > startOfWeek()
```

## Prerequsites / Libraries

* Java JDK (used 22)
* Maven installed
* Postman
* Jira API credentials (demo account provided application.properties)
* Unirest java
* Lombok

## Installation

* Import the project in IntelliJ (as a Maven project)
* Use Maven Menu to do 'Clean', 'Install'
* Navigate to the AppFireDemoApplication class

## Run
* Run Application
* Open API platform (Postman)
* Run GET request on the running server
```
http://localhost:8080/jira/issues/start-of-week?issueTypes=BUG&issueTypes=Story&projectKey=KAN&format=json
```

<img width="1295" alt="image" src="https://github.com/lachezarSamanliev/appFireDemo/assets/58422300/4ce07dab-7444-4c3f-8392-c4a4af43e653">
