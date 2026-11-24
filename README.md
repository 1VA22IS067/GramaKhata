# Grama-Khata
### Digital Ledger Application for Village Shopkeepers

Grama-Khata is an Android application designed to help small village shopkeepers manage customer credit records digitally instead of maintaining handwritten account books.

It provides a simple way to record customer transactions, track outstanding balances, manage customer details and send payment reminders.

## Problem Statement

Many small shopkeepers maintain customer credit records in traditional handwritten notebooks. These records can be difficult to maintain, search and update. Notebooks can also get lost or damaged, making it difficult to track pending payments.

Grama-Khata aims to simplify this process through a digital ledger that helps shopkeepers maintain customer accounts and track transactions in one place.

## Key Features

### 1. User Management
- User registration and login screens.
- Forgot password screen.
- English and Kannada language selection.

### 2. Customer Management
- Add and manage customer details.
- Store customer name and phone number.
- Maintain customer notes and due dates.
- Update and delete customer records.
- Search customers by name.

### 3. Digital Ledger – Give / Take
- **Give:** Record credit given to a customer and increase their outstanding balance.
- **Take:** Record payment received from a customer and reduce their outstanding balance.
- Automatically calculate customer balances.
- Maintain transaction history with amount, type, date and time.

### 4. Outstanding Balance Dashboard
- Display total outstanding balance.
- Show the total number of customers.
- Identify customers with high pending balances.
- Highlight customers with balances above ₹1,000.
- Sort customers by outstanding balance.

### 5. WhatsApp Payment Reminders
- Generate payment reminders containing customer details and pending balance.
- Open WhatsApp with a pre-filled reminder message.

### 6. Customer Ledger Sharing
- Share customer account details.
- Include customer information, current balance and transaction history.
- Use Android's native sharing functionality.

### 7. Transaction Reports
- Calculate total credit given.
- Calculate total payments collected.
- Display the net difference.
- View transaction records with customer names and timestamps.

## Technology Stack

| Technology | Purpose |
|---|---|
| Kotlin | Android application programming |
| Jetpack Compose | Declarative UI development |
| Material Design 3 | UI components and styling |
| Room Database | Local data persistence |
| SQLite | Underlying local database |
| Navigation Compose | Navigation between application screens |
| Kotlin Coroutines | Asynchronous database operations |
| KAPT | Room database code generation |
| Gradle Kotlin DSL | Build configuration |
| Android SDK | Android application development |

## Application Architecture

Grama-Khata uses a local database architecture with Room.

The application maintains three main database entities:

**User**
- User ID
- Name
- Phone number
- Password

**Customer**
- Customer ID
- Shopkeeper phone number
- Customer name
- Customer phone number
- Outstanding balance
- Notes
- Due date

**Transaction**
- Transaction ID
- Shopkeeper phone number
- Customer ID
- Transaction amount
- Transaction type (GIVE / TAKE)
- Date and time

Room provides database access through Data Access Objects (DAOs).

## Application Workflow

1. The shopkeeper registers or logs into the application.
2. The shopkeeper adds customer details.
3. Customer information is stored in the local Room database.
4. When goods are given on credit, the shopkeeper records a GIVE transaction.
5. When payment is received, the shopkeeper records a TAKE transaction.
6. The application updates the customer's outstanding balance.
7. The shopkeeper can view transaction history, reports and outstanding amounts.
8. Payment reminders and customer ledgers can be shared using WhatsApp and Android sharing.

## Project Structure

```text
GramaKhata/
│
├── app/
│   └── src/
│       └── main/
│           ├── java/com/example/gramakhata/
│           │   ├── MainActivity.kt
│           │   ├── AppLanguage.kt
│           │   ├── Customer.kt
│           │   ├── Transaction.kt
│           │   ├── Routes.kt
│           │   │
│           │   ├── data/
│           │   │   ├── AppDatabase.kt
│           │   │   ├── User.kt
│           │   │   ├── UserDao.kt
│           │   │   ├── CustomerDao.kt
│           │   │   └── TransactionDao.kt
│           │   │
│           │   ├── model/
│           │   │   └── Customer.kt
│           │   │
│           │   └── ui/
│           │       ├── LoginScreen.kt
│           │       ├── SignupScreen.kt
│           │       ├── ForgotPasswordScreen.kt
│           │       ├── HomeScreen.kt
│           │       ├── AddCustomerScreen.kt
│           │       ├── CustomerDetailScreen.kt
│           │       ├── DailyReportScreen.kt
│           │       └── theme/
│           │
│           └── res/
│
├── gradle/
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
└── README.md
```

## Getting Started

### Prerequisites

- Android Studio
- JDK 11 compatibility
- Android SDK
- An Android emulator or physical Android device

### 1. Clone the Repository

```bash
git clone https://github.com/1VA22IS067/GramaKhata.git
```

### 2. Open the Project

Open Android Studio and select **Open**.

Choose the downloaded `GramaKhata` project folder.

### 3. Sync Gradle

Allow Android Studio to sync the Gradle project and download the required dependencies.

### 4. Run the Application

Connect an Android device or start an emulator.

Click the **Run** button in Android Studio to build and launch Grama-Khata.

## Platform Requirements

- Minimum Android SDK: API 24
- Target Android SDK: API 36
- Application ID: `com.example.gramakhata`

## Project Notes

- Customer and transaction records are stored in a local Room database.
- The application is currently designed as a local Android application rather than a cloud-synchronised service.
- The current report screen summarizes the transactions loaded for the user; it does not filter them by calendar date.
- Authentication and password handling are prototype implementations and require a security review before production use.
- Database migrations currently use a destructive fallback, so database changes can result in previously stored local data being cleared.

## Project Objective

The goal of Grama-Khata is to provide small shopkeepers with a convenient digital alternative to handwritten credit books, making customer account management and transaction tracking easier.

## License

No separate license has been specified for this repository yet. Please check the repository's licensing before reusing or distributing the code.
