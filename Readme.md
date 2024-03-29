# Bistrot API

This CRUD REST API used to expose and manages resources **Lille bistrot**.

**Context:**

We have a menu exposure API that we would like to improve to make it an API that aggregates menus from a bistrot.

Basic restaurant process:

- An owner will create a menu / card from a set of composition, each composition represents a set of the dish or drink.

- A customer can collect all the menu / cards of a bistrot.

- An owner will be able to create, modify and delete a menu, composition and the content of a composition.


**Entity UML:**
[Miro](https://miro.com/app/board/o9J_lzLHiLc=/)


## Prerequisites
* JDK 16
* Spring Boot
* Liquibase H2
* Maven (wrapper to simplify ./mvnw)
* Karate
* Add lombok plugin in your favorite IDE (IntelliJ)
* github-action

## Workflow

The workflow for development is based on **github-flow** workflow.
Master or main branch is used for current feature development, branches called **next** are used for next delivery.
**Next** is branch must be stabilized by the development team for delivery.

## Running the project locally
Once everything is set up you can run the project using your favorite IDE or directly with maven.
To start your application , simply run:

    rm -rf data
    ./mvnw clean install && java -jar target/decathlon-bootcamp-1.0-SNAPSHOT.jar 


## Liquibase

The database is generated by liquibase when starting the application.

## H2:

The database is consulted by this console h2
http://localhost:8080/h2-console

## Testing

To launch your application's tests, run:

    ./mvnw clean test

# Login mock
This app can be used to run as a sidecar during Integartion Tests to mock a Security Provider (like login).

Clone this project: [login-mock](https://github.com/dktunited/login-mock)
Run application:
````
./mvnw clean install && java -jar target/login-mock-0.0.1-SNAPSHOT.jar
````

## Generate token

This app has also a controller to generate on demand jwt based on input claims.  
See [TokenRequest](/src/main/java/com/decathlon/member/login/mock/domain/TokenRequest.java) to see all availables params.
In a second step the same runing server can validate the generated token.
You can make a POST on /token uri to obtain the jwt.

* Example for a member token:
``` json
curl --location --request POST 'localhost:8082/token' \
--header 'Content-Type: application/json' \
--data-raw '{
"sub": "44bb52d0-ee25-4a1c-9f48-69b0dfcd6eba",
"claims": {
"location": "FR"
},
"scopes": ["profile","openid"]
} 
```
* Example for a client-credential token:
``` json
curl --location --request POST 'localhost:8082/token' \
--header 'Content-Type: application/json' \
--data-raw '{
"claims": {
"location": "FR"
},
"scopes": ["ADMIN","PROFILE","TEST"]
} 
```


## Access endpoints to test

The application exposes the API documentation, testable **/swagger-ui/** of the application :

http://[host:port]/swagger-ui/ (ex en local : http://localhost:8080/swagger-ui/)

## APIs REST Endpoints:
### version 1:
##### Fetch all composition items:
###### Request:

**Scope**: Accessible by all scopes
````
curl -X GET "http://localhost:8080/v1/composition-items" -H "accept: application/json" -H "Authorization: Bearer token"
````

###### Response:
* 200 Successful operation

```json
[
  {
    "id": 1,
    "name": "espresso",
    "price": 2.4,
    "recipe": "some thing",
    "quantity": 3
  },
  {
    "id": 2,
    "name": "AMERICANO",
    "price": 5,
    "recipe": "",
    "quantity": 0
  },
  {
    "id": 3,
    "name": "mocha",
    "price": 7,
    "recipe": "",
    "quantity": 2
  }
]
```

##### Fetch composition item by name:
###### Request:
**Scope**: Accessible by all scopes
````
curl -X GET "http://localhost:8080/v1/composition-items?name=espresso" -H "accept: application/json" -H "Authorization: Bearer token"
````
###### Response:
* 200 Successful operation

```json
[
  {
    "id": 1,
    "name": "espresso",
    "price": 2.4,
    "recipe": "some thing",
    "quantity": 3
  }
]
```
* 403 Forbidden

##### Returns the composition item by id:
###### Request:
**Scope**: Accessible by all scopes
````
curl -X GET "http://localhost:8080/v1/composition-items/1" -H "accept: application/json" -H "Authorization: Bearer token"
````
###### Response:

* 200 Successful operation

```json
{
  "id": 1,
  "name": "espresso",
  "price": 2.4,
  "recipe": "some thing",
  "quantity": 3
}
```

* 404 Not found
* 403 Forbidden


##### Create composition item:
###### Request:
**Scope**: Accessible by all scopes
````
curl -X POST "http://localhost:8080/v1/composition-items" -H "accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer token" -d "{\"name\":\"espresso\",\"price\":2.4,\"recipe\":\"some thing\",\"quantity\":3}"
````
###### Response:
* 201 Successful operation

```json
{
  "id": 100,
  "name": "espresso",
  "price": 2.4,
  "recipe": "some thing",
  "quantity": 3
}
```

* 400 Bad Request
  Composition item name is blank
* 403 Forbidden

##### Update the composition item by id:
###### Request:
**Scope**: Accessible by all scopes
````
curl -X PUT "http://localhost:8080/v1/composition-items/2" -H "accept: */*" -H "Authorization: Bearer " -H "Content-Type: application/json" -d 
"{\"name\":\"espresso\",\"price\":2.4,\"recipe\":\"some thing\",\"quantity\":3}"
````
###### Response:
* 204 No content
* 404 Malformed request the composition item not found
* 403 Forbidden

##### Delete the composition item by id:
###### Request:
**Scope**: Accessible by all scopes
````
curl -X DELETE "http://localhost:8080/v1/composition-items/1" -H "accept: */*"
-H "Authorization: Bearer token"
````
###### Response:
* 204 No content
* 404 Not found
* 403 Forbidden

##### Delete all the composition item:
###### Request:
**Scope**: Accessible by all scopes
````
curl -X DELETE "http://localhost:8080/v1/composition-items" -H "accept: */*"
-H "Authorization: Bearer token"
````
###### Response:
* 204	No content
* 403 Forbidden



### version 2:
The version 2 in web-flux reactor mode.

##### Fetch all composition items:
###### Request:

**Scope**: ADMIN
````
curl -X GET "http://localhost:8080/v1/composition-items" -H "accept: application/json" -H "Authorization: Bearer token"
````

###### Response:
* 200 Successful operation

```json
[
  {
    "id": 1,
    "name": "espresso",
    "price": 2.4,
    "recipe": "some thing",
    "quantity": 3
  },
  {
    "id": 2,
    "name": "AMERICANO",
    "price": 5,
    "recipe": "",
    "quantity": 0
  },
  {
    "id": 3,
    "name": "mocha",
    "price": 7,
    "recipe": "",
    "quantity": 2
  }
]
```

##### Fetch composition item by name:
###### Request:
**Scope**: ADMIN
````
curl -X GET "http://localhost:8080/v1/composition-items?name=espresso" -H "accept: application/json" -H "Authorization: Bearer token"
````
###### Response:
* 200 Successful operation

```json
[
  {
    "id": 1,
    "name": "espresso",
    "price": 2.4,
    "recipe": "some thing",
    "quantity": 3
  }
]
```
* 403 Forbidden

##### Returns the composition item by id:
###### Request:
**Scope**: PROFILE
````
curl -X GET "http://localhost:8080/v1/composition-items/1" -H "accept: application/json" -H "Authorization: Bearer token"
````
###### Response:

* 200 Successful operation

```json
{
  "id": 1,
  "name": "espresso",
  "price": 2.4,
  "recipe": "some thing",
  "quantity": 3
}
```

* 404 Not found
* 403 Forbidden


##### Create composition item:
###### Request:
**Scope**: PROFILE
````
curl -X POST "http://localhost:8080/v1/composition-items" -H "accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer token" -d "{\"name\":\"espresso\",\"price\":2.4,\"recipe\":\"some thing\",\"quantity\":3}"
````
###### Response:
* 201 Successful operation

```json
{
  "id": 100,
  "name": "espresso",
  "price": 2.4,
  "recipe": "some thing",
  "quantity": 3
}
```

* 400 Bad Request
  Composition item name is blank
* 403 Forbidden

##### Update the composition item by id:
###### Request:
**Scope**: ADMIN
````
curl -X PUT "http://localhost:8080/v1/composition-items/2" -H "accept: */*" -H "Authorization: Bearer token" -H "Content-Type: application/json" -d 
"{\"name\":\"espresso\",\"price\":2.4,\"recipe\":\"some thing\",\"quantity\":3}"
````
###### Response:
* 204 No content
* 404 Malformed request the composition item not found
* 403 Forbidden

##### Delete the composition item by id:
###### Request:
**Scope**: ADMIN
````
curl -X DELETE "http://localhost:8080/v1/composition-items/1" -H "accept: */*"
-H "Authorization: Bearer token"
````
###### Response:
* 204 No content
* 404 Not found
* 403 Forbidden

##### Delete all the composition item:
###### Request:
**Scope**: ADMIN
````
curl -X DELETE "http://localhost:8080/v1/composition-items" -H "accept: */*"
-H "Authorization: Bearer token"
````
###### Response:
* 204	No content
* 403 Forbidden







