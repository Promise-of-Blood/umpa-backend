# Copilot Instructions for umpa-backend

## Repository Overview

**umpa-backend** is a Spring Boot 3.4.0 application serving as the backend for a music education service platform. The project is built with Java 17 and follows a layered architecture inspired by Domain-Driven Design (DDD).

- **Repository Size**: ~70MB, 165 Java source files, 6 test files
- **Primary Technologies**: Spring Boot 3.4.0, Java 17, Gradle 8.11.1, Spring Data JPA, PostgreSQL (production), H2 (testing)
- **Security**: Spring Security with JWT authentication, OAuth2 (Google, Kakao, Naver)
- **API Documentation**: springdoc-openapi with Swagger UI at `/api/docs`
- **Key Libraries**: Lombok, MapStruct, com.auth0:java-jwt

## Build and Development Workflow

### Prerequisites
- **Java 17** (Temurin distribution recommended)
- **Gradle 8.11.1** (wrapper included - use `./gradlew`)
- **Spring profiles**: `test`, `dev`, `prod`

### Essential Commands

**ALWAYS run these commands in this order when working on code changes:**

1. **Clean build** (after pulling changes or when dependencies change):
   ```bash
   ./gradlew clean
   ```
   - Takes ~30-40 seconds
   - Clears the `build/` directory

2. **Build and test** (primary validation):
   ```bash
   ./gradlew build -Pspring.profiles.active=test
   ```
   - Takes ~60-90 seconds
   - Runs spotlessApply automatically before compilation
   - Runs all tests with H2 in-memory database
   - ALWAYS use `-Pspring.profiles.active=test` to ensure tests use H2

3. **Run tests only**:
   ```bash
   ./gradlew test -Pspring.profiles.active=test
   ```
   - Takes ~40-60 seconds
   - Use this for quick validation after small changes

4. **Format code** (runs automatically during compilation, but can be run manually):
   ```bash
   ./gradlew spotlessApply
   ```
   - Takes ~5-10 seconds
   - **NOTE**: `compileJava` task depends on `spotlessApply`, so code is auto-formatted during build
   - Uses Google Java Format style
   - Configures import order: java/javax/jakarta → lombok → spring → test libraries → blank line → static imports → package imports

5. **Check code formatting without applying**:
   ```bash
   ./gradlew spotlessCheck
   ```
   - Takes ~5-10 seconds
   - Fails if code doesn't meet formatting standards

6. **Build JAR for deployment**:
   ```bash
   ./gradlew bootJar -Pspring.profiles.active=prod
   ```
   - Takes ~40-60 seconds
   - Creates executable JAR in `build/libs/`
   - JAR configuration has `jar.enabled = false`, only bootJar is created

### Running the Application

**Development mode** (requires database connection):
```bash
./gradlew bootRun -Pspring.profiles.active=dev
```
- Requires environment variables: `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`, OAuth2 credentials
- Default port: 8080
- Uses Spring DevTools for live reload

**Test mode** (H2 in-memory):
```bash
./gradlew bootRun -Pspring.profiles.active=test
```
- Uses H2 database: `jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;MODE=PostgreSQL`
- No environment variables needed
- Hibernate DDL auto: `create-drop`

**Important**: The application CANNOT start without a valid database connection unless using the `test` profile with H2.

### Common Build Issues and Workarounds

1. **Missing database connection**: If you see "Failed to configure a DataSource", ensure you're using `-Pspring.profiles.active=test` or provide database environment variables.

2. **Code formatting failures**: The build automatically applies Spotless formatting, so formatting issues are auto-fixed. If `spotlessCheck` fails in CI, run `./gradlew spotlessApply` locally.

3. **Test failures due to wrong profile**: Always use `-Pspring.profiles.active=test` when running tests to ensure H2 is used instead of PostgreSQL.

4. **Gradle daemon issues**: If Gradle behaves unexpectedly, kill the daemon:
   ```bash
   ./gradlew --stop
   ```

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

- **Main application**: `src/main/java/promiseofblood/umpabackend/UmpaBackendApplication.java`
- **Build configuration**: `build.gradle` (root)
- **Application config**: `src/main/resources/application.yml` (with profiles: prod, dev, test)
- **Gradle wrapper**: `./gradlew` (executable)
- **Git ignore**: `.gitignore` (excludes: `.gradle`, `.env`, `build/`, `.idea`, `out/`)

### Configuration Files

1. **build.gradle**:
   - Defines dependencies and Spotless code style rules
   - Configures import order and formatting
   - Sets `compileJava` to depend on `spotlessApply`

2. **application.yml**:
   - Default profile: Basic JPA config with `hibernate.ddl-auto: validate`
   - `prod` profile: Uses env vars for DB and OAuth2, Discord webhook
   - `dev` profile: Uses env vars for DB and OAuth2, localhost redirect URLs
   - `test` profile: H2 in-memory DB, `hibernate.ddl-auto: create-drop`, no OAuth2

