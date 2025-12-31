# ✈️ Nimbus Airlines - Flight Booking Management System

A comprehensive full-stack airline management system built with Spring Boot and vanilla JavaScript, featuring real-time seat selection, connecting flight bookings, multi-class pricing, public flight search, and rolling 30-day flight availability with automatic health monitoring.

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.6-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue.svg)](https://www.mysql.com/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
[![Live Demo](https://img.shields.io/badge/Live-Demo-success.svg)](https://nimbus-airlines.netlify.app)

## 📋 Table of Contents

- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Architecture](#-architecture)
- [Installation](#-installation)
- [Configuration](#-configuration)
- [Deployment](#-deployment)
- [Usage](#-usage)
- [API Documentation](#-api-documentation)
- [Database Schema](#-database-schema)
- [Screenshots](#-screenshots)
- [Contributing](#-contributing)
- [License](#-license)

## 🌟 Features

### User Features
- **🌐 Public Flight Search** - Browse and search flights without login (NEW!)
- **🔐 Secure Authentication** - Login/signup required only for booking actions
- **🔍 Smart Flight Search** - Search for direct and connecting flights
- **📅 Rolling 30-Day Availability** - Always see flights for the next 30 days (NEW!)
- **📊 Browse Available Flights** - View all upcoming flights with advanced filters
- **💺 Visual Seat Selection** - Interactive seat map with real-time availability
- **🎯 Multi-Class Booking** - Economy, Business, and First Class options
- **🔄 Connecting Flights** - Book multi-leg journeys with layover validation (1-8 hours)
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

### Technical Features (NEW!)
- **🔓 Public Flight Search** - No authentication required for browsing flights
- **🔒 Protected Actions** - Login required for booking, payments, profile management
- **📅 Rolling Date Logic** - 30-day window instead of month-based (prevents end-of-month issues)
- **🏥 Health Monitoring** - Lightweight `/health` endpoint for uptime monitoring
- **⚡ Cold Start Prevention** - UptimeRobot integration to keep backend active
- **🔐 Spring Security Integration** - Granular endpoint protection
- **🪑 Dynamic Seat Allocation** - JSON-based multi-seat storage for connecting flights
- **💵 Class-Based Pricing** - Automatic price calculation (Economy: 1x, Business: 2x, First: 3x)
- **🔁 Seat Release on Cancellation** - Automatic seat availability restoration
- **🔍 Smart Flight Filtering** - Filter by source, destination, and date
- **🔗 Search Autofill** - Pre-populate search from browsed flights
- **🌐 RESTful API** - Clean, well-documented endpoints with CORS support
- **📱 Responsive Design** - Mobile-friendly interface
- **☁️ Cloud Deployment** - Render (Backend) + Netlify (Frontend) + Railway (Database)

## 🛠️ Tech Stack

### Backend
- **Java 21** - Core programming language
- **Spring Boot 3.5.6** - Application framework
- **Spring Security** - Authentication and authorization (NEW!)
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

### Cloud Infrastructure (NEW!)
- **Render** - Backend hosting (Spring Boot)
- **Netlify** - Frontend hosting (Static files)
- **Railway** - MySQL database hosting
- **UptimeRobot** - Health monitoring and cold start prevention

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
│   │   │   ├── HealthController.java       # NEW: Health monitoring
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
│   │   │   ├── FlightRepository.java       # Updated: Rolling date queries
│   │   │   ├── PassengerRepository.java
│   │   │   ├── PaymentRepository.java
│   │   │   └── SeatRepository.java
│   │   │
│   │   ├── service/                 # Business Logic
│   │   │   ├── AdminService.java
│   │   │   ├── BookingService.java
│   │   │   ├── FlightService.java          # Updated: 30-day logic
│   │   │   ├── PassengerService.java
│   │   │   ├── PaymentService.java
│   │   │   ├── SeatService.java
│   │   │   └── TicketService.java
│   │   │
│   │   ├── config/                  # Configuration
│   │   │   ├── CorsConfig.java              # Updated: Multiple origins
│   │   │   └── SecurityConfig.java          # NEW: Spring Security config
│   │   │
│   │   └── NimbusAirlinesApplication.java
│   │
│   ├── src/main/resources/
│   │   ├── application.properties          # Updated: Cloud database config
│   │   └── data.sql                        # Updated: Rolling date logic
│   │
│   └── pom.xml                      # Maven Dependencies
│
├── airline-frontend/                # Frontend Application
│   ├── css/
│   │   └── style.css
│   ├── js/
│   │   ├── script.js                       # Updated: Conditional auth checks
│   │   └── config.js                       # NEW: API endpoint configuration
│   ├── index.html                   # Updated: New nav links
│   ├── login.html                   # User Login
│   ├── signup.html                  # User Registration
│   ├── available-flights.html       # Browse flights page
│   ├── search.html                  # Updated: Public access, login for booking
│   ├── booking.html                 # Booking Form (Protected)
│   ├── payment.html                 # Payment Gateway (Protected)
│   ├── ticket.html                  # Ticket Display (Protected)
│   ├── my-bookings.html             # User Bookings (Protected)
│   ├── profile.html                 # User Profile (Protected)
│   └── admin.html                   # Admin Dashboard (Protected)
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

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Server Port
server.port=8080

# SQL Initialization (for rolling date updates)
spring.sql.init.mode=always
spring.jpa.defer-datasource-initialization=true
```

### Step 4: Add Spring Security Dependency
Ensure `pom.xml` includes:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

### Step 5: Build and Run Backend
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

**Note:** On startup, the system automatically updates flight dates to use rolling 30-day windows.

### Step 6: Configure Frontend
Create `airline-frontend/js/config.js`:

```javascript
const CONFIG = {
    API_BASE_URL: 'http://localhost:8080'
};
```

### Step 7: Run Frontend
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
- `http://localhost:5500` / `http://localhost:5501` (Local development)
- `http://127.0.0.1:5500` / `http://127.0.0.1:5501` (Alternative localhost)
- `https://airline-backend-i4wj.onrender.com` (Production backend)
- `https://nimbus-airlines.netlify.app` (Production frontend)

Update `CorsConfig.java` to add more origins if needed.

### Security Configuration (NEW!)
Spring Security is configured to:
- **Public Endpoints** (No authentication required):
  - `/health` - Health check for monitoring
  - `/ping` - Alternative health check
  - `/api/passengers/login` - User login
  - `/api/passengers/signup` - User registration
  - `/api/admin/login` - Admin login
  - `/api/flights/**` - All flight search and browse endpoints

- **Protected Endpoints** (Authentication required):
  - `/api/bookings/**` - Booking operations
  - `/api/payments/**` - Payment processing
  - `/api/passengers/{id}` - Passenger profile management
  - All other endpoints

### Rolling Date Logic (NEW!)
The system uses a **30-day rolling window** instead of month-based logic:
- **Flights shown:** Today + next 30 days
- **No end-of-month gaps:** Works seamlessly across month/year boundaries
- **Automatic updates:** On startup via `data.sql`
- **Sample data:** Flights distributed across the 30-day window

```sql
-- Flights are shown from CURDATE() to DATE_ADD(CURDATE(), INTERVAL 30 DAY)
-- This prevents "no flights on last day of month" issues
```

## 🚀 Deployment

### Backend Deployment (Render)

1. **Create Render Account** at https://render.com

2. **Create Web Service**:
   - Connect your GitHub repository
   - **Build Command:** `mvn clean install -DskipTests`
   - **Start Command:** `java -jar target/airline-backend-0.0.1-SNAPSHOT.jar`
   - **Environment:** Docker
   - **Plan:** Free

3. **Configure Environment Variables**:
   ```
   SPRING_DATASOURCE_URL=jdbc:mysql://your-railway-host:3306/railway
   SPRING_DATASOURCE_USERNAME=root
   SPRING_DATASOURCE_PASSWORD=your-password
   SPRING_JPA_HIBERNATE_DDL_AUTO=update
   SPRING_SQL_INIT_MODE=always
   ```

4. **Note:** Render free tier sleeps after 15 minutes of inactivity (see UptimeRobot setup below)

### Database Deployment (Railway)

1. **Create Railway Account** at https://railway.app

2. **Deploy MySQL**:
   - Create new project → Add MySQL
   - Note connection details (host, port, username, password, database)

3. **Import Schema**:
   ```bash
   mysql -h your-railway-host -u root -p railway < database/airline.sql
   ```

### Frontend Deployment (Netlify)

1. **Create Netlify Account** at https://netlify.com

2. **Deploy from GitHub**:
   - Connect repository
   - **Build command:** Leave empty (static site)
   - **Publish directory:** `airline-frontend`

3. **Update `config.js`**:
   ```javascript
   const CONFIG = {
       API_BASE_URL: 'https://your-app.onrender.com'
   };
   ```

4. **Redeploy** after config update

### Health Monitoring Setup (UptimeRobot) - NEW!

To prevent Render cold starts:

1. **Create UptimeRobot Account** at https://uptimerobot.com

2. **Add Monitor**:
   - **Monitor Type:** HTTP(s)
   - **Friendly Name:** Nimbus Airlines Health Check
   - **URL:** `https://your-app.onrender.com/health`
   - **Monitoring Interval:** 5 minutes
   - **Monitor Timeout:** 30 seconds

3. **Verify Status**:
   - Wait 5-10 minutes
   - Check monitor shows "Up" status
   - Your backend will now stay active!

**Why This Works:**
- Render free tier sleeps after 15 minutes
- UptimeRobot pings every 5 minutes
- Keeps your backend awake and responsive
- No more 30-second cold start delays

## 🚀 Usage

### User Workflow (NEW: Public Search!)
1. **Browse Flights (No Login)** → View all upcoming flights without authentication
2. **Search Flights (No Login)** → Search direct/connecting flights publicly
3. **Register/Login (For Booking)** → Login required only when clicking "Book Now"
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

### Health Check (NEW!)
```http
GET /health               # Health check for monitoring
GET /ping                 # Alternative health endpoint
```

**Response:**
```json
{
  "status": "UP",
  "service": "Nimbus Airlines Backend",
  "timestamp": "2025-01-05 14:30:00",
  "message": "Service is running"
}
```

### Authentication (PUBLIC - No Auth Required)
```http
POST /api/passengers/signup
POST /api/passengers/login
POST /api/admin/login
POST /api/admin/create
```

### Flights (PUBLIC - No Auth Required)
```http
GET    /api/flights                                                      # All flights
GET    /api/flights/{id}                                                 # Single flight
GET    /api/flights/upcoming                                            # Next 30 days (NEW!)
GET    /api/flights/current-month                                       # Alias for upcoming
GET    /api/flights/search?source=DEL&destination=MUM&date=2025-01-15   # Direct flights
GET    /api/flights/search/connecting?source=DEL&destination=HYD&date=2025-01-15
```

### Flights Management (PROTECTED - Auth Required)
```http
POST   /api/flights
PUT    /api/flights/{id}
DELETE /api/flights/{id}
```

### Bookings (PROTECTED - Auth Required)
```http
GET    /api/bookings
GET    /api/bookings/{id}
GET    /api/bookings/passenger/{passengerId}
POST   /api/bookings
PUT    /api/bookings/{id}/cancel
```

### Seats (PROTECTED - Auth Required)
```http
GET    /api/seats/flight/{flightId}
GET    /api/seats/flight/{flightId}/available
GET    /api/seats/flight/{flightId}/class/{seatClass}
POST   /api/seats/book
```

### Payments (PROTECTED - Auth Required)
```http
POST   /api/payments
GET    /api/payments/{id}
```

### Tickets (PROTECTED - Auth Required)
```http
GET    /api/tickets/{bookingId}/download
```

### Example Request/Response

**GET /api/flights/upcoming** (NEW - Rolling 30-day window)
```json
[
  {
    "id": 27,
    "flightNumber": "NB101",
    "source": "DEL",
    "destination": "MUM",
    "departureDate": "2025-01-07",
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

**POST /api/passengers/login**
```json
{
  "email": "rahul@test.com",
  "password": "password123"
}
```

**Response:**
```json
{
  "id": 1,
  "firstName": "Rahul",
  "lastName": "Sharma",
  "email": "rahul@test.com",
  "phoneNumber": "9876543210",
  "age": 28,
  "gender": "Male"
}
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

**Response:**
```json
{
  "id": 21,
  "passenger": {...},
  "flight": {...},
  "bookingDate": "2025-01-05T12:39:20",
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
- **Available Flights** - Browse all upcoming flights (30-day rolling window)
- **Flight Search** - Public access, login required for booking (NEW!)
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

### Public Flight Search (NEW!)
```java
// SecurityConfig.java - Public endpoints
.requestMatchers(
    "/api/passengers/login",
    "/api/passengers/signup",
    "/api/flights/**"  // All flight endpoints public
).permitAll()
```

```javascript
// search.html - Conditional authentication
function bookDirectFlight(flightId) {
    const passenger = localStorage.getItem('passenger');
    
    if (!passenger) {
        alert('🔒 Please login first to book flights');
        window.location.href = 'login.html';
        return;
    }
    
    // Proceed with booking...
}
```

### Rolling 30-Day Date Logic (NEW!)
```java
// FlightRepository.java - Rolling window query
@Query("SELECT f FROM Flight f WHERE f.departureDate >= CURRENT_DATE " +
       "AND f.departureDate <= DATE_ADD(CURRENT_DATE, 30) " +
       "ORDER BY f.departureDate ASC, f.departureTime ASC")
List<Flight> findUpcomingFlights();
```

```sql
-- data.sql - Automatic date updates on startup
UPDATE flights
SET departure_date = DATE_ADD(
    CURDATE(), 
    INTERVAL (DAY(departure_date) % 28) DAY
)
WHERE departure_date < CURDATE()
   OR departure_date > DATE_ADD(CURDATE(), INTERVAL 30 DAY);
```

### Health Monitoring (NEW!)
```java
// HealthController.java - Lightweight endpoint
@GetMapping("/health")
public ResponseEntity<Map<String, Object>> health() {
    Map<String, Object> response = new HashMap<>();
    response.put("status", "UP");
    response.put("service", "Nimbus Airlines Backend");
    response.put("timestamp", LocalDateTime.now());
    return ResponseEntity.ok(response);
}
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
- Finds flights with layover between 1-8 hours
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

### Login/Signup Not Working (NEW!)
```bash
# Verify SecurityConfig.java exists and has authentication endpoints public
# Check browser console for CORS errors
# Test endpoints directly:
curl -X POST http://localhost:8080/api/passengers/login \
  -H "Content-Type: application/json" \
  -d '{"email":"test@test.com","password":"password"}'
```

### Flight Dates Not Updating
```bash
# Verify data.sql is in src/main/resources/
# Check application.properties has:
spring.sql.init.mode=always
spring.jpa.defer-datasource-initialization=true

# Check console logs for SQL execution
```

### No Flights Showing (End of Month Issue)
```bash
# OLD PROBLEM: Month-based logic caused gaps on last day of month
# NEW SOLUTION: Rolling 30-day window always shows flights

# Test API endpoint:
curl http://localhost:8080/api/flights/upcoming

# Should return flights from today to 30 days ahead
```

### UptimeRobot Monitor Shows "Down"
```bash
# Verify health endpoint works:
curl https://your-app.onrender.com/health

# Check Render logs for errors
# Ensure URL in UptimeRobot is correct (https://, no typos)
# Verify SecurityConfig allows /health endpoint
```

### CORS Issues
- Ensure frontend runs on allowed ports (5500, 5501)
- Update `CorsConfig.java` to include your production URLs
- Check browser console for specific CORS errors

### Session Issues
- Clear browser localStorage
- Logout and login again
- Check that login endpoints are public in SecurityConfig

## 🔒 Security Best Practices

1. **Never commit sensitive data** to Git:
   - Add `application.properties` to `.gitignore`
   - Use environment variables in production

2. **Password Security**:
   - Consider adding password hashing (BCrypt) in production
   - Current implementation stores plain text (development only)

3. **API Security**:
   - Public endpoints are read-only (flights)
   - All write operations require authentication
   - Admin endpoints protected separately

4. **CORS Configuration**:
   - Only allow trusted origins
   - Update `CorsConfig.java` for production

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
- Spring Security Documentation
- MySQL Community
- iText PDF Library
- Render, Netlify, Railway for cloud hosting
- UptimeRobot for health monitoring
- All contributors and testers

## 📧 Contact

For questions or support:
- **GitHub**: [@anindita-19](https://github.com/anindita-19)
- **Repository**: [Airline Management System](https://github.com/anindita-19/Airline-Management-System.git)
- **Live Demo**: [Nimbus Airlines](https://nimbus-airlines.netlify.app)

---

## 🆕 Recent Updates (v2.0)

- ✅ **Public Flight Search** - Browse flights without login
- ✅ **Rolling 30-Day Logic** - No more end-of-month gaps
- ✅ **Spring Security Integration** - Granular endpoint protection
- ✅ **Health Monitoring** - `/health` endpoint for uptime tracking
- ✅ **Cold Start Prevention** - UptimeRobot integration
- ✅ **Cloud Deployment** - Render + Netlify + Railway
- ✅ **Improved UX** - Login required only for booking actions

---

⭐ Star this repo if you find it helpful!

**Made with ❤️ by Anindita**