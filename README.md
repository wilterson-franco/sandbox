# Spring Managed Class Access from a Pojo
This project illustrates how an application can access a spring managed class from a non-spring class. It can be useful on projects following the Hexagonal Architecture, to minimize the spring dependency in the application package (they must be as pure, less 3pp libraries dependent as possible).

The [AppLogger](./src/main/java/com/wilterson/beanaccess/AppLogger.java) class leverages the custom [SpringContext](./src/main/java/com/wilterson/beanaccess/SpringContext.java) class to get the spring-bean UserService so that the user first and last names are printed on each log message.

The project is inspired by this [How-to article](https://confluence.jaytaala.com/display/TKB/Super+simple+approach+to+accessing+Spring+beans+from+non-Spring+managed+classes+and+POJOs).

Using this type of mechanism for getting spring beans from non-spring classes can be tricky to unit test though, as it uses static methods.

# Logging
This project is also a good source for how to log with Logback. It establishes three different Loggers: `CONSOLE`, `FILE`, and `FILE-ROLLING`. The [LoggerName](./src/main/java/com/wilterson/beanaccess/LoggerName.java) enum is in sync with the [logback.xml](./src/main/resources/logback.xml) config file, which defines loggers leveraging the LogBack [hierarchy concept](https://logback.qos.ch/manual/architecture.html#LoggerContext) and without losing the benefit of logging the class name in the logs (prepend of `FILE` or `ROLLING`). When [BusinessWorker](./src/main/java/com/wilterson/beanaccess/BusinessWorker) creates a new [AppLogger](./src/main/java/com/wilterson/beanaccess/AppLogger.java), it passes what type of logger it wants the messages to be logged into. The [AppLogger](./src/main/java/com/wilterson/beanaccess/AppLogger.java) class constructors are smart enough to get the right logger via the LoggerFactory LogBack class

It also demonstrates how unit test classes can check if messages have been logged properly. These pages were my references for this:

- LogBack's [documentation](https://logback.qos.ch/manual/architecture.html).
- Beldung's [A Guide To Logback](https://www.baeldung.com/logback) and MKyong's [SLF4J Logback Tutorial](https://mkyong.com/logging/slf4j-logback-tutorial/) articles for a LogBack guide.
- Baeldung's [Asserting Log Messages With JUnit](https://www.baeldung.com/junit-asserting-logs) for understanding how to assert LogBack messages. 
- [stackoverflow question](https://stackoverflow.com/questions/4650222/what-is-the-best-way-to-unit-test-slf4j-log-messages) for an alternative approach to asserting LogBack messages.

# Spring Initializr Project Setup
```dtd
https://start.spring.io/#!type=maven-project&language=java&platformVersion=3.2.3&packaging=jar&jvmVersion=21&groupId=com.wilterson&artifactId=bean-access&name=Bean%20Access%20From%20Non-Spring%20Classes&description=Project%20that%20illustrates%20access%20to%20spring%20bean%20objects%20from%20non-spring%20classes&packageName=com.wilterson.beanaccess
```