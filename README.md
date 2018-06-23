# IP Black List Filtering API
I built this application as code challenge solution. 
## Development

I used following frameworks or major libraries for building this application:

1. [Spring Boot Version 2.0][]: I exposed two rest API using spring boot rest features
2. [Swagger]
3- [Hibernate validation]
4- [For the rest please refer to pom file]




 ## Running the project  
Just run the following command:
    ./mvnw
    
 the application would be available on 8080 port just go through this url: [http://localhost:8080/#/]

 ## Solution description

  I exposed two rest service for processing web page url:
   
    api/filterConfiguration  PUT,DELTE,GET  for manupulating black list
    api/filterConfiguration/{lookupIp}  GET for checking blacklist for lookupIp
    
input json sample :
{
	"fromIP": "1",
	"toIP":"192.9.200.16"
}
 
 ### Performance consideration

 I used parallelstream for concurrently looking for existing blackList Ip range entries.
 I prefered to convert IP into long when I want to look in the blacklist for 
 comparsion optimization .
 
 


