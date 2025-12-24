# ✈️ Nimbus Airlines - Flight Booking Management System

A comprehensive full-stack airline management system built with Spring Boot and vanilla JavaScript, featuring real-time seat selection, connecting flight bookings, multi-class pricing, and dynamic flight date management.

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.6-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue.svg)](https://www.mysql.com/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

## 📋 Table of Contents

- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Architecture](#-architecture)
- [Installation](#-installation)
- [Configuration](#-configuration)
- [Usage](#-usage)
- [API Documentation](#-api-documentation)
- [Database Schema](#-database-schema)
- [Screenshots](#-screenshots)
- [Contributing](#-contributing)
- [License](#-license)

## 🌟 Features

### User Features
- **🔐 User Authentication** - Secure login/signup with session management
- **🔍 Smart Flight Search** - Search for direct and connecting flights
- **📅 Browse Available Flights** - View all upcoming flights with advanced filters
- **🔄 Auto-Date Management** - Flights automatically updated to current month/year
- **💺 Visual Seat Selection** - Interactive seat map with real-time availability
- **🎯 Multi-Class Booking** - Economy, Business, and First Class options
- **🔄 Connecting Flights** - Book multi-leg journeys with layover validation (1-6 hours)
- **🧳 Luggage Management** - Automatic calculation based on class (Economy: 20kg, Business: 30kg, First: 40kg)
- **💳 Payment Gateway** - Multiple payment methods (Card, UPI, Net Banking)
- **🎫 PDF Ticket Generation** - Download printable tickets with QR codes
- **📱 Booking Management** - View, modify, and cancel bookings
- **👤 Profile Management** - Update personal information

### Admin Features
- **📊 Dashboard Analytics** - Total flights, bookings, and revenue tracking
- **✈️ Flight Management** - CRUD operations for flight schedules
- **👥 Passenger Management** - View and manage passenger data
- **📋 Booking Overview** - Monitor all bookings with status filters
- **💰 Revenue Tracking** - Real-time revenue calculations

### Technical Features
- **🔒 Session-Based Authentication** - Mutual exclusion between user/admin sessions
- **🪑 Dynamic Seat Allocation** - JSON-based multi-seat storage for connecting flights
- **💵 Class-Based Pricing** - Automatic price calculation (Economy: 1x, Business: 2x, First: 3x)
- **🔁 Seat Release on Cancellation** - Automatic seat availability restoration
- **📆 Dynamic Date Updates** - SQL-based automatic flight date management on startup
- **🔍 Smart Flight Filtering** - Filter by source, destination, and date
- **🔗 Search Autofill** - Pre-populate search from browsed flights
- **🌐 RESTful API** - Clean, well-documented endpoints
- **📱 Responsive Design** - Mobile-friendly interface

## 🛠️ Tech Stack

### Backend
- **Java 21** - Core programming language
- **Spring Boot 3.5.6** - Application framework
- **Spring Data JPA** - Database ORM
- **Hibernate** - JPA implementation
- **MySQL 8.0** - Relational database
- **iText7** - PDF generation
- **Lombok** - Boilerplate code reduction
- **Maven** - Dependency management

### Frontend
- **HTML5/CSS3** - Structure and styling
- **Vanilla JavaScript** - Client-side logic
- **Fetch API** - HTTP requests
- **LocalStorage** - Session management

### Development Tools
- **Git** - Version control
- **IntelliJ IDEA / Eclipse** - IDE
- **MySQL Workbench** - Database management
- **Postman** - API testing

## 🏗️ Architecture

```
nimbus-airlines/
│
├── airline-backend/                 # Spring Boot Backend
│   ├── src/main/java/com/airline/nimbus/
│   │   ├── controller/              # REST Controllers
│   │   │   ├── AdminController.java
│   │   │   ├── BookingController.java
│   │   │   ├── FlightController.java       # Updated: New endpoints
│   │   │   ├── PassengerController.java
│   │   │   ├── PaymentController.java
│   │   │   ├── SeatController.java
│   │   │   └── TicketController.java
│   │   │
│   │   ├── model/                   # Entity Classes
│   │   │   ├── Admin.java
│   │   │   ├── Booking.java
│   │   │   ├── Flight.java
│   │   │   ├── Passenger.java
│   │   │   ├── Payment.java
│   │   │   └── Seat.java
│   │   │
│   │   ├── repository/              # JPA Repositories
│   │   │   ├── AdminRepository.java
│   │   │   ├── BookingRepository.java
│   │   │   ├── FlightRepository.java       # Updated: Query methods
│   │   │   ├── PassengerRepository.java
│   │   │   ├── PaymentRepository.java
│   │   │   └── SeatRepository.java
│   │   │
│   │   ├── service/                 # Business Logic
│   │   │   ├── AdminService.java
│   │   │   ├── BookingService.java
│   │   │   ├── FlightService.java          # Updated: New methods
│   │   │   ├── PassengerService.java
│   │   │   ├── PaymentService.java
│   │   │   ├── SeatService.java
│   │   │   └── TicketService.java
│   │   │
│   │   ├── config/                  # Configuration
│   │   │   └── CorsConfig.java
│   │   │
│   │   └── NimbusAirlinesApplication.java
│   │
│   ├── src/main/resources/
│   │   ├── application.properties          # Updated: SQL init config
│   │   └── data.sql                        # New: Auto-date update script
│   │
│   └── pom.xml                      # Maven Dependencies
│
├── airline-frontend/                # Frontend Application
│   ├── css/
│   │   └── style.css
│   ├── js/
│   │   └── script.js
│   ├── index.html                   # Updated: New nav links
│   ├── login.html                   # User Login
│   ├── signup.html                  # User Registration
│   ├── available-flights.html       # New: Browse flights page
│   ├── search.html                  # Updated: Autofill support
│   ├── booking.html                 # Booking Form
│   ├── payment.html                 # Payment Gateway
│   ├── ticket.html                  # Ticket Display
│   ├── my-bookings.html             # User Bookings
│   ├── profile.html                 # User Profile
│   └── admin.html                   # Admin Dashboard
│
└── database/
    └── airline.sql                  # Database Schema & Sample Data
```

## 📦 Installation

### Prerequisites
- Java 21 or higher
- MySQL 8.0 or higher
- Maven 3.6+
- Git

### Step 1: Clone the Repository
```bash
git clone https://github.com/anindita-19/Airline-Management-System.git
cd Airline-Management-System
```

### Step 2: Setup Database
```bash
# Login to MySQL
mysql -u root -p

# Create database
CREATE DATABASE airline_db;

# Import schema and data
mysql -u root -p airline_db < database/airline.sql
```

### Step 3: Configure Backend
Update `airline-backend/src/main/resources/application.properties`:

```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/airline_db
spring.datasource.username=YOUR_MYSQL_USERNAME
spring.datasource.password=YOUR_MYSQL_PASSWORD

# Server Port
server.port=8080

# SQL Initialization (for auto-date updates)
spring.sql.init.mode=always
spring.jpa.defer-datasource-initialization=true
```

### Step 4: Build and Run Backend
```bash
cd airline-backend

# Using Maven
mvn clean install
mvn spring-boot:run

# Or using Maven Wrapper
./mvnw clean install
./mvnw spring-boot:run
```

The backend will start at `http://localhost:8080`

**Note:** On startup, the system automatically updates flight dates to the current month/year.

### Step 5: Run Frontend
```bash
cd airline-frontend

# Using Live Server (VS Code extension)
# Or any static file server

# Using Python
python -m http.server 5500

# Using Node.js http-server
npx http-server -p 5500
```

The frontend will be available at `http://localhost:5500`

## ⚙️ Configuration

### Default Admin Credentials
```
Email: admin@skywings.com
Password: admin123

Email: manager@skywings.com
Password: manager123
```

### Default User Credentials
```
Email: rahul@test.com
Password: password123

(See airline.sql for more test users)
```

### CORS Configuration
The backend is configured to accept requests from:
- `http://localhost:5500`
- `http://localhost:5501`
- `http://127.0.0.1:5500`
- `http://127.0.0.1:5501`

Update `CorsConfig.java` to add more origins if needed.

### Dynamic Date Management
The system includes an automatic date update feature:
- **On Startup**: Flights with past dates are updated to current month/year
- **Day Preservation**: The day component (e.g., 12, 25) remains unchanged
- **Edge Cases**: Handles invalid dates (e.g., Feb 30 → Feb 28/29)
- **Configuration**: Set `spring.sql.init.mode=never` in production after initial deployment

## 🚀 Usage

### User Workflow
1. **Register/Login** → Create account or login with existing credentials
2. **Browse Available Flights** → View all upcoming flights with filters (NEW!)
3. **Search Flights** → Enter source, destination, and date OR use autofill from browsed flights
4. **Select Flight** → Choose from direct or connecting flights
5. **Choose Class** → Economy, Business, or First Class
6. **Select Seats** → Pick seats from interactive seat map
7. **Enter Luggage** → Specify luggage weight (free limits apply)
8. **Review Booking** → Check booking summary
9. **Payment** → Complete payment via Card/UPI/Net Banking
10. **Download Ticket** → Get PDF ticket with booking details

### Admin Workflow
1. **Login** → Access admin dashboard
2. **View Analytics** → Monitor flights, bookings, revenue
3. **Manage Flights** → Add, edit, or delete flights
4. **View Bookings** → Track all passenger bookings
5. **Manage Passengers** → View passenger information

## 📡 API Documentation

### Authentication
```http
POST /api/passengers/signup
POST /api/passengers/login
POST /api/admin/login
```

### Flights
```http
GET    /api/flights
GET    /api/flights/{id}
GET    /api/flights/upcoming                                              # New: All future flights
GET    /api/flights/current-month                                        # New: This month's flights
GET    /api/flights/search?source=DEL&destination=MUM&date=2025-10-28
GET    /api/flights/search/connecting?source=DEL&destination=HYD&date=2025-10-28
POST   /api/flights
PUT    /api/flights/{id}
DELETE /api/flights/{id}
```

### Bookings
```http
GET    /api/bookings
GET    /api/bookings/{id}
GET    /api/bookings/passenger/{passengerId}
POST   /api/bookings
PUT    /api/bookings/{id}/cancel
```

### Seats
```http
GET    /api/seats/flight/{flightId}
GET    /api/seats/flight/{flightId}/available
GET    /api/seats/flight/{flightId}/class/{seatClass}
POST   /api/seats/book
```

### Payments
```http
POST   /api/payments
GET    /api/payments/{id}
```

### Tickets
```http
GET    /api/tickets/{bookingId}/download
```

### Example Request/Response

**GET /api/flights/upcoming**
```json
[
  {
    "id": 27,
    "flightNumber": "NB101",
    "source": "DEL",
    "destination": "MUM",
    "departureDate": "2025-12-28",
    "departureTime": "08:00:00",
    "arrivalTime": "10:30:00",
    "airline": "Nimbus Air",
    "totalSeats": 190,
    "availableSeats": 190,
    "economyPrice": 5000.0,
    "businessPrice": 10000.0,
    "firstClassPrice": 15000.0
  }
]
```

**POST /api/bookings**
```json
{
  "passengerId": 1,
  "flightId": 27,
  "numberOfSeats": 1,
  "flightClass": "BUSINESS",
  "seatNumber": "11A",
  "luggageWeight": 53,
  "isConnecting": true,
  "connectingFlightId": 22,
  "seatNumbers": {
    "flight1": "11A",
    "flight2": "15B"
  }
}
```

**Response**
```json
{
  "id": 21,
  "passenger": {...},
  "flight": {...},
  "bookingDate": "2024-10-24T12:39:20",
  "status": "CONFIRMED",
  "numberOfSeats": 1,
  "totalAmount": 14830.0,
  "flightClass": "BUSINESS",
  "luggageWeight": 53.0,
  "luggageCharges": 230.0,
  "isConnecting": true,
  "connectingFlightId": 22,
  "seatNumbers": "{\"flight1\":\"11A\",\"flight2\":\"15B\"}"
}
```

## 🗄️ Database Schema

### Key Tables

**passengers**
- id, firstName, lastName, email, phone, password, age, gender, photoUrl

**flights**
- id, flightNumber, airline, source, destination
- departureDate, departureTime, arrivalTime
- price, economyPrice, businessPrice, firstClassPrice
- totalSeats, availableSeats
- economySeats, businessSeats, firstClassSeats

**bookings**
- id, passenger_id, flight_id
- bookingDate, status, numberOfSeats, totalAmount
- seatNumber, seatNumbers (JSON), flightClass
- luggageWeight, luggageCharges
- isConnecting, connectingFlightId

**seats**
- id, flight_id, seatNumber, seatClass, isAvailable

**payments**
- id, booking_id, amount, paymentMethod, paymentStatus
- transactionId, paymentDate

**admins**
- id, username, email, password

## 📸 Screenshots

### User Interface
- **Home Page** - Landing page with features
- **Available Flights** - Browse all upcoming flights with filters (NEW!)
- **Flight Search** - Direct and connecting flight results with autofill
- **Seat Selection** - Interactive seat map
- **Booking Summary** - Detailed booking information
- **Payment Gateway** - Multiple payment options
- **Ticket** - Downloadable PDF ticket

### Admin Dashboard
- **Analytics** - Revenue and booking statistics
- **Flight Management** - Add/Edit/Delete flights
- **Booking Management** - View all bookings
- **Passenger Management** - View passenger details

## 🔧 Key Features Implementation

### Dynamic Flight Date Management
```sql
-- data.sql - Automatic date update on startup
UPDATE flights
SET departure_date = DATE_FORMAT(
    CONCAT(YEAR(CURDATE()), '-', MONTH(CURDATE()), '-', DAY(departure_date)),
    '%Y-%m-%d'
)
WHERE departure_date < CURDATE()
   OR YEAR(departure_date) != YEAR(CURDATE())
   OR MONTH(departure_date) != MONTH(CURDATE());
```

### Available Flights Browsing
```javascript
// FlightRepository.java - Query upcoming flights
@Query("SELECT f FROM Flight f WHERE f.departureDate >= :today 
        ORDER BY f.departureDate ASC, f.departureTime ASC")
List<Flight> findUpcomingFlights(LocalDate today);
```

### Search Autofill Feature
```javascript
// available-flights.html → search.html
// Users can click "Search This Route" to autofill search form
localStorage.setItem('searchSource', source);
localStorage.setItem('searchDestination', destination);
localStorage.setItem('searchDate', date);
```

### Multi-Seat Selection for Connecting Flights
```javascript
// Booking.java - Multi-seat storage
@Column(length = 1000)
private String seatNumbers; // JSON: {"flight1": "12A", "flight2": "14C"}
```

### Class-Based Pricing
- **Economy**: Base price
- **Business**: 2x Economy price
- **First Class**: 3x Economy price

### Luggage Charges
- **Economy**: Free 20kg, ₹10/kg extra
- **Business**: Free 30kg, ₹10/kg extra
- **First**: Free 40kg, ₹10/kg extra

### Connecting Flight Algorithm
- Finds flights with layover between 1-6 hours
- Validates time compatibility
- Calculates total journey price

## 🐛 Troubleshooting

### Backend Won't Start
```bash
# Check if port 8080 is in use
netstat -ano | findstr :8080

# Update port in application.properties
server.port=8081
```

### Database Connection Error
```bash
# Verify MySQL is running
mysql -u root -p

# Check credentials in application.properties
# Ensure database 'airline_db' exists
```

### Flight Dates Not Updating
```bash
# Verify data.sql is in src/main/resources/
# Check application.properties has:
spring.sql.init.mode=always
spring.jpa.defer-datasource-initialization=true

# Check console logs for SQL execution errors
```

### Available Flights Page Shows No Data
```bash
# Test API endpoint directly
curl http://localhost:8080/api/flights/upcoming

# Ensure at least one flight has departure_date >= today
# Check browser console for errors
```

### CORS Issues
- Ensure frontend runs on allowed ports (5500, 5501)
- Update `CorsConfig.java` if using different ports

### Session Issues
- Clear browser localStorage
- Logout and login again

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add AmazingFeature'`)
4. Push to branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👥 Authors

- **Anindita** - [GitHub Profile](https://github.com/anindita-19)

## 🙏 Acknowledgments

- Spring Boot Documentation
- MySQL Community
- iText PDF Library
- All contributors and testers

## 📧 Contact

For questions or support:
- **GitHub**: [@anindita-19](https://github.com/anindita-19)
- **Repository**: [Airline Management System](https://github.com/anindita-19/Airline-Management-System.git)

---

⭐ Star this repo if you find it helpful!

**Made with ❤️ by Anindita**