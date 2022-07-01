# ZTE_910_modem_service
Java service that uses ZTE 910 modem as service for sending SMS messages

## Application configuration:

### ENV Variables
1. PORT - spring application port, by default - 8080
2. SCHEDULER_CRONE - job time to execute for standalone running (override by CONFIG_SERVER_URI)
3. ZIPKIN_PORT - URL to Zipkin distributed tracing system, by default - http://localhost
4. CONFIG_SERVER_URI - URL to configuration properties to spring configuration server
###   ZTE 910 modem Variables
5. PWD
6. URL_GET
7. URL_POST
8. HEADER_ORIGIN
9. HEADER_REFERER

#### Postman
* postman collection for test API: /info/GSM APi.postman_collection.json

## Application info/documentation:

#### Spring boot:
* [How to Start and Stop Scheduler in Spring Boot](https://www.yawintutor.com/how-to-start-and-stop-scheduler-in-spring-boot/)
* [Running Scheduled Jobs in Spring Boot](https://reflectoring.io/spring-scheduler/)

#### Spring Zipkin:
* [Spring Cloud Sleuth](https://medium.com/@kirill.sereda/spring-cloud-sleuth-zipkin-%D0%BF%D0%BE-%D1%80%D1%83%D1%81%D1%81%D0%BA%D0%B8-9f8504581dae)

#### Spring Boot Actuator:
* [How to Enable All Endpoints](https://www.baeldung.com/spring-boot-actuator-enable-endpoints)
* [Web API Documentation](https://docs.spring.io/spring-boot/docs/current/actuator-api/htmlsingle/)

#### Spring Boot Client Config
1. add new libraries to gradle build file
```
implementation 'org.springframework.cloud:spring-cloud-starter-config'
implementation 'org.springframework.cloud:spring-cloud-starter-bootstrap'
```