### Domain Model (Key Entities)

- **User**: Main user entity with roles (STUDENT, TEACHER, ADMIN)
- **ServicePost**: Base class for service postings (lesson, MR production, etc.)
  - LessonServicePost
  - AccompanimentServicePost
  - MrProductionServicePost
  - ScoreProductionServicePost
- **TeacherProfile** / **StudentProfile**: User profile extensions
- **TeacherCareer**: Teacher work history
- **ChatRoom** / **ChatMessage**: Real-time chat functionality
- **Review**: Service reviews
- **Oauth2User**: OAuth2 provider user mappings

### Value Objects (domain/vo)

Common value objects include: `Email`, `Username`, `Password`, `Nickname`, `PhoneNumber`, `ServiceCost`, and various enums.

## CI/CD and Validation

### GitHub Actions Workflows

Located in `.github/workflows/`:

1. **test.yml** (runs on PR to develop/main, push to main):
   ```yaml
   - Checkout code
   - Setup JDK 17 (Temurin, with Gradle cache)
   - Grant execute permission: chmod +x gradlew
   - Run tests: ./gradlew test -Pspring.profiles.active=test
   ```
   - Takes ~60-90 seconds

2. **deploy.yml** (runs after test.yml succeeds on main):
   ```yaml
   Build job:
     - Build JAR: ./gradlew bootJar -Pspring.profiles.active=prod
     - Upload build artifacts
   Deploy job:
     - Download artifacts
     - SCP to production server
     - Execute deployment script
   ```

### Pre-commit Validation (Local)

Before committing, ensure these pass:
```bash
./gradlew spotlessApply    # Auto-format code
./gradlew build -Pspring.profiles.active=test  # Build and test
```

### CI/CD Expectations

- All PRs to `develop` or `main` MUST pass tests
- Code formatting is enforced (Spotless auto-applies during build)
- Build artifacts are deployed automatically when tests pass on `main`

## Development Guidelines

### Code Style

- **Auto-formatting**: Spotless applies Google Java Format automatically during compilation
- **Import order**: java/javax/jakarta → lombok → spring → test → blank → static → package
- **Lombok**: Widely used (`@Data`, `@Builder`, `@RequiredArgsConstructor`, etc.)
- **MapStruct**: Used for DTO mapping (see `dto/` package)

### Working with the Codebase

1. **Making changes**: Always start with `./gradlew clean` if dependencies changed
2. **Testing changes**: Run `./gradlew test -Pspring.profiles.active=test` frequently
3. **Before committing**: Run `./gradlew build -Pspring.profiles.active=test` to ensure full validation
4. **Formatting**: Don't worry about manual formatting; Spotless handles it during build

### Known Patterns and Conventions

- **Exception handling**: Domain exceptions in `domain/exception`, application exceptions in `application/exception`, global handler in `web/advice`
- **DTO mapping**: MapStruct interfaces in `dto/` package
- **Security**: JWT-based with custom filters in `infrastructure/security`
- **API responses**: Wrapped in `ApiResponse<T>` (see `web/schema/response/ApiResponse.java`)
- **Database**: JPA with Spring Data repositories, entities use Lombok

### Files You Should NOT Modify

- `.github/agents/` - Agent configuration (not relevant to development)
- `gradle/wrapper/` - Gradle wrapper (managed by Gradle)
- `.gradle/` - Gradle cache (generated)
- `build/` - Build output (generated)

### Environment Variables Required

**For dev/prod profiles**:
- `DB_URL`, `DB_USERNAME`, `DB_PASSWORD` - PostgreSQL connection
- `JWT_SECRET_KEY` - JWT signing key (test profile uses hardcoded value)
- `OAUTH2_GOOGLE_CLIENT_ID`, `OAUTH2_GOOGLE_CLIENT_SECRET` - Google OAuth2
- `OAUTH2_KAKAO_CLIENT_ID`, `OAUTH2_KAKAO_CLIENT_SECRET` - Kakao OAuth2
- `OAUTH2_NAVER_CLIENT_ID`, `OAUTH2_NAVER_CLIENT_SECRET` - Naver OAuth2
- `DISCORD_WEBHOOK_URL` - Discord notifications (optional in dev)

**For test profile**: No environment variables needed (uses H2 in-memory database and test defaults)

## Trust These Instructions

These instructions have been validated by running actual builds, tests, and examining the codebase structure. If you encounter issues not covered here, verify the problem first before searching the codebase. Common issues are typically:
1. Wrong Spring profile (use `test` profile for local development/testing)
2. Missing Gradle wrapper execution permission (run `chmod +x gradlew`)
3. Stale Gradle cache (run `./gradlew --stop` then retry)

## Additional Resources

- **API Documentation**: Run the app and visit http://localhost:8080/api/docs
- **Architecture Details**: See `README.md` (Korean) for proposed architecture improvements
- **Project Context**: See `GEMINI.md` for additional context about the project
