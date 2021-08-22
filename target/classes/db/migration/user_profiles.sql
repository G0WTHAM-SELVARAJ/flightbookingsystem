CREATE TYPE role_type AS ENUM ('admin','customer');

CREATE TABLE userprofiles( 
	user_id serial PRIMARY KEY, 
	user_name VARCHAR(200) UNIQUE NOT NULL, 
	user_password VARCHAR(200) NOT NULL, 
	user_email VARCHAR(255) UNIQUE NOT NULL, 
	user_type role_type, created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, 
	wallet_amount int8 DEFAULT 0
);