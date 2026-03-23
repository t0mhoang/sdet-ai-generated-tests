# SDET AI-Generated Test Suite

A Java test project demonstrating AI-assisted test generation and shift-left testing.

## What this covers
- AI-generated test cases from user story requirements
- Shift-left testing — test stubs written before feature implementation
- @Disabled annotation for pending tests awaiting implementation
- TODO comments documenting implementation requirements
- REST Assured API tests running against live endpoints
- Separation of runnable tests from stubbed tests
- Allure reporting

## Tech stack
- Java 17
- REST Assured 5.4.0
- JUnit 5.10.0
- Allure 2.25.0
- Maven

## How to run
```bash
mvn clean test
mvn allure:serve
```

## Project structure
```
src/
├── main/java/com/sdet/
│   └── config/ApiConfig.java
└── test/java/com/sdet/
    └── tests/
        ├── UserAuthTest.java        # Live API tests — fully implemented
        └── PasswordResetTest.java   # Shift-left stubs — awaiting implementation
```

## Testing approach
UserAuthTest contains 13 fully implemented tests hitting the JSONPlaceholder API.
PasswordResetTest contains 39 test stubs generated from a password reset user story
using AI-assisted test case generation. These stubs define expected behavior from
requirements and are ready for implementation when the feature is built.