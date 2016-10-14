# Human Resources Application

[![Build Status](https://travis-ci.org/Rossiar/dropwizard-hr-rest.svg?branch=master)](https://travis-ci.org/Rossiar/dropwizard-hr-rest)

Provides a REST interface to access the data contained [in this GitHub project](https://github.com/datacharmer/test_db).

The current open endpoints are:

    GET     /departments
    GET     /departments/{id}
    GET     /departments/{id}/employees
    GET     /employees
    GET     /employees/{id}
    GET     /employees/{id}/titles

There are several other endpoints that are secured via an api key that you must provide in the `Authorization` header
of your request:

    GET     /employees/{id}/salaries
    DELETE  /users
    GET     /users
    POST    /users
    GET     /users/{key}


Requirements
---

1. Java 8
1. Maven 3


How to start
---

1. Run `mvn clean package` to build
1. Open `config.yml` and edit the database settings so that the application will connect to your version of the database
1. Start the application with `java -jar target/dropwizard-hr-rest-1.0.jar server config.yml`

For secure api access, you can obtain your initial api key by running the following:

    java -jar target/dropwizard-hr-rest-1.0.jar create-admin --username ross config.yml


Improvements
---

1. There is no distinction between a user and an admin (and there should be to stop users creating other users 
arbitrarily)
1. Users should not have to add the `users` table to their mysql instance
1. Find a way to run on Heroku