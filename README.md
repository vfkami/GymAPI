# GymAPI

The GymAPI is a simple REST API created using Kotlin and Spring Boot that manages a gym system. 
It's possible to register students (customers) and instructors. The API controls customers fees and allows 
instructors to create training sessions for their students.

# Requirements
- Kotlin 1.8.22
- Java 17
- PostgreSQL
- Good IDE like [IntelliJ](https://www.jetbrains.com/idea/) or code editor like [VSCode](https://code.visualstudio.com/)

# Usage

Assuming you have Maven in your path, just run in project root folder:
```
mvn spring-boot:run
```
# Requests

The Requests for GymAPI should follow this pattern:

| Método | Descrição                                     |
|--------|-----------------------------------------------|
| `GET`  | Returns information from one or more records. |
| `POST` | Used to create a new record.                  |
| `PUT`  | Update data in a record or change its status. |

If you need more details about the endpoins, see the [detailed API Documentation](API-README.md)
# Authors
- M.Sc Vinicius Queiroz - [Mail-me](mailto:viniciusqquei@gmail.com) 📩 

# TO-DO

This simple TODO list guides the project scope:

- [x]  Must connect to a PostgreSQL database;
- [x]  User ID must be generated using a pattern;
- [x]  Must generate a report of students who haven't paid the monthly fee;
- [x]  Must allow updating a student's fee payment;
- [x]  Must generate the next month's fee as soon as the current one is paid;
- [ ]  Must generate a report of students without training;
- [ ]  Must generate the current training of the student;
- [ ]  Must have authentication;
- [ ]  Password reset;
- [ ]  Must generate the next month's fee as soon as the current one expires;
- [ ]  Must calculate interest for fee payment;
- [ ]  Storage System Log;
- [ ]  Should perform unit tests (JUnity probably);

(This can be modified at any time)