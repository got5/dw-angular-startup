dw-angular-startup
==================

Usage 
-----

Skip the tests for now as they are broken.

	mvn clean install -DskipTests

	java -jar service/target/service-1.0.0-SNAPSHOT.jar server todo.sample.yml 

An instance of Jetty will start on port 8080.

The api is accessible from /api and the web app is at /index.html (The redirection is also broken)


Front Developpement
-------------------

The code of the web project is located at service/src/main/web/  

There, you need to configure and download some dependencies and tools.

	npm install
	bower install

Ok, now just launch the server with : 

	grunt server

and your application start on port 9000 with a nodejs instance. 

With this configuration, you don't need to bother with live reloading in the java environnement. Indeed, there is a proxy configured to redirect all requests from localhost:9000/api to localhost:8080/api allowing you hit the Dropwizard REST API without further configuration.

Package the application
-----------------------

In the service/src/main/web/ directory, do a : 

	grunt build -f

Package your application like described in Usage section and you are good to go.
