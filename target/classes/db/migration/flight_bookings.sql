CREATE TABLE IF NOT EXISTS flight_bookings(
  booking_id serial PRIMARY  KEY,
  flight_id serial NOT NULL,
  flight_capacity INT NOT NULL,
  flight_current_capacity INT NOT NULL,
  FOREIGN KEY (flight_id)
    REFERENCES flight_details(flight_id)
    );