# Spring Cloud OpenFeign

This project just scratches the surface on how to use the Spring Cloud OpenFeign project to send HTTP requests to another service. It encapsulates the RestTemplate usage, providing a proxy that can be injected into the application. (Check [Project Action Points](#project-action-points) section for gaps in this project).

This application leverages the [JSON Placeholder](https://jsonplaceholder.typicode.com/) service as the server service. At the moment, this application does not provide JUnits for testing its functionality.

In this project I followed the article [Introduction to Spring Cloud OpenFeign](https://www.baeldung.com/spring-cloud-openfeign), but it is too superficial and doesn't even show the OpenFeign client interface being injected for usage.

## Project Action Points
* Implement JUnits for testing this project. Stand up a mocked service to respond to the client requests. As a reference, have a look at this very same repository's branch `spring/web-clients/http-interface` for how to stand up a mocked service;
* I didn't find a way to check whether the Hystrix integration is working. All scenarios that I tested, the application flow didn't stop on my breakpoints on the methods `getPosts` and `getPostById` of the class [PostClientFallback](src/main/java/com/wilterson/demo/hystrix/PostClientFallback.java). Hystrix integration with Spring Cloud OpenFeign needs to be further explored to ensure this project's implementation in correct;
* I'm not very confident that the Log configuration is working as expected. I suspect that the `LogLevel.FULL` should be producing more information than it is in this project. The documentation states that it `Log the headers, body, and metadata for both requests and responses` but I couldn't see all this information on the console. Perhaps all of this information is not available on a simple GET request?! Anyway, Loging with Spring Cloud OpenFeign is another subject that needs to be further explored for a more comprehensive understanding;

## Related References
Olga Masciaszek-Sharma, from Spring Team, briefly talks about Spring Cloud OpenFeign in this [YouTube video](https://www.youtube.com/watch?v=3NcmlrumSOc&t=1884s). She also mentions the issues related to OpenFeign project:

* Issues with reusing client interfaces as controllers
  * @RequestMapping at class level user issues (GH-547, GH-678)
  * CVE-2021-22044
* Maintenance issue caused by dependence on third party upstream project
* Lack of non-blocking support

## Stack Reference Documentation

For further reference, please consider the following sections:

* [JSON Placeholder](https://jsonplaceholder.typicode.com/)
* [Spring Cloud OpenFeign](https://cloud.spring.io/spring-cloud-openfeign/2.2.x/reference/html/)
