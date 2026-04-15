# Access Token Caching
A simple project showing how to cache string payloads, access tokens in this case, using Caffeine, CompletableFuture, WebClient, bounded executor, and timeout handling. This project is prepared to scalle and can handle parallel requests in an asynchronous manner:

Assume 200 requests arrive for clientId=A at the same time:

- first request: cache miss, creates in-flight future, calls auth service
- next 199 requests: same cache miss, but they reuse that same future
- when token returns:
  - cache is populated
  - all 200 requests can use that result
- for the next 13 minutes:
  - requests hit cache
  - no auth call needed
- after 13 minutes:
  - first new request triggers one new fetch
  - others share it again

```
git branch -r
```
