# Gemini Code Assistant Context

This document provides context for the Gemini code assistant to understand the `umpa-backend`
project.

## Project Overview

`umpa-backend` is a Spring Boot application written in Java 17. It serves as the backend for a
service platform, likely related to music services given the "umpa" name and some of the code
details. The project follows a layered architecture inspired by Domain-Driven Design (DDD), with a
clear separation of concerns between the API, application, domain, and infrastructure layers.

### Key Technologies

* **Framework:** Spring Boot
* **Language:** Java 17
* **Build Tool:** Gradle
* **Data Access:** Spring Data JPA with Hibernate
* **Database:** PostgreSQL (production/development), H2 (testing)
* **Security:** Spring Security with JWT authentication
* **API Documentation:** `springdoc-openapi` (Swagger UI)
* **Code Generation/Utilities:** Lombok, MapStruct
* **Code Style:** Spotless

### Architecture

The project is structured into the following main packages:

* `web`: The presentation layer, responsible for handling HTTP requests and responses. This
  corresponds to the `api` layer in the proposed architecture in the `README.md`.
* `application`: The application layer, which contains the business logic and orchestrates the
  domain layer.
* `domain`: The core of the application, containing the domain entities, value objects, and
  repository interfaces.
* `infrastructure`: The infrastructure layer, which provides implementations for external concerns
  such as databases, security, and external services.

## Building and Running

### Building the Project

To build the project, use the following Gradle command:

```bash
./gradlew build
```

### Running the Application

The application can be run using the following Gradle command:

```bash
./gradlew bootRun
```

The application will start on the port specified in the `application.yml` file (default is 8080).

### Running Tests

To run the tests, use the following Gradle command:

```bash
./gradlew test
```

## Development Conventions

### Code Style

The project uses the Spotless plugin to enforce a consistent code style. The configuration is
defined in the `build.gradle` file. Before committing any code, it's recommended to run the
following command to format the code:

```bash
./gradlew spotlessApply
```

### API Documentation

The project uses `springdoc-openapi` to generate OpenAPI documentation. The Swagger UI can be
accessed at `/api/docs` when the application is running.

### Environment Configuration

The application uses Spring profiles to manage different configurations for `prod`, `dev`, and
`test` environments. The configuration files are located in `src/main/resources/application.yml`.
The `prod` and `dev` profiles rely on environment variables for sensitive information such as
database credentials and API keys.
