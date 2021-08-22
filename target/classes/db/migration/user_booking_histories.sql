CREATE TYPE booking AS ENUM ('cancelled','booked','expired');

CREATE TABLE IF NOT EXISTS user_booking_histories(
    history_id serial PRIMARY KEY,
    user_id serial NOT NULL,
    booking_id serial NOT NULL,
    booking_status booking,
    FOREIGN KEY (user_id)
        REFERENCES  user_profiles(user_id),
    FOREIGN KEY (booking_id)
        REFERENCES  flight_bookings(booking_id)
);