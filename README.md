# GSS Challenge application

The green spectrum insurance uses this application for managing their customers.

## Setting up and starting the application

* The easiest way is starting using Maven (this must be installed first) and the following command:
`mvn spring-boot:run`
* Furthermore, the application could be built using Maven with the command `mvn clean install` and then run
  via `java -jar target/challenge-0.0.1-SNAPSHOT.jar` 

## Using the application
* The application starts a server on <localhost:8080> and configures several REST endpoints under the URI
  <localhost:8080/api/customers>.
* The application also contains further documentation for the REST endpoints using OpenAPI and how to use them, as well as example implementations using Swagger.  
You can find these under the URL <http://localhost:8080/swagger-ui.html>.
