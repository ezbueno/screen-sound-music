
# Screen Sound Music

This project was developed as part of a challenge from the Java Back-End training course by Oracle Next Education and Alura.

## Description

Screen Sound Music is a Spring Boot application that manages music-related data, including artists and songs, with a PostgreSQL database integration.

## Technologies

- Java 24
- Spring Boot 3.5.0
- Spring Data JPA
- PostgreSQL
- dotenv-java for environment variable management
- OpenAI GPT-3 Java client

## Configuration

Configure your database connection in environment variables or `.env` file at the project root:

```env
DB_URL=jdbc:postgresql://localhost:5432/your_database_name
DB_USERNAME=your_username
DB_PASSWORD=your_password
```

If you prefer, you can override default username and password via environment variables or let them default to `postgres`.

## How to Run

1. Ensure you have Java 24 installed.
2. Set up PostgreSQL database and update the `.env` file or environment variables with your credentials.
3. Build the project using Maven:

```bash
mvn clean install
```

4. Run the application:

```bash
mvn spring-boot:run
```

## Environment Variables

The project uses the following environment variables:

- `DB_URL`: Database JDBC URL
- `DB_USERNAME`: Database username (default: `postgres`)
- `DB_PASSWORD`: Database password (default: `postgres`)

## License

This project is for educational purposes as part of the Oracle Next Education and Alura course challenge.
