# Game of the Goose (Gioco dell'Oca) - OOP Implementation

This project is a digital simulation of the classic "Game of the Goose" board game, developed as part of the **Object-Oriented Programming (OOP)** laboratory at the University of L'Aquila (UnivAQ).

## Project Overview
The application demonstrates the core principles of OOP, including:
* **Encapsulation:** Managing player states and board rules.
* **Inheritance/Polymorphism:** Implementation of special board cells with unique effects.
* **Maven Integration:** Managed build system and dependency management.

## Repository Contents
* `src/`: Source code containing game logic and UI.
* `documentation/`: Class diagrams (UML) and project report.
* `pom.xml`: Maven configuration file.

---

## Technical Instructions (How to Run)

### 1. Compile the Project
To compile the source code and clean temporary files:
```mvn clean compile```
### 2. Run Tests
If you want to execute the JUnit test cases:
```mvn test```
### 3. Build the Package
To generate the executable .jar file:
``` mvn package```
### 4. Launch the Game
``` mvn exec:java -Dexec.mainClass="change.it.Runner"```



