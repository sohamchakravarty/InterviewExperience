# Java Project

A Java 25 project with Lombok and JUnit 5 dependencies.

## Project Structure

```
java-project/
├── src/
│   ├── main/
│   │   ├── java/          # Source code
│   │   └── resources/     # Resource files
│   └── test/
│       ├── java/          # Test code
│       └── resources/     # Test resource files
├── pom.xml                # Maven configuration
└── README.md              # This file
```

## Requirements

- Java 25
- Maven 3.6+

## Building

```bash
mvn clean compile
```

## Testing

```bash
mvn test
```

## Dependencies

- **Lombok** - Reduce boilerplate code
- **JUnit 5** - Testing framework
