# Jakarta Bean Validation Kick-In
This is a quick demo on how to get Jacarta Bean Validation kicked in by Spring Boot.

## References
- [Spring Framework's Java Bean Validation Documentation](https://docs.spring.io/spring-framework/reference/core/validation/beanvalidation.html)
- [Baeldung's Article Method Constraints with Bean Validation 3.0](https://www.baeldung.com/javax-validation-method-constraints#1-automatic-validation-with-spring)

## Spring Initializr Project's Setup
https://start.spring.io/#!type=maven-project&language=java&platformVersion=3.2.3&packaging=jar&jvmVersion=21&groupId=com.wilterson&artifactId=beanvalidation&name=Jakarta%20Bean%20Validation&description=Demo%20that%20shows%20how%20to%20get%20Jakarta%20Bean%20Validation%20kicked%20in&packageName=com.wilterson.beanvalidation&dependencies=validation,lombok

### Additional Dependencies
```
testImplementation group: 'org.assertj', name: 'assertj-core', version: '3.25.3'
```