# Wallet App

![wallet-money-cartoon](https://github.com/user-attachments/assets/3818ae7a-06c7-4dc3-aad2-d7f0acc3b0a1)

## Description

This project is a full-stack application that allows users to manage their Ethereum and USD wallets. Users can perform operations such as buying and selling Ethereum, and transaction logs are maintained for each user. The backend is built using Spring Boot, and the frontend is developed using Vue.js.

### Key Features
- User registration and authentication
- Main Wallet with two SubWallets: Ethereum and USD
- Buy and sell Ethereum functionality
- Transaction logging and history
- RESTful APIs for wallet operations
- Secure password handling with encryption

## Technologies Used

### Backend
- **Spring Boot**: Framework used for building the backend API.
- **Hibernate**: ORM tool for handling database operations.
- **MongoDB**: Database for storing user, wallet, and transaction information.
- **Spring Security**: For securing user authentication and authorization.
- **Spring Data JPA**: For data persistence and repository management.

### Frontend
- **React**: Framework used for building the frontend application.
- **Tailwind CSS**: For responsive and modern UI design.

## Getting Started

### Prerequisites
- Java 21
- Node.js
- MongoDB

### Backend Setup

1. Clone the repository:
    ```sh
    git clone ....
    ```
2. Navigate to the backend directory:
    ```sh
    cd TransactionSystemBackend
    ```
3. Configure the database connection in `application.properties`:
    ```properties
       spring.application.name=TransactionSystem

       spring.data.mongodb.uri=mongodb://localhost:27017/transaction_system
       spring.data.mongodb.database=transaction_system


     jwt.secret=wJalrXUtnFEMI/K7MDENG+bPxRfiCYEXAMPLEKEY


      spring.jpa.hibernate.ddl-auto=create-update
    ```
4. Build and run the backend application:
    ```sh
    ./mvnw spring-boot:run
    ```

### Frontend Setup

1. Navigate to the frontend directory:
    ```sh
    cd TransactionSystemfrontend
    ```
2. Install dependencies:
    ```sh
    npm install
    ```
3. Run the frontend application:
    ```sh
    npm run dev
    ```

## Contributors

- Amjad Khaliliah

