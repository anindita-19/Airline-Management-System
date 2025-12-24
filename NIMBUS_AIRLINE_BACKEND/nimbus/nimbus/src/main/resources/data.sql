-- ============================================
-- FILE LOCATION: airline-backend/src/main/resources/data.sql
-- PURPOSE: Automatically update flight dates to current month/year
-- RUNS: On application startup (Spring Boot auto-executes data.sql)
-- ============================================

-- Update all flights to current month/year while preserving the day
UPDATE flights
SET departure_date = DATE_FORMAT(
    CONCAT(
        YEAR(CURDATE()), '-',
        MONTH(CURDATE()), '-',
        DAY(departure_date)
    ), '%Y-%m-%d'
)
WHERE departure_date < CURDATE()
   OR YEAR(departure_date) != YEAR(CURDATE())
   OR MONTH(departure_date) != MONTH(CURDATE());

-- Handle edge case: if day doesn't exist in current month (e.g., Feb 30)
-- Set to last day of current month
UPDATE flights
SET departure_date = LAST_DAY(CURDATE())
WHERE DAY(departure_date) > DAY(LAST_DAY(CURDATE()));

-- Log update (optional - for debugging)
-- SELECT CONCAT('Updated ', ROW_COUNT(), ' flight dates to current month/year') AS status;

-- ============================================
-- SAMPLE DATA: Flights across different days
-- (Only runs if table is empty)
-- ============================================

-- Check if we need to insert sample data
SET @row_count = (SELECT COUNT(*) FROM flights);

-- Insert sample flights only if table is empty
INSERT INTO flights (flight_number, source, destination, departure_date, departure_time, arrival_time,
                     price, total_seats, available_seats, airline, economy_seats, business_seats,
                     first_class_seats, economy_price, business_price, first_class_price)
SELECT * FROM (
    SELECT 'NB101' AS flight_number, 'DEL' AS source, 'MUM' AS destination,
           DATE_ADD(CURDATE(), INTERVAL 2 DAY) AS departure_date,
           '08:00:00' AS departure_time, '10:30:00' AS arrival_time,
           5000.00 AS price, 190 AS total_seats, 190 AS available_seats,
           'Nimbus Air' AS airline, 143 AS economy_seats, 38 AS business_seats,
           9 AS first_class_seats, 5000.00 AS economy_price,
           10000.00 AS business_price, 15000.00 AS first_class_price
    UNION ALL
    SELECT 'NB102', 'MUM', 'BLR', DATE_ADD(CURDATE(), INTERVAL 5 DAY),
           '14:00:00', '16:00:00', 4500.00, 190, 190, 'Nimbus Air',
           143, 38, 9, 4500.00, 9000.00, 13500.00
    UNION ALL
    SELECT 'NB103', 'DEL', 'GOA', DATE_ADD(CURDATE(), INTERVAL 7 DAY),
           '06:30:00', '09:00:00', 6000.00, 190, 190, 'Nimbus Air',
           143, 38, 9, 6000.00, 12000.00, 18000.00
) AS sample_data
WHERE @row_count = 0;