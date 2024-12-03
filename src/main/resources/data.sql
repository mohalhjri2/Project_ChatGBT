-- Insert sample data into Household
INSERT INTO household (eircode, number_of_occupants, max_number_of_occupants, owner_occupied)
VALUES
    ('D12ABC1', 3, 5, true),
    ('D34DEF2', 4, 6, false),
    ('D56GHI3', 2, 4, true);

-- Insert sample data into Pet
INSERT INTO pet (name, animal_type, breed, age, household_eircode)
VALUES
    ('Buddy', 'Dog', 'Golden Retriever', 5, 'D12ABC1'),
    ('Whiskers', 'Cat', 'Siamese', 3, 'D34DEF2'),
    ('Charlie', 'Dog', 'Bulldog', 4, 'D12ABC1'),
    ('Mittens', 'Cat', 'Persian', 2, 'D56GHI3');
