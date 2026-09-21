CREATE TABLE employee_info ( 
    employee_Id INT NOT NULL AUTO_INCREMENT,
    first_Name VARCHAR(20) NOT NULL,
    last_Name VARCHAR(20) NOT NULL,
    email VARCHAR(40) NOT NULL,
    phone_Number VARCHAR(40) NOT NULL,
    address VARCHAR(40) NOT NULL,
    shift_ID INT,
    PRIMARY KEY (employee_Id)
);

INSERT INTO employee_info (first_Name, last_Name, email, phone_Number, address, shift_ID)
VALUES
    ('Alice', 'Smith', 'alice.smith@example.com', '555-123-4567', '123 Main St, Anytown', 1),
    ('Bob', 'Johnson', 'bob.johnson@example.com', '555-987-6543', '456 Oak Ave, Anytown', 2),
    ('Charlie', 'Brown', 'charlie.brown@example.com', '555-555-5555', '789 Pine Ln, Anytown', 3),
    ('Diana', 'Miller', 'diana.miller@example.com', '555-111-2222', '101 Elm Rd, Anytown', 1),
    ('Eve', 'Davis', 'eve.davis@example.com', '555-333-4444', '222 Maple Dr, Anytown', 2);

CREATE TABLE shifts (
    shift_Id INT NOT NULL AUTO_INCREMENT,
    start_time VARCHAR(50) NOT NULL,
    end_time VARCHAR(50) NOT NULL,
    PRIMARY KEY (shift_Id)
);

INSERT INTO shifts (start_time, end_time)
VALUES
    ('8:00 AM', '4:00 PM'),
    ('4:00 PM', '12:00 AM'),
    ('12:00 AM', '8:00 AM');

CREATE TABLE employee_shifts (
    employee_shift_ID INT NOT NULL AUTO_INCREMENT,
    employee_Id INT NOT NULL,
    shift_Id INT NOT NULL,
    check_In DATETIME NOT NULL,
    check_Out DATETIME NULL,
    PRIMARY KEY (employee_shift_ID),
    FOREIGN KEY (shift_Id) REFERENCES shifts(shift_Id)
);

CREATE TABLE attendance_record (
    record_Id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    employee_Id INT NOT NULL,
    time_In DATETIME DEFAULT CURRENT_TIMESTAMP,
    time_Out DATETIME DEFAULT NULL,
    attendance_Status VARCHAR(20) NULL,
    remarks VARCHAR(100) NULL,
    FOREIGN KEY (employee_Id) REFERENCES employee_info(employee_Id)
);

CREATE TABLE admin_users (
    admin_ID INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    PRIMARY KEY (admin_ID)
);

INSERT INTO admin_users (username, password)
VALUES
    ('admin101', 'admin102');

CREATE TABLE employee_users (
    employee_ID INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    PRIMARY KEY (employee_ID)
);

INSERT INTO employee_users (username, password)
VALUES
    ('employee101', 'employee102');