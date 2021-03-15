# jersey-docker-demo
Shows how to run Java RESTful API (based on Jersey framework) as a Docker container.

You may compile and test the RESTful service with the following commands:
		mvn compile
		mvn -Dmaven.test.skip=true install
		mvn exec:java
		http://localhost:8080/myapp/myresource
		http://localhost:8080/myapp/application.wadl		


To Dockerize your app, follow these commands:
	1. docker build --tag=myapp .
	2. docker run -p 18080:8080 -t -i myapp
	3. curl http://localhost:18080/myapp/myresource

Contains code from [How to Dockerize Java RESTful API Application](https://medium.com/dekses/how-to-dockerize-java-restful-api-application-7aed70ef3c3a) tutorial.
