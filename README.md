# Spring Managed Class Access from a Pojo
This project illustrates how an application can access a spring managed class from a non-spring class. It can be useful on projects following the Hexagonal Architecture, to minimize the spring dependency in the application package (they must be as pure, less 3pp libraries dependent as possible).

The project is inspired by this [How-to article](https://confluence.jaytaala.com/display/TKB/Super+simple+approach+to+accessing+Spring+beans+from+non-Spring+managed+classes+and+POJOs).

## Project Setup
```dtd
https://start.spring.io/#!type=maven-project&language=java&platformVersion=3.2.3&packaging=jar&jvmVersion=21&groupId=com.wilterson&artifactId=bean-access&name=Bean%20Access%20From%20Non-Spring%20Classes&description=Project%20that%20illustrates%20access%20to%20spring%20bean%20objects%20from%20non-spring%20classes&packageName=com.wilterson.beanaccess&dependencies=lombok
```