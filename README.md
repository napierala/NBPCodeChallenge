# NBPCodeChallenge

## Decisions:
- Security - Every request needs to be authenticated by an API user and password, Basic was chosen to simplify the challenge, of course HTTPS is needed for basic authentication but was omitted to avoid the self-signed certificate problems. 
- Persistence - A simple h2 db has been added - it just for demonstration purpose.
- NBPApi cache - Np cache has been added to the api calls, and it just seems wasteful as the rates change every working day around 12:00, but to simplify things no cache has been added.

## Prerequisites:
- maven installed.
- Java8 jdk installed.
- 8088 port not used.

## Run integration tests:

mvn clean test -P IT

## Build:

mvn clean package

## Launch:

java -jar target/nbpcodechallenge.jar

## REST documentation:

http://localhost:8088/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config

Everything is in the swagger but here is an example REST call:
- Method: POST
- Path: localhost:8088/exchangeRate/accountBalance
- Authorization Basic(user: api#1 password: user_password)
- Body:
```json
{
    "currencyType": "EUR",
    "bankAccountNumber": "PL27114020040000300201355387",
    "userCode": "1c07ae29"
}
```

## PreLoaded data:

- Admin: user: admin pass: admin_password
- Regular: user: api#1 pass: user_password
- Example bank account:
- 
  | userCode | accountNumber                | balance | currency |
  |----------|------------------------------|---------|----------|
  | 1c07ae29 | PL27114020040000300201355387 | 500     | PLN      |