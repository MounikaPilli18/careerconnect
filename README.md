# CareerConnect

## Project Overview

CareerConnect is a Spring Boot REST API project developed to manage job postings. It provides CRUD (Create, Read, Update, Delete) operations with validation, exception handling, and DTO implementation.

This project is built as part of my Java Full Stack learning journey.

---

## Features

- Add a new job
- View all jobs
- View a job by ID
- Update job details
- Delete a job
- Input validation using Bean Validation
- Global exception handling
- DTO (Data Transfer Object) implementation
- RESTful API development

---

## Technologies Used

- Java 21
- Spring Boot
- Spring Data JPA
- Hibernate
- MySQL
- Maven
- Postman
- Git
- GitHub

---

## Project Structure

```
src
 ├── controller
 ├── service
 ├── repository
 ├── entity
 ├── dto
 ├── exception
 └── resources
```

---

## API Endpoints

| Method | Endpoint | Description |
|---------|----------|-------------|
| POST | /jobs | Create a new job |
| GET | /jobs | Get all jobs |
| GET | /jobs/{id} | Get job by ID |
| PUT | /jobs | Update job |
| DELETE | /jobs/{id} | Delete job |

---

## Validation

The project validates:

- Job Title
- Company Name
- Location
- Description
- Salary must be greater than zero

---

## Exception Handling

Custom Exception:

- JobNotFoundException

Global Exception Handling:

- Validation Errors
- Resource Not Found Errors

---

## DTO

The project uses JobDTO to separate API requests/responses from the database entity.

---

## Tools Used

- Eclipse IDE
- MySQL Workbench
- Postman
- Git
- GitHub

---

## Future Enhancements

- Search jobs by title
- Search by company
- Search by location
- Pagination and Sorting
- Swagger API Documentation
- Authentication and Authorization (Spring Security + JWT)

---

## Author

**Mounika Pilli**

GitHub:
https://github.com/MounikaPilli18