# Overview

This documentation provides an overview of the available API endpoints for managing students (Alunos), instructors (Instrutores), and monthly fees (Mensalidades) in the Gym API.

## Aluno Requests

### GET All Alunos

- **HTTP Method:** GET
- **Endpoint:** `/aluno`
- **Description:** Retrieves a list of all alunos.
- **Response:**
    - Status Code: 200 OK
    - Body: List of objects representing students.

### GET Aluno By CPF

- **HTTP Method:** GET
- **Endpoint:** `/aluno/{cpf}`
- **Description:** Retrieves a student by their CPF (Brazilian taxpayer ID).
- **Parameters:**
    - `cpf` (Path Variable): The CPF of the student to retrieve.
- **Response:**
    - Status Code: 200 OK or 404 Not Found

### Save Aluno

- **HTTP Method:** POST
- **Endpoint:** `/aluno`
- **Description:** Registers a new student with the system.
- **Request Body (JSON):**
```
{
  "cpf": "123.456.789-10",
  "nome": "Anthony Kieds",
  "idade": "50",
  "login": "anthony.kieds",
  "peso": "80",
  "diaPagamento": "15",
  "valorMensalidade" : 150.50
}
```
- **Response:**
    - Status Code: 201 Created or 400 Bad Request or 409 Conflict;

## Instrutor Requests

### GET All Instrutores

- **HTTP Method:** GET
- **Endpoint:** `/instrutor`
- **Description:** Retrieves a list of all instructors.
- **Response:**
    - Status Code: 200 OK

### GET Instrutor By CPF

- **HTTP Method:** GET
- **Endpoint:** `/instrutor/{cpf}`
- **Description:** Retrieves an instructor by their CPF (Brazilian taxpayer ID).
- **Parameters:**
    - `cpf` (Path Variable): The CPF of the instructor to retrieve.
- **Response:**
    - Status Code: 200 OK or 404 Not Found

### Save Instrutor

- **HTTP Method:** POST
- **Endpoint:** `/instrutor`
- **Description:** Registers a new instructor with the system.
- **Request Body (JSON):**
```
{
  "cpf": "123.456.789-10",
  "nome": "Flea",
  "idade": "35",
  "login": "flea.rhcp",
  "salario": "500",
  "cfe" : "0000XYZ"
  }
```
- **Response:**
    - Status Code: 201 Created or 400 Bad Request or 409 Conflict


## Mensalidade Requests

### GET All Mensalidades By Matricula

- **HTTP Method:** GET
- **Endpoint:** `/mensalidade/{matricula}`
- **Description:** Retrieves all monthly fees for a specific matricula (student ID).
- **Parameters:**
  - `matricula` (Path Variable): The matricula (student ID) for which to retrieve monthly fees.
- **Response:**
  - Status Code: 200 OK
  - Body: List of MensalidadeModel objects representing monthly fees.

### GET Unpaid Mensalidades By Matricula

- **HTTP Method:** GET
- **Endpoint:** `/mensalidade/naoPago/{matricula}`
- **Description:** Retrieves unpaid monthly fees for a specific matricula (student ID).
- **Parameters:**
  - `matricula` (Path Variable): The matricula (student ID) for which to retrieve unpaid monthly fees.
- **Response:**
  - Status Code: 200 OK or 404 Not Found

### Mark Mensalidade as Paid

- **HTTP Method:** PUT
- **Endpoint:** `/mensalidade/pagar/{id}`
- **Description:** Marks a specific monthly fee as paid.
- **Parameters:**
  - `id` (Path Variable): The unique ID of the monthly fee to mark as paid (UUID).
- **Response:**
  - Status Code: 200 OK or 400 Bad Request or 409 Conflict

### GET All Unpaid Mensalidades

- **HTTP Method:** GET
- **Endpoint:** `/mensalidade/naoPago`
- **Description:** Retrieves all unpaid monthly fees.
- **Response:**
  - Status Code: 200 OK or 404 Not Found

### GET All Overdue Mensalidades

- **HTTP Method:** GET
- **Endpoint:** `/mensalidade/atrasado`
- **Description:** Retrieves all overdue (past due date) monthly fees.
- **Response:**
  - Status Code: 200 OK or 404 Not Found