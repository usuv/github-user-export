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

- **Search and Export Users:**
  - POST /api/users/export

- **List Export History:**
  - GET /api/export-history

- **Download Exported PDF:**
  - GET /api/export-history/{id}/download

## Configuration

The application can be configured by modifying the `application.properties` file located in the `src/main/resources` directory. You can update the database connection details, server port, and other properties as needed.

## Contributing

Contributions are welcome! If you find any issues or would like to add new features, please feel free to open an issue or submit a pull request.

## License

This project is licensed under the MIT License.

## Acknowledgements

- Spring Boot
- PostgreSQL
- Hibernate
- iText PDF Library
