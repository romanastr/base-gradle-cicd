# Multi-project build
Gradle API and reporting sample application.
Demonstrates incremental builds with Gradle and CI/CD.  
Modules:
* data-model - common objects utilized by components
* dynamo-db-client - client for both reads and writes to DynamoDB
* api - which calls public APIs and returns data
* usage-api -  which remembers the history of the client calls
* usage-reporting
* Gatling load tests

Shortlist of public APIs:
OpenLibrary: https://openlibrary.org/dev/docs/restful_api#query
Nameday API: https://app.swaggerhub.com/apis/nekvapil/InternationalNamedayAPI/3.0.0#/default/get_today
