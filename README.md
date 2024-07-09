# MultipartFile Spring Class for Receiving Files
I'm exploring through this project how to receive files in Spring via its built-in MultipartFile class. This project is also a PoC for the story that I have to implement for Ethoca, which requires the binary file and its metadata sent as form-data attributes. The PoC also requires OpenAPI Generator for auto-generating the controller class.

## Project Setup
```
https://start.spring.io/#!type=maven-project&language=java&platformVersion=3.3.1&packaging=jar&jvmVersion=21&groupId=com.wilterson&artifactId=multipartfile-demo&name=multipartfile-demo&description=Demo%20project%20for%20Spring%20Boot's%20MultipartFile&packageName=com.wilterson&dependencies=web,devtools,devtools
```

## CURL
```
curl -i -X POST -H "Content-Type: multipart/form-data" -F 'file=@"/home/francow/Shared/test.txt"' -F 'file=@"/home/francow/Shared/test1.txt"' -F 'author="Wilterson Franco"' -L 'http://localhost:8080/api/upload'
```

## References
[RFC 2388 - Returning Values from Forms:  multipart/form-data](https://www.rfc-editor.org/rfc/rfc2388)

[RFC-7578 - Returning Values from Forms: multipart/form-data](https://www.rfc-editor.org/rfc/rfc7578)