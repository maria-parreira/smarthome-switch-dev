# Smart Home Project

This project was developed by a group of students as part of the postgraduate program in Software Development (Switch) at the Instituto de Engenharia do Porto, during the academic year 2023-2024.

## Introduction

A Smart Home is a house or apartment that uses IT and networked devices to improve comfort, safety, security, and energy efficiency. Another important characteristic of many smart homes is the presence of electric power generation (e.g., solar panels) and/or storage (e.g., batteries).

For more information, you can refer to this [definition of a smart home](https://www.techtarget.com/iotagenda/definition/smart-home-or-building).

## Objective

The objective of this project is to develop a system that allows users to manage their smart homes, including the management of devices that encompass sensor and/or actuator behavior.

## Non-Functional Requirements

- Agile approach.
- Java programming language.
- Test-Driven Development (TDD) approach.
- Minimum 95% of code coverage and mutation coverage of tests.
- Automatic and regression tests: unit tests (with isolation) must be adopted for domain classes, and integration tests (without isolation) must be adopted for controllers.
- All code, comments, and documentation in English.
- Project wiki in the GitHub repository.
- Visual representation using the C4 and 4+1 models.
- User interface developed using ReactJS.
- Responsive user interface (adapt the design of the forms to the type of device used by the user).
- Support persistence of information in a relational DB server.
- Obtain weather information from a specialized service through a REST API.

## Domain Model

![Domain Model](Documentation/uml/Wiki/DomainModel/DomainModel.png)

## Getting Started

To get started with this project, follow these steps:

To set up the project locally, you will need:
- Java 17 or higher
- Maven 3.13.0 or higher

1. **Clone the repository:**
    ```sh
    git clone https://github.com/your-repository-url.git
    ```
2. **Navigate to the project directory:**
    ```sh
    cd smart-home-project
    ```
3. **Install dependencies:**
    ```sh
    mvn install
    ```
4. **Run the backend:**
    ```sh
    mvn spring-boot:run
    ```
   
5. **Access the user interface:**
   ```sh
   cd frontend
   npm install
   npm run dev
   ```
6. **Hosting System on Tomcat Server and Connecting to Database Container**

   - [for more info see this README.md](documentation/README.md)


## Contributors

- [Maria Parreira](https://github.com/mariaparreira-code)
- [Pedro Fernandes](https://github.com/PedroMSFernandes5)
- [Ricardo Araujo](https://github.com/RicardoAraujoSwitch)
- [Inês Guedes](https://github.com/InesGuedes-Switch)
- [Filipa Cardoso](https://github.com/filipacardoso)
- [Maria Neto](https://github.com/maria-neto)
- [Pedro Gil](https://github.com/PedroHGill)
- [Francisco Martins](https://github.com/FranciscoRamosMartins)
- [Tiago Pereira](https://github.com/tiagopereiraswitch)
- [Tomás Ferreira](https://github.com/1141142TF)
