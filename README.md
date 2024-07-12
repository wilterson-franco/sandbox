# MultipartFile Spring Class for Receiving Files
I'm exploring through this project how to receive files in Spring via its built-in MultipartFile class. This project is also a PoC for the story that I have to implement for Ethoca, which requires the binary file and its metadata to be sent as form-data attributes. The PoC also requires OpenAPI Generator for auto-generating the controller class.

## Project Setup
```
https://start.spring.io/#!type=maven-project&language=java&platformVersion=3.3.1&packaging=jar&jvmVersion=21&groupId=com.wilterson&artifactId=multipartfile-demo&name=multipartfile-demo&description=Demo%20project%20for%20Spring%20Boot's%20MultipartFile&packageName=com.wilterson&dependencies=web,devtools,devtools
```

## Maven Commands
### To get OpenAPI Generator to generate the model and controller classes
```
mvn clean generate-sources
```

## Command Line REST
```
curl -i -X POST \
    -H "Content-Type: multipart/form-data" \
    -F 'file=@"/home/francow/Shared/test.txt"' \
    -F 'jobParams="[{ \"name\":\"parentGuid\",\"value\":\"ABCDEFGHIJ\",\"type\":\"STRING\"}]";type=application/json' \
    -L 'http://localhost:8080/api/upload'
```

## Postman Collection
_*Note*: save this JSON to a file and import it into Postman_
```
{
	"info": {
		"_postman_id": "6378ddad-c172-4c90-bfd2-50fa20e9bc10",
		"name": "Form-Data-Request",
		"schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json",
		"_exporter_id": "4979021"
	},
	"item": [
		{
			"name": "Send File",
			"request": {
				"method": "POST",
				"header": [],
				"body": {
					"mode": "formdata",
					"formdata": [
						{
							"key": "file",
							"contentType": "text/plain",
							"type": "file",
							"src": "/home/francow/Shared/test.txt"
						},
						{
							"key": "jobParams",
							"value": "[{ \"name\":\"parentGuid\",\"value\":\"ABCDEFGHIJ\",\"type\":\"STRING\"}]",
							"contentType": "application/json",
							"type": "text"
						}
					]
				},
				"url": {
					"raw": "http://localhost:8080/api/upload",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"api",
						"upload"
					]
				}
			},
			"response": []
		}
	]
}
```

## References
[RFC 2388 - Returning Values from Forms:  multipart/form-data](https://www.rfc-editor.org/rfc/rfc2388)

[RFC-7578 - Returning Values from Forms: multipart/form-data](https://www.rfc-editor.org/rfc/rfc7578)

## TODO
