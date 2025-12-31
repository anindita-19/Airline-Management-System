-- ============================================
-- FILE LOCATION: airline-backend/src/main/resources/data.sql
-- UPDATED: Uses rolling 30-day window instead of month-based
-- RUNS: On application startup (Spring Boot auto-executes data.sql)
-- ============================================

-- ✅ UPDATED: Set all old flights to dates within the next 30 days
-- This ensures flights are always visible and spread across the rolling window

UPDATE flights
SET departure_date = DATE_ADD(
    CURDATE(),
    INTERVAL (DAY(departure_date) % 28) DAY
)
WHERE departure_date < CURDATE()
   OR departure_date > DATE_ADD(CURDATE(), INTERVAL 30 DAY);

-- ============================================
-- SAMPLE DATA: Flights spread across 30 days
-- (Only runs if table is empty)
-- ============================================

-- Check if we need to insert sample data
SET @row_count = (SELECT COUNT(*) FROM flights);

-- Insert sample flights spread across the next 30 days
INSERT INTO flights (flight_number, source, destination, departure_date, departure_time, arrival_time,
                     price, total_seats, available_seats, airline, economy_seats, business_seats,
                     first_class_seats, economy_price, business_price, first_class_price)
SELECT * FROM (
    -- Flights in Week 1 (Days 1-7)
    SELECT 'NB101' AS flight_number, 'DEL' AS source, 'MUM' AS destination,
           DATE_ADD(CURDATE(), INTERVAL 2 DAY) AS departure_date,
           '08:00:00' AS departure_time, '10:30:00' AS arrival_time,
           5000.00 AS price, 190 AS total_seats, 190 AS available_seats,
           'Nimbus Air' AS airline, 143 AS economy_seats, 38 AS business_seats,
           9 AS first_class_seats, 5000.00 AS economy_price,
           10000.00 AS business_price, 15000.00 AS first_class_price
    UNION ALL
    SELECT 'NB102', 'MUM', 'BLR', DATE_ADD(CURDATE(), INTERVAL 3 DAY),
           '14:00:00', '16:00:00', 4500.00, 190, 190, 'Nimbus Air',
           143, 38, 9, 4500.00, 9000.00, 13500.00
    UNION ALL
    SELECT 'NB103', 'DEL', 'GOA', DATE_ADD(CURDATE(), INTERVAL 5 DAY),
           '06:30:00', '09:00:00', 6000.00, 190, 190, 'Nimbus Air',
           143, 38, 9, 6000.00, 12000.00, 18000.00
    UNION ALL
    SELECT 'NB104', 'BLR', 'DEL', DATE_ADD(CURDATE(), INTERVAL 7 DAY),
           '12:00:00', '14:30:00', 5500.00, 190, 190, 'Nimbus Air',
           143, 38, 9, 5500.00, 11000.00, 16500.00

    -- Flights in Week 2 (Days 8-14)
    UNION ALL
    SELECT 'NB105', 'MUM', 'DEL', DATE_ADD(CURDATE(), INTERVAL 10 DAY),
           '18:00:00', '20:30:00', 5200.00, 190, 190, 'Nimbus Air',
           143, 38, 9, 5200.00, 10400.00, 15600.00
    UNION ALL
    SELECT 'NB106', 'GOA', 'BLR', DATE_ADD(CURDATE(), INTERVAL 12 DAY),
           '11:00:00', '12:30:00', 3800.00, 190, 190, 'Nimbus Air',
           143, 38, 9, 3800.00, 7600.00, 11400.00
    UNION ALL
    SELECT 'NB107', 'DEL', 'BLR', DATE_ADD(CURDATE(), INTERVAL 14 DAY),
           '07:00:00', '09:30:00', 6500.00, 190, 190, 'Nimbus Air',
           143, 38, 9, 6500.00, 13000.00, 19500.00

    -- Flights in Week 3 (Days 15-21)
    UNION ALL
    SELECT 'NB108', 'BLR', 'MUM', DATE_ADD(CURDATE(), INTERVAL 17 DAY),
           '15:30:00', '17:30:00', 4700.00, 190, 190, 'Nimbus Air',
           143, 38, 9, 4700.00, 9400.00, 14100.00
    UNION ALL
    SELECT 'NB109', 'MUM', 'GOA', DATE_ADD(CURDATE(), INTERVAL 19 DAY),
           '09:00:00', '10:30:00', 4200.00, 190, 190, 'Nimbus Air',
           143, 38, 9, 4200.00, 8400.00, 12600.00
    UNION ALL
    SELECT 'NB110', 'GOA', 'DEL', DATE_ADD(CURDATE(), INTERVAL 21 DAY),
           '16:00:00', '18:30:00', 6300.00, 190, 190, 'Nimbus Air',
           143, 38, 9, 6300.00, 12600.00, 18900.00

    -- Flights in Week 4 (Days 22-28)
    UNION ALL
    SELECT 'NB111', 'DEL', 'MUM', DATE_ADD(CURDATE(), INTERVAL 24 DAY),
           '20:00:00', '22:30:00', 5800.00, 190, 190, 'Nimbus Air',
           143, 38, 9, 5800.00, 11600.00, 17400.00
    UNION ALL
    SELECT 'NB112', 'BLR', 'GOA', DATE_ADD(CURDATE(), INTERVAL 26 DAY),
           '13:00:00', '14:30:00', 3900.00, 190, 190, 'Nimbus Air',
           143, 38, 9, 3900.00, 7800.00, 11700.00
    UNION ALL
    SELECT 'NB113', 'MUM', 'BLR', DATE_ADD(CURDATE(), INTERVAL 28 DAY),
           '10:30:00', '12:30:00', 4600.00, 190, 190, 'Nimbus Air',
           143, 38, 9, 4600.00, 9200.00, 13800.00
) AS sample_data
WHERE @row_count = 0;

-- ✅ Verify data was inserted
SELECT CONCAT('✅ Total flights in database: ', COUNT(*)) AS status FROM flights;
SELECT CONCAT('📅 Date range: ', MIN(departure_date), ' to ', MAX(departure_date)) AS date_range FROM flights;