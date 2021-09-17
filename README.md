# CRUD Web

A simple minimalistic app with

Client Side - AngularJS

Server Side - Spring boot

Database - PostgreSQL


## Download

``` shell
$ git clone https://github.com/rlaqks2/angularJS_study.git
```

## Run Server Side

``` shell
npm start
```

# Setup Database

1. File name \
- application-dev.properties
2. Database information
- spring.datasource.platform=postgres
- spring.datasource.driver-class-name=org.postgresql.Driver 
- spring.datasource.url= jdbc:postgresql://localhost:5432/postgres 
- spring.datasource.username=postgres 
- spring.datasource.password=admin

## Description

- User registration and authentication implemented.
- Browse through users registered in the app.
- API require authentication to get data.
- Only admin users can add users and edit his role but registration is open.

## Example Screenshots

![alt tag](src/main/resources/public/images/app-screenshots/home.jpg?raw=true)

![alt tag](src/main/resources/public/images/app-screenshots/not logged in.jpg?raw=true)

![alt tag](src/main/resources/public/images/app-screenshots/registration.jpg?raw=true)

![alt tag](src/main/resources/public/images/app-screenshots/log in.jpg?raw=true)

![alt tag](src/main/resources/public/images/app-screenshots/users.jpg?raw=true)

![alt tag](src/main/resources/public/images/app-screenshots/products.jpg?raw=true)

![alt tag](src/main/resources/public/images/app-screenshots/product.jpg?raw=true)



