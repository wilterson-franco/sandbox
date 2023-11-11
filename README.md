# Overview
This application uses Spring Batch to move data from one DB table to another. The only processing it does, before moving the data, is changing the data to upper case. The DB access is implemented with JDBC template and reads 10 items at a time (configurable, of course). Items not processed are picked up on the next Job run.

This application has the web layer just to give me a chance to log into the H2 console to query the data. It is not a web application.

## Notes
- The `@EnableBatchProcessing` annotation activates the Spring Boot auto-configuration which in turn sets up all the Spring Batch infrastructure.
- Spring Boot will run the Job for us. It will look for any Job in the Spring application context when the Java application is stated and will just run them. You can explicitly kick them off, perhaps in response to an event, but the default behaviour with Spring Boot auto-configuration is to run the Job on application startup.

## Spring Initializr
https://start.spring.io/#!type=maven-project&language=java&platformVersion=3.1.5&packaging=jar&jvmVersion=17&groupId=com.wilterson&artifactId=first-spring-batch&name=first-spring-batch&description=Spring%20Batch%20first%20project&packageName=com.wilterson&dependencies=batch,h2,web,lombok,data-jpa

## TODO
- The data must be in a Postgres DB
- The batch job must be triggered by an event, say new data added to the source table


## References
- [Spring Batch Guid: Creating a Batch Service](https://spring.io/guides/gs/batch-processing/)
- [Josh Long YouTube Video](https://www.youtube.com/watch?v=x4nBNLoizOc)
- [Spring Batch Page](https://spring.io/batch)
- [Spring Batch Documentation](https://spring.io/projects/spring-batch#learn)
- [Spring Batch 5.0 Migration Guide](https://github.com/spring-projects/spring-batch/wiki/Spring-Batch-5.0-Migration-Guide)
- [Spring Batch Reference Documentation](https://docs.spring.io/spring-batch/docs/current/reference/html/)
- [Cursor-based ItemReader Implementations](https://docs.spring.io/spring-batch/docs/current/reference/html/readersAndWriters.html#cursorBasedItemReaders)
- [Spring Batch GitHub with lots of info and exemples](https://github.com/spring-projects/spring-batch/tree/main)