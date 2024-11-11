# GraphQL PoC
Project to follow to PoC GraphQL with Spring and OpenAPI Spec. In this project, I also explore Jakarta validation and pagination with GraphQL

## Project Setup
Spring Initializr [LINK](https://start.spring.io/#!type=maven-project&language=java&platformVersion=3.3.5&packaging=jar&jvmVersion=21&groupId=com.wilterson&artifactId=graphql-poc&name=GraphQL%20PoC&description=Project%20for%20exploring%20GraphQL%20with%20Spring%20and%20OpenAPI&packageName=com.wilterson.graphqlpoc&dependencies=graphql,web,actuator) to the project config.

## How to Launch
Run the project and open the [GraphiQL web interface](http://localhost:8080/graphiql) in the browser. Enter the following query on the left-hand side of the GraphiQL palyground and click the Play button:

```dtd
query myPets {
    pets {
        name
        color
    }
}
```