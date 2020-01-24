#  Spring security oauth2 cassandra example

## Prerequisites:
* Cassandra 3.9.0
* JDK 1.8 
* Maven 3.*

## Install and run the project 
1. download/clone the project 
2. Build the project using following maven command from project root folder where pom.xml file place.
  * `mvn clean package`
3. Create docker image from following command 
  * `docker build -t assignment_image .`
4. Run the docker-compose using the following command   
  * `docker-compose up -d`     
  
5. Let's check if our authentication endpoint is working
   * `curl -X POST http://localhost:8080/oauth/token -H 'authorization: Basic Y2ZmZTM5OTAtNmYwZS0xMWU4LWI3NTAtNGQ4NjE0Yzk0MGZmOnNlY3JldA==' -F grant_type=password -F username=ahmed -F password=secret -F scope=trust`
  
