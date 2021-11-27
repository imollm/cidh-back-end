# PDP Häagen-Dazs Backend -> MacadamiaNut

![HD Logo](https://i.imgur.com/MaPZD0w.png)

## Tech Stack


Macadamia Nut has been written using `Kotlin` ver `1.6.0`, and uses `Spring Boot` as framework.

- For DataBase SQL migrations it uses [FlyWay](https://flywaydb.org)
- For the data layer, Macadamia Nut does not use any type or RDBMS. Instead, it uses SQL directly with a thin layer on top through [jOOQ](https://www.jooq.org). jOOQ provides type safety to the SQL queries without losing control of the queries against the database.
- Server requires a Postgres or Postgres SQL Dialect compatible database.
- User session and authentication is done using [JWT Tokens](http://jwt.io)

## Project Build

1 - Project uses [Gradle](https://gradle.org) as build tool
2 - It is recommended to build and run the project using JetBrains [IntelliJ IDEA](https://www.jetbrains.com/idea/) IDE
3 - Project contains a default run configuration, called `MACADAMIANUTCONFIGURATION`. These are the settings:

![MacadamiaNutConfig](https://i.imgur.com/YaofAsn.png)

4 - Project requires JVM version 16

### Running the server

1 - Make sure you have a Postgres database running locally. make sure that the port, user and password matches with the configuration file of the server.
2 - Once the database is running, execute the server by using the `MACADAMIANUTCONFIGURATION`.


## Configuration

Server configuration properties are all set within the `config/application.yml` file.  
