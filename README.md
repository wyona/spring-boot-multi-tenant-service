# Spring Boot based RESTful multi-tenant service webapp

## Requirements

JDK: 1.8 or higher
Maven 3.3.3 or higher

## Run from Command Line

Configure src/main/resources/application.properties
Build webapp as war, run: 'sh build.sh'
java -jar target/multi-tenant-service-webapp-0.0.1-SNAPSHOT.war
Request in browser: http://localhost:5080 or https://localhost:5443 (see SSL properties inside src/main/resources/application.properties)
