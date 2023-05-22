# GitHub User Export

GitHub User Export is a Java Spring Boot application that allows you to search and export GitHub users into a PDF format. It provides a RESTful API for performing user searches, exporting user data to PDF, managing export history, and downloading exported PDF files.

## Features

- Search and export GitHub users into PDF format.
- Track and manage export history.
- Download existing exported PDF files.

## Technologies Used

- Java
- Spring Boot
- PostgreSQL
- Hibernate
- iText PDF Library

## Prerequisites

- Java 8 or higher installed
- Apache Maven installed
- PostgreSQL database server

## Getting Started

1. Clone the repository.

2. Configure the PostgreSQL database.

3. Build the application.

4. Run the application.

5. The application will start running on `http://localhost:8080`.

## API Endpoints

- **Search Users:**
  - GET /users?query=Q
  ![image](https://github.com/usuv/github-user-export/assets/45777644/34d33475-df73-48cb-bca0-22839ea3bce2)

- **Export Users:**
  - GET /users/export?query=Q
  ![image](https://github.com/usuv/github-user-export/assets/45777644/fe0732cb-884b-433e-8601-71a528c9e211)

- **List Export History:**
  - GET /users/export-history
  ![image](https://github.com/usuv/github-user-export/assets/45777644/cd008e60-749d-4cee-a15d-8faa975953b0)

- **Download Exported PDF:**
  - GET /users/download/{id}
  ![image](https://github.com/usuv/github-user-export/assets/45777644/639dc815-bd15-48d8-aad9-85d02b8395a2)

## Configuration

The application can be configured by modifying the `application.properties` file located in the `src/main/resources` directory. You can update the database connection details, server port, and other properties as needed.

## Acknowledgements

- Spring Boot
- PostgreSQL
- Hibernate
- iText PDF Library
