# Copilot Instructions for umpa-backend

## Repository Overview

**umpa-backend** is a Spring Boot 3.4.0 music education service backend with Java 17, following Domain-Driven Design (DDD).

- **Size**: 165 Java source files, 6 test files
- **Stack**: Spring Boot 3.4.0, Java 17, Gradle 8.11.1, Spring Data JPA, PostgreSQL (prod), H2 (test)
- **Security**: JWT + OAuth2 (Google, Kakao, Naver)
- **Libraries**: Lombok, MapStruct, springdoc-openapi (Swagger at `/api/docs`)

## Build and Development Workflow

### Prerequisites
Java 17 (Temurin), Gradle 8.11.1 (wrapper: `./gradlew`), Spring profiles: `test`, `dev`, `prod`

### Essential Commands

**Run commands in this order:**

1. **Clean**: `./gradlew clean` (~30-40s) - Use after dependency changes
2. **Build & Test**: `./gradlew build -Pspring.profiles.active=test` (~60-90s) - Primary validation, auto-runs spotlessApply
3. **Test only**: `./gradlew test -Pspring.profiles.active=test` (~40-60s) - Quick validation
4. **Format**: `./gradlew spotlessApply` (~5-10s) - Auto-formats with Google Java Format (runs during build)
5. **Check format**: `./gradlew spotlessCheck` (~5-10s) - Validates formatting
6. **Build JAR**: `./gradlew bootJar -Pspring.profiles.active=prod` (~40-60s) - Creates executable JAR in `build/libs/`

**CRITICAL**: Always use `-Pspring.profiles.active=test` for tests to use H2 database.

### Running the Application

- **Dev**: `./gradlew bootRun -Pspring.profiles.active=dev` (requires DB env vars, port 8080)
- **Test**: `./gradlew bootRun -Pspring.profiles.active=test` (uses H2, no env vars needed)

**Note**: Application requires valid DB connection except in `test` profile with H2.

### Common Issues

1. **"Failed to configure a DataSource"**: Use `-Pspring.profiles.active=test` or provide DB environment variables
2. **Formatting failures**: Auto-fixed by build; run `./gradlew spotlessApply` if needed
3. **Wrong profile in tests**: Always use `-Pspring.profiles.active=test`
4. **Gradle issues**: Run `./gradlew --stop` to kill daemon

## Project Architecture

### Package Structure

```
src/main/java/promiseofblood/umpabackend/
├── UmpaBackendApplication.java          # Main Spring Boot application entry point
├── web/                                 # Presentation Layer (API endpoints)
│   ├── controller/                      # 11 REST controllers (User, Login, Register, etc.)
│   ├── advice/                          # Global exception handlers (@RestControllerAdvice)
│   └── schema/                          # Request/Response DTOs
│       ├── request/                     # API request models
│       └── response/                    # API response models (includes ApiResponse wrapper)
├── application/                         # Application Layer (business logic)
│   ├── service/                         # Service implementations (UserService, Oauth2Service, etc.)
│   ├── command/                         # Command objects for write operations
│   ├── query/                           # Query objects for read operations
│   └── exception/                       # Application-level exceptions
├── domain/                              # Domain Layer (core business logic)
│   ├── entity/                          # JPA entities (User, ServicePost, TeacherProfile, etc.)
│   │   └── abs/                         # Abstract base entities
│   ├── vo/                              # Value Objects (Email, Password, ServiceCost, etc.)
│   └── repository/                      # Repository interfaces (11 repositories)
├── infrastructure/                      # Infrastructure Layer (external integrations)
│   ├── config/                          # Configuration classes (Security, JPA, WebSocket, etc.)
│   ├── security/                        # Security implementations (JwtFilter, JWT utilities)
│   ├── oauth/                           # OAuth2 provider integrations
│   ├── storage/                         # File storage implementations
│   └── validation/                      # Custom validators
└── dto/                                 # Shared DTOs (mapper interfaces with MapStruct)
```

