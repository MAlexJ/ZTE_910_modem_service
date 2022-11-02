# ZTE_910_modem_service

Java Web service that uses ZTE 910 modem as service for sending SMS messages

## Application configuration:

## ENV Variables

### Required:

1. ADMIN_PHONE_NUMBER - admin phone number for notification

### Optional:

1. PORT - spring application port, by default - 8080
2. SCHEDULER_CRONE - job time to execute for standalone running (override by CONFIG_SERVER_URI)
3. ZIPKIN_PORT - URL to Zipkin distributed tracing system, by default - http://localhost
4. CONFIG_SERVER_URI - URL to configuration properties to spring configuration server

### ZTE 910 modem Variables

5. PWD
6. URL_GET
7. URL_POST
8. HEADER_ORIGIN
9. HEADER_REFERER

### How to manually test ZTE 910 modem:

##### LOGIN

```
curl "http://192.168.32.1/goform/goform_set_cmd_process"
-H "Connection: keep-alive"
-H "Content-Type: application/x-www-form-urlencoded; charset=UTF-8"
-H "Origin: http://192.168.32.1"
-H "Referer: http://192.168.32.1/index.html"
--data-raw "isTest=false&goformId=LOGIN&password=YWRtaW4%3D"
--insecure
-c cookie.txt
-o file.txt
```

##### SEND SMS

```
curl "http://192.168.32.1/goform/goform_set_cmd_process"
-H "Accept: application/json, text/javascript, */*; q=0.01"
-H "Accept-Language: en-US,en;q=0.9,ru;q=0.8"
-H "Connection: keep-alive"
-H "Content-Type: application/x-www-form-urlencoded; charset=UTF-8"
-H "Cookie: stok=078DBC4F4A38DD7F3BB43BC2"
-H "Origin: http://192.168.32.1"
-H "Referer: http://192.168.32.1/index.html"
-H "X-Requested-With: XMLHttpRequest"
--data-raw "isTest=false&goformId=SEND_SMS&notCallback=true&Number=80672687484&MessageBody=00680065006C006C006F0021&ID=-1&encode_type=GSM7_default"
--insecure
```

#### CHECK BATTERY

```
curl "http://192.168.32.1/goform/goform_get_cmd_process?multi_data=1&isTest=false&cmd=battery_charging,battery_vol_percent,battery_pers" -H "Referer: http://192.168.32.1/index.html"
```

### Postman

* postman collection for test API: /info/GSM APi.postman_collection.json

### Swagger UI

* documentation: https://springdoc.org/ <br>
* how to use: The Swagger UI page will then be available at http://server:port/context-path/swagger-ui.html

## Application info/documentation:

### Spring boot:

* [How to Start and Stop Scheduler in Spring Boot](https://www.yawintutor.com/how-to-start-and-stop-scheduler-in-spring-boot/)
* [Running Scheduled Jobs in Spring Boot](https://reflectoring.io/spring-scheduler/)

### Spring Zipkin:

* [Spring Cloud Sleuth](https://medium.com/@kirill.sereda/spring-cloud-sleuth-zipkin-%D0%BF%D0%BE-%D1%80%D1%83%D1%81%D1%81%D0%BA%D0%B8-9f8504581dae)

### Spring Boot Actuator:

* [How to Enable All Endpoints](https://www.baeldung.com/spring-boot-actuator-enable-endpoints)
* [Web API Documentation](https://docs.spring.io/spring-boot/docs/current/actuator-api/htmlsingle/)

### Spring Boot Client Config

1. Add new libraries to gradle build file

```
implementation 'org.springframework.cloud:spring-cloud-starter-config'
implementation 'org.springframework.cloud:spring-cloud-starter-bootstrap'
```