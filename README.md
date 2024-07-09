# Poseidon
# Poseidon Trading Application

Poseidon is a trading application designed to manage and monitor trading activities. It includes features such as bid management, curve points, ratings, rules, trades, and user management.

## Table of Contents
- [Installation](#installation)
- [Configuration](#configuration)
- [Usage](#usage)
- [Running Tests](#running-tests)
- [Technologies Used](#technologies-used)
- [Contributing](#contributing)



## Installation

To install the application, follow these steps:

1. Clone the repository:
    ```sh
    git clone https://github.com/A-Pogam/Poseidon
    ```
2. Navigate to the project directory:
    ```sh
    cd poseidon
    ```
3. Build the project using Maven:
    ```sh
    mvn clean install
    ```

## Configuration

Configure the application by modifying the `application.properties` and `application-test.properties` files located in the `src/main/resources` directory. These files contain settings for database connections, logging, and other configurations.

## Usage

To run the application, use the following command:
```sh
mvn spring-boot:run
```
The application will be available at http://localhost:8080.

## Running Tests
To run the tests, use the following command:
```sh
mvn test
```

## Technologies Used
- **Programming Language**: Java
- **Frameworks**:
    - Spring Boot
    - Spring MVC
    - Spring Security
- **Templating Engine**: Thymeleaf
- **ORM**: JPA/Hibernate
- **Build Tool**: Maven

## Contributing
Contributions are welcome! Please create a pull request or submit an issue for any improvements or bug fixes.

THANKS !
