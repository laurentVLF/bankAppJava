# Bank Transaction Management System

## Table of Contents

- [Description](#description)
- [Installation](#installation)
- [Running Tests](#running-tests)
- [Project Architecture](#project-architecture)
- [Technologies used](#technologies-used)

## Description

This project implements a banking transaction management system, which allows users to:
- Make deposits and withdrawals from their bank accounts.
- View their transaction history.

## Installation

### Prerequisite

- JDK 23
- MAVEN 3.6

### Steps installation

1. Clone repository :
   ```bash
   git clone https://github.com/laurentVLF/bankAppJava.git
   cd bankAppJava
   
2. Installing dependencies :
   mvn clean install


## Running Tests

### Unit Tests

   mvn test


## Project architecture
```plaintext
   src
   ├── main
   │   ├── java
   │   │   ├── org.example
   │   │   │   ├── domain              # Business domaine
   └── test
       └── kotlin
           └── org.example
               └── domain             # Service Tests
```
## Technologies Used

   - Java: Main language of the project.
   - JUnit 5: Test framework.
