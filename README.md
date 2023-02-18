# Spring 6 HTTP Clients

This project shows how to use the new Spring 6 HTTP Clients feature to call an endpoint. Spring creates a proxy from the provided Interface, annotated with `@HttpExchange` or its metadata annotations `@GetExchange`, `@PostExchange`, `@PutExchange`, or `@DeleteExchange`.

The implementation is tested through JUnits, where a mocked service is stood up to respond to the client requests.

The inspiration for this project came from the article [HTTP Interface in Spring 6](https://www.baeldung.com/spring-6-http-interface). The article's GitHub is [https://github.com/eugenp/tutorials/tree/master/spring-core-6](https://github.com/eugenp/tutorials/tree/master/spring-core-6).

## Related References
Olga Masciaszek-Sharma, from Spring Team, also talk about Spring 6 HTTP Clients on these two YouTube videos, but she goes beyond and explain why Spring Cloud Feign is not recommended anymore:

* [Declarative Clients in Spring](https://www.youtube.com/watch?v=3NcmlrumSOc&t=1884s)
* [Interface Clients in Spring](https://www.youtube.com/watch?v=lsrJu1Ul_fE&t=1656s)

#### Spring Cloud OpenFeign Issues
* Issues with reusing client interfaces as controllers
  * @RequestMapping at class level user issues (GH-547, GH-678)
  * CVE-2021-22044
* Maintenance issue caused by dependence on third party upstream project
* Lack of non-blocking support

## Project's Stack Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.0.2/maven-plugin/reference/html/)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/3.0.2/reference/htmlsingle/#using.devtools)
* [Spring Reactive Web](https://docs.spring.io/spring-boot/docs/3.0.2/reference/htmlsingle/#web.reactive)
* [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/3.0.2/reference/htmlsingle/#actuator)
