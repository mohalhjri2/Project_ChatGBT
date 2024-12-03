-- Create Household table
CREATE TABLE household (
                           eircode VARCHAR(20) PRIMARY KEY,
                           number_of_occupants INT NOT NULL,
                           max_number_of_occupants INT NOT NULL,
                           owner_occupied BOOLEAN NOT NULL
);

-- Create Pet table with a foreign key to Household
CREATE TABLE pet (
                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                     name VARCHAR(255) NOT NULL,
                     animal_type VARCHAR(50) NOT NULL,
                     breed VARCHAR(100),
                     age INT NOT NULL,
                     household_eircode VARCHAR(20),
                     FOREIGN KEY (household_eircode) REFERENCES household(eircode) ON DELETE SET NULL
);
CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       role VARCHAR(50) NOT NULL,
                       locked BOOLEAN NOT NULL DEFAULT FALSE,
                       first_name VARCHAR(100),
                       last_name VARCHAR(100),
                       county VARCHAR(100)
);
