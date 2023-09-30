# Spring Data JPA Tuning
This application's goal is to show how a simple Spring JPA application can be optimized for the best performance. I am not following any specific tutorial or website, but the following Medium article has provided a lot of insights:

```
https://medium.com/@avi.singh.iit01/optimizing-performance-with-spring-data-jpa-85583362cf3a
```

# DataStore
This project uses H2 Memory DB, which can be accessed under:

```
http://localhost:8080/h2-console/
```

Use the following JDBC URL:

```
jdbc:h2:mem:testdb
```

# Project Generation
This project was generated using Spring Initializr. The following link can be used to re-generate it:
```
https://start.spring.io/#!type=maven-project&language=java&platformVersion=3.1.4&packaging=jar&jvmVersion=17&groupId=com.wilterson&artifactId=springjpa-tuning&name=springjpa-tuning&description=Spring%20Data%20JPA%20Tuning&packageName=com.wilterson&dependencies=data-jpa,h2,lombok,web,actuator,devtools
```