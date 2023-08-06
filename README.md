# Spring Batch
This Spring Batch project was built following along with [Modern batch processing with Spring Batch](https://paulosergio-jnr.medium.com/modern-batch-processing-with-spring-batch-195c97880460). The author's repository is [HERE](https://github.com/paulosergio-jnr/spring-batch-basics).

## Project Generation
This project was generated through Spring Initializr. In order to load it up in Spring Initializr, just click [HERE](https://start.spring.io/#!type=maven-project&language=java&platformVersion=2.7.14&packaging=jar&jvmVersion=1.8&groupId=com.wilterson.springbatch&artifactId=batch-basics&name=SpringBatchBasics&description=Modern%20batch%20processing%20with%20Spring%20Batch&packageName=com.wilterson.springbatch&dependencies=batch,lombok,h2).

## Project Stack
* Java 8 (not targeting the latest version on purpose)
* Lombok
* H2
* Spting Batch 4 (not targeting the latest version on purpose)

## Spring Batch Database Schema
Note that the `application.yml` sets the `spring.batch.initialize-schema` to `never`, which means that the Spring Batch DB tables will never be initialized automatically. In order to get them created I dropped in the project's resources directory the `schema.H2.sql` file, which gets called automatically by Spring Boot (_**Note: it works combined with `spring.sql.init.platform` attribute_). On a real application though, Spring Batch tables should be created by a DB version control framework, like Flyway or Liquibase. This would guarantee that the tables will be created only once. Finally, Spring Batch DB schema scripts per DB platform can be downloaded [HERE](
https://github.com/spring-projects/spring-batch/tree/main/spring-batch-core/src/main/resources/org/springframework/batch/core).

Documentation on Spring Batch Meta-Data Shema can be read [HERE](https://docs.spring.io/spring-batch/docs/current/reference/html/schema-appendix.html).