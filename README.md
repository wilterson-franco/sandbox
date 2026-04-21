# CURL command for sending requests to this project

```
curl --request POST \
--url http://localhost:8080/demo/searches \
--header 'Content-Type: application/json' \
--header 'User-Agent: insomnia/12.5.0' \
--data '{
"locale": "en-CA",
"a2aCriteria": [
{
"transactionType": "string",
"transactionValue": "string",
"a2aRail": {
"railType": "sepa",
"cnpj": "cnpj",
"e2eTrx": "e2eTrx",
"atr1": "atr1",
"atr2": "atr2"
}
}
]
}'
```