# URL Shortener Microservice - Spring Boot

This is a URL shortener microservice implemented using Spring Boot, a popular Java-based framework that simplifies the development of standalone, production-grade Spring-based Applications. It utilizes Java Version 20 and JDK Version 20 to provide a fast and efficient way of creating, managing, and sharing short URLs.

## Technology Stack

- Language: Java 20
- Framework: Spring Boot
- Build Tool: Maven
- JDK Version: 20

## Prerequisites

1. Install Java Development Kit (JDK) 20:
    - For Windows, download the installer from the [official Oracle page](https://www.oracle.com/java/technologies/javase-jdk20-downloads.html).
    - For macOS, download the installer from the [official Oracle page](https://www.oracle.com/java/technologies/javase-jdk20-downloads.html).

2. Install [Maven](https://maven.apache.org/download.cgi):
    - For Windows, follow the installation guide [here](https://maven.apache.org/guides/getting-started/windows-prerequisites.html).
    - For macOS, follow the installation guide [here](https://maven.apache.org/install.html).

## Installation

1. Clone the repository to your local machine:

```sh
git clone https://github.com/your_username/url-shortener-springboot.git
```

2. Change directory to the project folder:

```sh
cd url-shortener-springboot
```

3. Build the project using Maven:

```sh
mvn clean install
```

4. Start the application using Maven Spring Boot plugin:

```sh
mvn spring-boot:run
```

## Running Locally

The application should now be running locally at [http://localhost:8080](http://localhost:8080).

### Windows

1. Open the command prompt and navigate to the project directory.
2. Execute the following command to start the application:

```sh
java -jar target\url-shortener-springboot-0.0.1-SNAPSHOT.jar
```

### macOS

1. Open the terminal and navigate to the project directory.
2. Execute the following command to start the application:

```sh
java -jar target/url-shortener-springboot-0.0.1-SNAPSHOT.jar
```

## Code Coverage Badge

[![codecov](https://codecov.io/gh/simiyu1/shorter-url-shortening-service/graph/badge.svg?token=692b805b-da99-443d-ad28-2272239425dc)](https://codecov.io/gh/simiyu1/shorter-url-shortening-service)



## API Usage

After running the microservice, you can use the following endpoints:

- **POST /shorten** - Shorten a URL
- **GET /{shortURL}** - Retrieve the original URL using the short URL

Example usage:

```sh
curl -X POST -H "Content-Type: application/json" -d '{"url":"https://www.example.com"}' http://localhost:8080/shorten
```

This will return a JSON response containing the short URL:

```json
{
  "shortUrl": "http://localhost:8080/abc123"
}
```