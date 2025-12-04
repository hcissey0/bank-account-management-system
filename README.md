# Bank Account Management System

A robust, Java-based Command Line Interface (CLI) application designed to simulate core banking operations. This system allows for efficient management of customers, accounts, and transactions with a focus on object-oriented design principles.

## ✨ Features

- **Customer Management**: 
  - Support for **Regular** and **Premium** customers.
  - Premium customers enjoy enhanced benefits and distinct validation rules.
  
- **Account Operations**:
  - **Savings Accounts**: Earn interest (3.5%) with minimum balance requirements.
  - **Checking Accounts**: Support overdraft protection with monthly fees.
  
- **Transaction Processing**:
  - Secure **Deposits** and **Withdrawals**.
  - Real-time balance updates and validation.
  - Comprehensive transaction history tracking.

- **Reporting**:
  - **Bank Statements**: Generate detailed statements with net change summaries.
  - **Transaction History**: View history per account or globally.

- **Integrated Testing**:
  - Custom built-in test runner accessible directly from the main menu.
  - Runs JUnit 5 tests to ensure system stability.

## ️Tech Stack

- **Language**: Java 17
- **Build Tool**: Maven
- **Testing**: JUnit 5, JUnit Platform Launcher
- **Logging**: Java Util Logging

## Getting Started

### Prerequisites
- Java Development Kit (JDK) 17 or higher
- Apache Maven

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/hcissey0/bank-account-management-system.git
   cd bank-account-management-system
   ```

2. **Build the project**
   ```bash
   mvn clean install
   ```

### Running the Application

You can run the application directly using Maven:

```bash
mvn exec:java
```

Or run the tests:

```bash
mvn test
```

##  Project Structure

```
src/main/java/
├── accounts/       # Account logic (Savings, Checking)
├── customers/      # Customer entities
├── main/           # Entry point (Main.java)
├── transactions/   # Transaction processing
└── utils/          # Helpers (InputReader, TestRunner)
```

##  Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request
