CREATE TABLE users(
    id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(300) NOT NULL,
    role ENUM('ADMIN', 'USER'),
    enabled BOOLEAN
);

CREATE TABLE employees(
    id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    position VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE customers(
    document VARCHAR(20) PRIMARY KEY NOT NULL,
    name VARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE vehicles(
    vin VARCHAR(17) PRIMARY KEY NOT NULL UNIQUE,
    license_plate VARCHAR(7) NOT NULL UNIQUE,
    brand VARCHAR(50) NOT NULL,
    model VARCHAR(50) NOT NULL,
    color VARCHAR(30),
    year INT NOT NULL,
    price DECIMAL(12,2) NOT NULL ,
    status VARCHAR(20) DEFAULT 'DISPONIVEL',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE sales(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    vehicle_vin VARCHAR(17) NOT NULL UNIQUE,
    customer_id VARCHAR(20) NOT NULL,
    employee_id BIGINT NOT NULL,
    sale_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    value DECIMAL(12,2) NOT NULL,
    payment_method VARCHAR(30),

    CONSTRAINT fk_vehicle FOREIGN KEY (vehicle_vin) REFERENCES vehicles(vin),
    CONSTRAINT fk_customer FOREIGN KEY (customer_id) REFERENCES customers(document),
    CONSTRAINT fk_employee FOREIGN KEY (employee_id) REFERENCES employees(id)
);