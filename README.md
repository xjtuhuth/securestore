# securestore
secure store sample implementation.

REST-API with GET, POST, and DELETE method. Puts a constant value to the secure store, allows to read the value, and also to delete it.

The sample also shows how to deal with two connected database services in spring cloud connector. Therefore, two service-instances of the hana service (one with securestore plan, the other with schema plan, which is normally used for persistence-service) are bound to the application.

## prerequisites:

### create service instances in target space

xs create-service hana securestore sample-securestore

xs create-service hana schema sample-schema

## build

mvn clean package

## deploy

xs push

## usage

POST: "app-domain/securestore to pos a constant value"

GET: "app-domain/securetore to get the constant value"

DELETE: "app-domain/securetore to delete the constant value"

