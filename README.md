## Prerequisites:
* Cassandra
* JDK 1.8 
* Maven 3.*

## Install and run the project 
1. download/clone the project 
2. change to the root folder of the project and excute the following maven command 
  * `mvn spring-boot:run`
  * now the REST api is up and running with tomcat 8 on `localhost:9292`
     
     
3. Let's check if our authentication endpoint is working
   * `curl -X POST http://localhost:9292/oauth/token -H 'authorization: Basic Y2ZmZTM5OTAtNmYwZS0xMWU4LWI3NTAtNGQ4NjE0Yzk0MGZmOnNlY3JldA==' -F grant_type=password -F username=ahmed -F password=secret -F scope=trust`
