// ============================================
// FILE LOCATION: airline-frontend/js/script.js
// FIXED: Session Management - Mutual Exclusion
// ============================================

// Base API URL
const API_BASE_URL = 'http://localhost:8080/api';

// Helper function to display messages
function showMessage(message, type) {
    const messageDiv = document.getElementById('message') || 
                       document.getElementById('loginMessage') || 
                       document.getElementById('addFlightMessage');
    
    if (messageDiv) {
        messageDiv.innerHTML = `<p class="${type}">${message}</p>`;
        setTimeout(() => {
            messageDiv.innerHTML = '';
        }, 5000);
    }
}

// ✅ NEW: Session Type Management
function getActiveSessionType() {
    if (localStorage.getItem('passenger')) return 'USER';
    if (localStorage.getItem('admin')) return 'ADMIN';
    return null;
}

function clearAllSessions() {
    localStorage.removeItem('passenger');
    localStorage.removeItem('admin');
    localStorage.removeItem('selectedFlight');
    localStorage.removeItem('currentBooking');
    localStorage.removeItem('connectingFlightId');
    localStorage.removeItem('isConnecting');
    localStorage.removeItem('selectedClass');
    localStorage.removeItem('selectedSeat');
    localStorage.removeItem('luggageWeight');
}

// ✅ FIXED: Check if user is authenticated
function checkAuth() {
    const passenger = localStorage.getItem('passenger');
    if (!passenger) {
        window.location.href = 'login.html';
        return false;
    }
    
    // Setup logout button
    const logoutBtn = document.getElementById('logoutBtn');
    if (logoutBtn) {
        logoutBtn.addEventListener('click', (e) => {
            e.preventDefault();
            logout();
        });
    }
    
    return true;
}

// ✅ FIXED: Check if admin is authenticated
function checkAdminAuth() {
    const admin = localStorage.getItem('admin');
    if (!admin) {
        window.location.href = 'admin.html';
        return false;
    }
    return true;
}

// ✅ FIXED: Logout function - Always redirect to homepage
function logout() {
    clearAllSessions();
    window.location.href = 'index.html';
}

// Format date for display
function formatDate(dateString) {
    const options = { year: 'numeric', month: 'long', day: 'numeric' };
    return new Date(dateString).toLocaleDateString('en-US', options);
}

// Format time for display
function formatTime(timeString) {
    return timeString.substring(0, 5); // Extract HH:MM from HH:MM:SS
}

// Validate email format
function isValidEmail(email) {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
}

// Validate phone number (basic validation)
function isValidPhone(phone) {
    const phoneRegex = /^\d{10}$/;
    return phoneRegex.test(phone.replace(/[\s\-\(\)]/g, ''));
}

// Format currency
function formatCurrency(amount) {
    return new Intl.NumberFormat('en-IN', {
        style: 'currency',
        currency: 'INR',
        minimumFractionDigits: 0,
        maximumFractionDigits: 0
    }).format(amount);
}

// API Helper Functions
const API = {
    // Flights
    async searchFlights(source, destination, date) {
        try {
            const response = await fetch(
                `${API_BASE_URL}/flights/search?source=${source}&destination=${destination}&date=${date}`
            );
            return await response.json();
        } catch (error) {
            console.error('Error searching flights:', error);
            throw error;
        }
    },
    
    async getAllFlights() {
        try {
            const response = await fetch(`${API_BASE_URL}/flights`);
            return await response.json();
        } catch (error) {
            console.error('Error fetching flights:', error);
            throw error;
        }
    },
    
    async getFlightById(id) {
        try {
            const response = await fetch(`${API_BASE_URL}/flights/${id}`);
            return await response.json();
        } catch (error) {
            console.error('Error fetching flight:', error);
            throw error;
        }
    },
    
    async createFlight(flightData) {
        try {
            const response = await fetch(`${API_BASE_URL}/flights`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(flightData)
            });
            return await response.json();
        } catch (error) {
            console.error('Error creating flight:', error);
            throw error;
        }
    },
    
    // Passengers
    async signup(passengerData) {
        try {
            const response = await fetch(`${API_BASE_URL}/passengers/signup`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(passengerData)
            });
            
            if (!response.ok) {
                const error = await response.json();
                throw new Error(error.message || 'Signup failed');
            }
            
            return await response.json();
        } catch (error) {
            console.error('Error during signup:', error);
            throw error;
        }
    },
    
    async login(email, password) {
        try {
            const response = await fetch(`${API_BASE_URL}/passengers/login`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ email, password })
            });
            
            if (!response.ok) {
                const error = await response.json();
                throw new Error(error.message || 'Login failed');
            }
            
            return await response.json();
        } catch (error) {
            console.error('Error during login:', error);
            throw error;
        }
    },
    
    async getAllPassengers() {
        try {
            const response = await fetch(`${API_BASE_URL}/passengers`);
            return await response.json();
        } catch (error) {
            console.error('Error fetching passengers:', error);
            throw error;
        }
    },
    
    // Bookings
    async createBooking(bookingData) {
        try {
            const response = await fetch(`${API_BASE_URL}/bookings`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(bookingData)
            });
            
            if (!response.ok) {
                const error = await response.json();
                throw new Error(error.message || 'Booking failed');
            }
            
            return await response.json();
        } catch (error) {
            console.error('Error creating booking:', error);
            throw error;
        }
    },
    
    async getBookingById(id) {
        try {
            const response = await fetch(`${API_BASE_URL}/bookings/${id}`);
            return await response.json();
        } catch (error) {
            console.error('Error fetching booking:', error);
            throw error;
        }
    },
    
    async getAllBookings() {
        try {
            const response = await fetch(`${API_BASE_URL}/bookings`);
            return await response.json();
        } catch (error) {
            console.error('Error fetching bookings:', error);
            throw error;
        }
    },
    
    async cancelBooking(id) {
        try {
            const response = await fetch(`${API_BASE_URL}/bookings/${id}/cancel`, {
                method: 'PUT'
            });
            
            if (!response.ok) {
                const error = await response.json();
                throw new Error(error.message || 'Cancellation failed');
            }
            
            return await response.json();
        } catch (error) {
            console.error('Error cancelling booking:', error);
            throw error;
        }
    },
    
    // Payments
    async processPayment(paymentData) {
        try {
            const response = await fetch(`${API_BASE_URL}/payments`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(paymentData)
            });
            
            if (!response.ok) {
                const error = await response.json();
                throw new Error(error.message || 'Payment failed');
            }
            
            return await response.json();
        } catch (error) {
            console.error('Error processing payment:', error);
            throw error;
        }
    },
    
    // Admin
    async adminLogin(email, password) {
        try {
            const response = await fetch(`${API_BASE_URL}/admin/login`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ email, password })
            });
            
            if (!response.ok) {
                const error = await response.json();
                throw new Error(error.message || 'Admin login failed');
            }
            
            return await response.json();
        } catch (error) {
            console.error('Error during admin login:', error);
            throw error;
        }
    }
};

// Initialize date picker with today's date as minimum
document.addEventListener('DOMContentLoaded', () => {
    const dateInputs = document.querySelectorAll('input[type="date"]');
    const today = new Date().toISOString().split('T')[0];
    
    dateInputs.forEach(input => {
        input.min = today;
    });
});

// Export for use in other scripts
if (typeof module !== 'undefined' && module.exports) {
    module.exports = {
        API,
        showMessage,
        checkAuth,
        checkAdminAuth,
        logout,
        formatDate,
        formatTime,
        isValidEmail,
        isValidPhone,
        formatCurrency,
        getActiveSessionType,
        clearAllSessions
    };
}