### Key Files and Locations

- Main: `src/main/java/promiseofblood/umpabackend/UmpaBackendApplication.java`
- Build: `build.gradle`, `./gradlew`
- Config: `src/main/resources/application.yml` (profiles: prod, dev, test)
- Ignore: `.gitignore` (excludes `.gradle`, `.env`, `build/`, `.idea`)

### Configuration Files

**build.gradle**: Dependencies, Spotless rules (Google Java Format, import order), `compileJava` depends on `spotlessApply`

**application.yml**:
- Default: JPA with `hibernate.ddl-auto: validate`
- `prod`: Env vars for DB/OAuth2, Discord webhook
- `dev`: Env vars for DB/OAuth2, localhost redirects
- `test`: H2 in-memory (`jdbc:h2:mem:testdb`), `hibernate.ddl-auto: create-drop`, no OAuth2

### Domain Model

**Key Entities**: User (roles: STUDENT, TEACHER, ADMIN), ServicePost (base for LessonServicePost, AccompanimentServicePost, MrProductionServicePost, ScoreProductionServicePost), TeacherProfile, StudentProfile, TeacherCareer, ChatRoom, ChatMessage, Review, Oauth2User

**Value Objects** (domain/vo): Email, Username, Password, Nickname, PhoneNumber, ServiceCost, enums

## CI/CD and Validation

### GitHub Actions (`.github/workflows/`)

**test.yml** (PR to develop/main, push to main):
- Setup JDK 17 (Temurin, Gradle cache)
- `chmod +x gradlew`
- `./gradlew test -Pspring.profiles.active=test` (~60-90s)

**deploy.yml** (after test.yml succeeds on main):
- Build: `./gradlew bootJar -Pspring.profiles.active=prod`
- Upload artifacts, SCP to production, execute deployment script

### Pre-commit Validation
```bash
./gradlew spotlessApply
./gradlew build -Pspring.profiles.active=test
```

All PRs to `develop`/`main` must pass tests. Spotless auto-applies formatting. Build artifacts deploy on `main` after tests pass.

## Development Guidelines

### Code Style
- **Spotless**: Auto-applies Google Java Format during compilation
- **Import order**: java/javax/jakarta → lombok → spring → test → blank → static → package
- **Lombok**: Widely used (`@Data`, `@Builder`, `@RequiredArgsConstructor`)
- **MapStruct**: DTO mapping (see `dto/` package)

### Workflow
1. `./gradlew clean` if dependencies changed
2. `./gradlew test -Pspring.profiles.active=test` frequently during development
3. `./gradlew build -Pspring.profiles.active=test` before committing
4. Formatting is automatic during build

### Patterns
- **Exceptions**: Domain (`domain/exception`), application (`application/exception`), global handler (`web/advice`)
- **DTOs**: MapStruct interfaces in `dto/`
- **Security**: JWT with custom filters in `infrastructure/security`
- **API**: Wrapped in `ApiResponse<T>` (`web/schema/response/ApiResponse.java`)
- **Database**: JPA with Spring Data, Lombok entities

### Do NOT Modify
`.github/agents/`, `gradle/wrapper/`, `.gradle/`, `build/`

### Environment Variables
**dev/prod**: `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`, `JWT_SECRET_KEY`, OAuth2 credentials (`OAUTH2_GOOGLE_CLIENT_ID`, etc.), `DISCORD_WEBHOOK_URL`
**test**: None needed (H2 + test defaults)

## Trust These Instructions

These instructions are validated by running actual builds and tests. Common issues:
1. Wrong Spring profile (use `test` for local dev/testing)
2. Missing gradlew permission (`chmod +x gradlew`)
3. Stale Gradle cache (`./gradlew --stop`)

**Additional Resources**: API docs at http://localhost:8080/api/docs, `README.md` (Korean) for architecture, `GEMINI.md` for context
