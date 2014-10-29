scratch-spring-rest
==============

A very simple webapp that can be used to quickly try out code within a Java Servlet 3.0 web container.

#### Build

To build the application and run the unit tests use the following command.

    mvn clean verify

To run the Cucumber tests against the application run the following.

    mvn clean verify -P cucumber

*NOTE:* The cucumber tests are help within an external dependency that must be installed into your local maven
repository. This project can be found [here](https://github.com/karlbennett/scratch-cucumber-rest).

#### Run

The webapp can be run with the following command (Note: "`tomcat7`" this is required for async support):

    mvn tomcat7:run

This will start the server which can be accessed at [http://localhost:8080/scratch/spring/](http://localhost:8080/scratch/spring/ "scratch-spring-webapp")

It is also possible to carry out CRUD operation on simple users:

###### Create
    $ curl -XPOST -H "Accept:application/json" -H "Content-Type:application/json" http://localhost:8080/scratch/spring/users -d '{
        "email": "some.one@there.com",
        "firstName": "Some",
        "lastName": "One"
    }'

###### Retrieve
    $ curl -XGET -H "Accept:application/json" http://localhost:8080/scratch/spring/users
    $ curl -XGET -H "Accept:application/json" http://localhost:8080/scratch/spring/users/1

###### Update
    $ curl -XPUT -H "Content-Type:application/json" http://localhost:8080/scratch/spring/users/1 -d '{
        "email": "some.one@there.com",
        "firstName": "Some",
        "lastName": "Two"
    }'

###### Delete
    $ curl -XDELETE -H "Accept:application/json" http://localhost:8080/scratch/spring/users/1


The  webapp only contains six classes:

The controller class that handles the `/scratch/spring/` request mapping.

[`scratch.spring.webapp.controller.ScratchController`](https://github.com/karlbennett/scratch-spring-webapp/blob/master/src/main/java/scratch/spring/webapp/controller/ScratchController.java "ScratchController")

The controller class that handles the `/scratch/spring/users` and `/scratch/spring/users/{id}` request mappings.

[`scratch.spring.webapp.controller.UserController`](https://github.com/karlbennett/scratch-spring-webapp/blob/master/src/main/java/scratch/spring/webapp/controller/UserController.java "UserController")

The the configuration class that configures Spring MVC and Spring Data.

[`scratch.spring.webapp.config.ScratchConfiguration`](https://github.com/karlbennett/scratch-spring-webapp/blob/master/src/main/java/scratch/spring/webapp/config/ScratchConfiguration.java "ScratchConfiguration")
    
The the domain class that can be persisted into an in memory database using the CRUD endpoints.

[`scratch.spring.webapp.data.User`](https://github.com/karlbennett/scratch-spring-webapp/blob/master/src/main/java/scratch/spring/webapp/data/User.java "User")

The repository class that is used to persisted the User class.

[`scratch.spring.webapp.data.UserRepository`](https://github.com/karlbennett/scratch-spring-webapp/blob/master/src/main/java/scratch/spring/webapp/data/UserRepository.java "UserRepository")

And lastly the servlet class that will be automatically loaded by the servlet container that the webapp is deployed to.

[`scratch.spring.webapp.servlet.ScratchDispatcherServlet`](https://github.com/karlbennett/scratch-spring-webapp/blob/master/src/main/java/scratch/spring/webapp/servlet/ScratchDispatcherServlet.java "ScratchDispatcherServlet")

There are also two configuration files:

The maven [`pom.xml`](https://github.com/karlbennett/scratch-spring-webapp/blob/master/pom.xml "pom.xml") file, this contains the Jetty plugin configuration and the dependencies for the project.

The [`log4j.xml`](https://github.com/karlbennett/scratch-spring-webapp/blob/master/src/main/resources/log4j.xml "log4j.xml") file that defines the log levels for the webapp and it's libraries. It's currently set to INFO.

That is the entire project, have fun :)
