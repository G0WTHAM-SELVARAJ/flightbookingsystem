CREATE TABLE IF NOT EXISTS flight_details(
    flight_id serial PRIMARY KEY,
    flight_source_location VARCHAR(50) NOT NULL,
    flight_destination_location VARCHAR(50) NOT NULL,
    flight_depatured_at TIMESTAMP  NOT NULL,
    flight_arrived_at TIMESTAMP  NOT NULL,
    ticket_price INT NOT NULL
);