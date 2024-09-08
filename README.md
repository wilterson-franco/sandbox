# Book: GraphQL with Java and Spring
Project to follow along with the book `GraphQL with Java and Spring`

## Project Setup
Spring Initializr [LINK](https://start.spring.io/#!type=maven-project&language=java&platformVersion=3.3.3&packaging=jar&jvmVersion=21&groupId=com.wilterson&artifactId=graphql-with-java-and-spring&name=GraphQL%20with%20Java%20and%20Spring&description=Project%20for%20following%20along%20with%20the%20book%20%22GraphQL%20with%20Java%20and%20Spring%22&packageName=com.wilterson&dependencies=graphql,webflux) to the project config.

## How to Launch
Run the project and open the [GraphiQL web interface](http://localhost:8080/graphiql) in the browser. Enter the following query on the left-nand side of the GraphiQL palyground and click the Play button:

```dtd
query myPets {
    pets {
        name
        color
    }
}
```