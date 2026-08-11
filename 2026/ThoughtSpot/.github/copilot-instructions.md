# ThoughtSpot Console App - Development Guidelines

## Project Overview
Java 25 console application with Lombok and JUnit 5 dependencies.

## Build & Test
- **Compile**: `mvn clean compile`
- **Run Tests**: `mvn test`
- **Run App**: `mvn exec:java -Dexec.mainClass="Main"`
- **Full Build**: `mvn clean package`

## Project Structure
- `/src/main/java` - Main application source code
- `/src/main/resources` - Application resources
- `/src/test/java` - Unit tests
- `pom.xml` - Maven configuration with Java 25, Lombok, and JUnit 5

## Dependencies
- **Java**: 25
- **Lombok**: 1.18.30 (for reducing boilerplate code)
- **JUnit**: 5.10.1 (for unit testing)

## Development Notes
- Use Lombok annotations (@Log, @Data, @Getter, @Setter, etc.) to reduce boilerplate
- Write unit tests in `/src/test/java` following JUnit 5 conventions
- Follow Maven standard directory structure
