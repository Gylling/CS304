
DROP TABLE IF EXISTS Reservations;
DROP TABLE IF EXISTS Rentals;
DROP TABLE IF EXISTS Vehicles;
DROP TABLE IF EXISTS VehicleTypes;
DROP TABLE IF EXISTS Customers;
DROP TABLE IF EXISTS Returns;
DROP TABLE IF EXISTS TimePeriod;
DROP TABLE IF EXISTS Branch;

CREATE TABLE Reservations (
    confNo INTEGER,
    vtName VARCHAR(20),
    dLicense VARCHAR(20),
    fromDate VARCHAR (20),
    fromTime VARCHAR (20),
    toDate VARCHAR (20),
    toTime VARCHAR (20),
    PRIMARY KEY (confNo),
    FOREIGN KEY (vtName) REFERENCES VehicleTypes,
    FOREIGN KEY (dLicense) REFERENCES Customers,
);

CREATE TABLE Rentals(
    rid INTEGER,
    vid INTEGER,
    dLicense VARCHAR(20),
    fromDate VARCHAR(20),
    fromTime VARCHAR(20),
    toDate VARCHAR(20),
    toTime VARCHAR(20),
    odometer INTEGER,
    cardName VARCHAR(20),
    cardNo INTEGER,
    expDate VARCHAR(20),
    confNo INTEGER,
    PRIMARY KEY (rid),
    FOREIGN KEY (dLicense) REFERENCES Customers (dLicense),
    FOREIGN KEY (confNo) REFERENCES Reservations (confNo)
);

CREATE TABLE Vehicles(
    vid INTEGER,
    vLicense VARCHAR(20),
    make VARCHAR(20),
    model VARCHAR(20),
    year INTEGER,
    color VARCHAR(20),
    odometer INTEGER,
    status VARCHAR(20),
    vtname VARCHAR(20),
    location VARCHAR(20),
    city VARCHAR(20),
    PRIMARY KEY (vLicense),
    FOREIGN KEY (vtname) REFERENCES VehicleTypes (vtname),
    FOREIGN KEY (location, city) REFERENCES Branch (location, city)
);

CREATE TABLE VehicleTypes(
    vtname VARCHAR(20),
    features VARCHAR(20),
    wrate INTEGER,
    drate INTEGER,
    hrate INTEGER,
    wirate INTEGER,
    dirate INTEGER,
    hirate INTEGER,
    krate INTEGER,
    PRIMARY KEY (vtname),
);

CREATE TABLE Customers(
    dLicense VARCHAR(20),
    name VARCHAR(20),
    address VARCHAR(20),
    PRIMARY KEY (dLicense)
);

-- This table could be added to the rent table, not sure how we should go about doing that
-- CREATE TABLE Returns(
-- );

CREATE TABLE Branch(
    location VARCHAR(20),
    city VARCHAR(20),
    PRIMARY KEY (location, city)
);

-- Customers
INSERT INTO Customers
    values ('A1456BZ','Megan Tumaro', '1234 George Street, Vancouver');
INSERT INTO Customers
    values ('A3456TZ','Alison Fitzpatrick', '9834 Hastings Street, Vancouver');
INSERT INTO Customers
    values ('B2456HG','Jessica DeTello', '4493 Georgia Street, Vancouver');
INSERT INTO Customers
    values ('F1366BG','Maxwell Telluro', '2275 Lawson Avenue, West Vancouver');
INSERT INTO Customers
    values ('W2356BZ','Jordan Fenix', '2234 11th Street, West Vancouver');
INSERT INTO Customers
    values ('X1956XZ','Ivan Rogolich', '4563 42nd Street, Vancouver');
INSERT INTO Customers
    values ('A1446RR','Lily Dubrovik', '123 Ontario Street, Vancouver');
INSERT INTO Customers
    values ('W4456GZ','Alison Monroe', '2194 152nd Street, Surrey');
INSERT INTO Customers
    values ('Y4446GZ','Alex Thind', '223 276th Street, Surrey');
INSERT INTO Customers
    values ('F4436KZ','Alexis Westly', '3451 Fitzpatrick Street, Delta');
INSERT INTO Customers
    values ('R2456GP','Alexander DeThomas', '2294 150th Street, Abbotsford');
INSERT INTO Customers
    values ('B5456BZ','Morgan Gertide', '124 40th Street, Vancouver');
INSERT INTO Customers
    values ('B3459BB','Alex Jansen', '834 Hastings Street, Vancouver');
INSERT INTO Customers
    values ('F4456HG','Jessica Thomas', '493 Vedder Road, Chilliwack');
INSERT INTO Customers
    values ('Z2366BZ','Aaron Pumello', '227 Ottowa Street, West Vancouver');
INSERT INTO Customers
    values ('J2356BJ','Jordan Halo', '4234 Lonsdale Avenue, North Vancouver');
INSERT INTO Customers
    values ('X4956ZZ','Mika Roland', '4263 11th Street, North Vancouver');
INSERT INTO Customers
    values ('A1443BA','Natalie Shelland', '123 Old Yale Road, Abbotsford');
INSERT INTO Customers
    values ('Y3456YZ','Alexander Sharp', '214 45th Street, Surrey');
INSERT INTO Customers
    values ('B5496GA','DeMarcus Felland', '2223 276th Street, Abbotsford');
INSERT INTO Customers
    values ('J4449KZ','Alexis Thornhill', '3451 Holland Street, Vancouver');
INSERT INTO Customers
    values ('J9456GH','Lauren Taylor', '1294 150th Street, Abbotsford');

--Reservations
INSERT INTO Reservations
    values (2456, 'Standard', 'J9456GH', '2019/12/22', '13:00', '2019/12/29', '13:00');
INSERT INTO Reservations
    values (9656, 'Truck', 'J4449KZ', '2020/01/02', '20:00', '2020/01/18', '11:00');
INSERT INTO Reservations
    values (4201, 'SUV', 'Y3456YZ', '2020/01/02', '20:00', '2020/01/10', '9:00');
INSERT INTO Reservations
    values (8230, 'SUV', 'A1443BA', '2019/11/29', '14:00', '2020/01/02', '8:00');
INSERT INTO Reservations
    values (8234, 'Compact', 'A1443BA', '2020/03/15', '13:00', '2020/03/27', '10:00');
INSERT INTO Reservations
    values (3230, 'Economy', 'Z2366BZ', '2019/11/29', '13:00', '2019/12/25', '14:00');
INSERT INTO Reservations
    values (4269, 'Economy', 'W4456GZ', '2020/01/19', '12:00', '2020/01/27', '21:00');
INSERT INTO Reservations
    values (2275, 'Truck', 'X4956ZZ', '2020/02/19', '12:00', '2020/02/25', '21:00');
INSERT INTO Reservations
    values (2455, 'Compact', 'J2356BJ', '2020/02/19', '9:00', '2020/02/23', '8:00');
INSERT INTO Reservations
    values (8445, 'Full-size', 'A1456BZ', '2020/04/02', '10:00', '2020/04/07', '12:00');
INSERT INTO Reservations
    values (9021, 'Mid-size', 'X1956XZ', '2020/01/03', '9:00', '2020/01/10', '12:00');

-- VehicleTypes
INSERT INTO VehicleTypes
    values ('Economy', 'No Heated-seats', 750, 160, 8, 50, 10, 1, 1);
INSERT INTO VehicleTypes
    values ('Compact', 'Heated-seats', 750, 160, 8, 50, 10, 1, 1);
INSERT INTO VehicleTypes
    values ('Standard', 'Heated-seats', 780, 165, 8, 50, 10, 1, 1);
INSERT INTO VehicleTypes
    values ('Mid-size', 'Heated-seats', 800, 165, 8, 50, 10, 1, 1);
INSERT INTO VehicleTypes
    values ('Full-size', 'Heated-seats', 875, 175, 10, 50, 10, 1, 1);
INSERT INTO VehicleTypes
    values ('SUV', 'Heated-seats', 950, 180, 12, 55, 12, 1, 2);
INSERT INTO VehicleTypes
    values ('Truck', 'Heated-seats', 980, 185, 12, 60, 14, 1, 2);

-- Vehicles
--Economy
INSERT INTO Vehicles
    values (228975, 'AG3 45H', 'Toyota', 'Prius', '2019', 'Silver', 3000, 'Available', 'Economy', 'A', 'Abbotsford');
INSERT INTO Vehicles
    values (328975, 'BF3 43H', 'Toyota', 'Prius', '2018', 'Blue', 3090, 'Available', 'Economy', 'A', 'Abbotsford');
INSERT INTO Vehicles
    values (228975, 'JG9 69Z', 'Hyundai', 'Kona', '2018', 'Silver', 4500, 'Available', 'Economy', 'C', 'Chilliwack');
INSERT INTO Vehicles
    values (228975, 'TG3 95H', 'Chevrolet', 'Bolt', '2018', 'Grey', 5000, 'Available', 'Economy', 'C', 'Chilliwack');
INSERT INTO Vehicles
    values (391452, 'XG3 42H', 'Ford', 'Focus', '2017', 'Silver', 10000, 'Rented', 'Economy', 'V', 'Vancouver');
INSERT INTO Vehicles
    values (491452, 'HG9 42H', 'Ford', 'Focus', '2018', 'Silver', 10000, 'Rented', 'Economy', 'V', 'Vancouver');
INSERT INTO Vehicles
    values (289345, 'YU6 89K', 'Hyundai', 'Kona', '2016', 'Red', 20200, 'Available', 'Economy', 'N', 'North Vancouver');
INSERT INTO Vehicles
    values (283333, 'GU6 29K', 'Toyota', 'Prius', '2017', 'Black', 10200, 'Rented', 'Economy', 'N', 'North Vancouver');
INSERT INTO Vehicles
    values (493333, 'YT6 22G', 'Toyota', 'Prius', '2018', 'Black', 9200, 'In Shop', 'Economy', 'W', 'West Vancouver');
INSERT INTO Vehicles
    values (433339, 'GH6 52G', 'Hyundai', 'Kona', '2018', 'Black', 7500, 'Rented', 'Economy', 'W', 'West Vancouver');
-- Compact
INSERT INTO Vehicles
    values (848975, 'GH9 45H', 'Toyota', 'Carolla', '2019', 'Silver', 4000, 'Rented', 'Compact', 'A', 'Abbotsford');
INSERT INTO Vehicles
    values (938275, 'PH9 43H', 'Honda', 'Civic', '2018', 'Blue', 9820, 'Available', 'Compact', 'A', 'Abbotsford');
INSERT INTO Vehicles
    values (283941, 'JG9 89R', 'Honda', 'Civic', '2018', 'Silver', 2500, 'Available', 'Compact', 'C', 'Chilliwack');
INSERT INTO Vehicles
    values (293975, 'TG3 69P', 'Kia', 'Soul', '2018', 'Grey', 4500, 'Available', 'Compact', 'C', 'Chilliwack');
INSERT INTO Vehicles
    values (991562, 'TG3 45H', 'Kia', 'Soul', '2017', 'Silver', 23000, 'Rented', 'Compact', 'V', 'Vancouver');
INSERT INTO Vehicles
    values (491442, 'HQ9 43H', 'Hyundai', 'Elantra', '2018', 'Silver', 30450, 'In Shop', 'Compact', 'V', 'Vancouver');
INSERT INTO Vehicles
    values (224345, 'AA9 89K', 'Honda', 'Civic', '2016', 'Red', 51200, 'Available', 'Compact', 'N', 'North Vancouver');
INSERT INTO Vehicles
    values (999333, 'AP3 49K', 'Toyota', 'Carolla', '2017', 'Black', 19200, 'Available', 'Compact', 'N', 'North Vancouver');
INSERT INTO Vehicles
    values (493443, 'XT6 45G', 'Toyota', 'Carolla', '2018', 'Black', 23200, 'In Shop', 'Compact', 'W', 'West Vancouver');
INSERT INTO Vehicles
    values (434449, 'XX6 69X', 'Hyundai', 'Elantra', '2018', 'Black', 10200, 'Available', 'Compact', 'W', 'West Vancouver');
-- Mid-Size
INSERT INTO Vehicles
    values (798975, 'GG3 33H', 'Ford', 'Fusion', '2019', 'Silver', 4500, 'Available', 'Mid-size', 'A', 'Abbotsford');
INSERT INTO Vehicles
    values (298275, 'GG9 22H', 'Subaru', 'Legacy', '2018', 'Blue', 3820, 'Rented', 'Mid-size', 'A', 'Abbotsford');
INSERT INTO Vehicles
    values (286941, 'XX1 99P', 'Subaru', 'Legacy', '2018', 'Silver', 12500, 'Rented', 'Mid-size', 'C', 'Chilliwack');
INSERT INTO Vehicles
    values (293435, 'XX2 69P', 'Kia', 'Optima', '2018', 'Grey', 9500, 'Available', 'Mid-size', 'C', 'Chilliwack');
INSERT INTO Vehicles
    values (694201, 'RR3 45H', 'Kia', 'Optima', '2017', 'Silver', 45000, 'In Shop', 'Mid-size', 'V', 'Vancouver');
INSERT INTO Vehicles
    values (491420, 'FF9 43H', 'Nissan', 'Altima', '2018', 'Silver', 22450, 'Available', 'Mid-size', 'V', 'Vancouver');
INSERT INTO Vehicles
    values (224669, 'GG9 89K', 'Subaru', 'Legacy', '2016', 'Red', 66000, 'Available', 'Mid-size', 'N', 'North Vancouver');
INSERT INTO Vehicles
    values (929223, 'DD3 49K', 'Ford', 'Fusion', '2017', 'Black', 15200, 'Available', 'Mid-size', 'N', 'North Vancouver');
INSERT INTO Vehicles
    values (493223, 'QQ6 45G', 'Ford', 'Fusion', '2018', 'Black', 40200, 'In Shop', 'Mid-size', 'W', 'West Vancouver');
INSERT INTO Vehicles
    values (432229, 'TT6 69X', 'Nissan', 'Altima', '2018', 'Black', 19200, 'Rented', 'Mid-size', 'W', 'West Vancouver');
-- Standard
INSERT INTO Vehicles
    values (998235, 'GG3 33J', 'Mazda', '3', '2019', 'Silver', 4100, 'Available', 'Standard', 'A', 'Abbotsford');
INSERT INTO Vehicles
    values (900009, 'GG9 22I', 'Mazda', '3', '2018', 'Blue', 5820, 'Available', 'Standard', 'A', 'Abbotsford');
INSERT INTO Vehicles
    values (900008, 'XX1 99H', 'Mazda', '3', '2018', 'Silver', 22500, 'Rented', 'Standard', 'C', 'Chilliwack');
INSERT INTO Vehicles
    values (900007, 'XX2 69G', 'Mazda', '3', '2018', 'Grey', 9000, 'Available', 'Standard', 'C', 'Chilliwack');
INSERT INTO Vehicles
    values (900006, 'RR3 45F', 'Mazda', '3', '2017', 'Silver', 35000, 'Rented', 'Standard', 'V', 'Vancouver');
INSERT INTO Vehicles
    values (900005, 'FF9 43E', 'Mazda', '3', '2018', 'Silver', 23450, 'Available', 'Standard', 'V', 'Vancouver');
INSERT INTO Vehicles
    values (900004, 'GG9 89D', 'Mazda', '3', '2016', 'Red', 60000, 'Available', 'Standard', 'N', 'North Vancouver');
INSERT INTO Vehicles
    values (900003, 'DD3 49C', 'Mazda', '3', '2017', 'Black', 21200, 'Available', 'Standard', 'N', 'North Vancouver');
INSERT INTO Vehicles
    values (900002, 'QQ6 45B', 'Mazda', '3', '2018', 'Black', 34200, 'In Shop', 'Standard', 'W', 'West Vancouver');
INSERT INTO Vehicles
    values (900001, 'TT6 69A', 'Mazda', '3', '2018', 'Black', 33200, 'Available', 'Standard', 'W', 'West Vancouver');
--Mid-Size
INSERT INTO Vehicles
    values (998000, 'LD1 33J', 'Hyundai', 'Sonata', '2019', 'Silver', 4100, 'Available', 'Mid-size', 'A', 'Abbotsford');
INSERT INTO Vehicles
    values (990009, 'LL9 22I', 'Hyundai', 'Sonata', '2018', 'Blue', 5820, 'Available', 'Mid-size', 'A', 'Abbotsford');
INSERT INTO Vehicles
    values (990008, 'LL8 99H', 'Hyundai', 'Sonata', '2018', 'Silver', 22500, 'Rented', 'Mid-size', 'C', 'Chilliwack');
INSERT INTO Vehicles
    values (990007, 'LL7 69G', 'Hyundai', 'Sonata', '2018', 'Grey', 9000, 'Available', 'Mid-size', 'C', 'Chilliwack');
INSERT INTO Vehicles
    values (990006, 'LL6 45F', 'Hyundai', 'Sonata', '2017', 'Silver', 35000, 'Rented', 'Mid-size', 'V', 'Vancouver');
INSERT INTO Vehicles
    values (990005, 'LL5 43E', 'Hyundai', 'Sonata', '2018', 'Silver', 23450, 'Available', 'Mid-size', 'V', 'Vancouver');
INSERT INTO Vehicles
    values (990004, 'LL4 89D', 'Hyundai', 'Sonata', '2016', 'Red', 60000, 'Available', 'Mid-size', 'N', 'North Vancouver');
INSERT INTO Vehicles
    values (990003, 'LL3 49C', 'Hyundai', 'Sonata', '2017', 'Black', 21200, 'Available', 'Mid-size', 'N', 'North Vancouver');
INSERT INTO Vehicles
    values (990002, 'LL2 45B', 'Hyundai', 'Sonata', '2018', 'Black', 34200, 'Available', 'Mid-size', 'W', 'West Vancouver');
INSERT INTO Vehicles
    values (990001, 'LL1 69A', 'Hyundai', 'Sonata', '2018', 'Black', 33200, 'Available', 'Mid-size', 'W', 'West Vancouver');

--Full-Size
INSERT INTO Vehicles
    values (998000, 'LD1 50X', 'Dodge', 'Charger', '2019', 'Silver', 3100, 'Available', 'Full-size', 'A', 'Abbotsford');
INSERT INTO Vehicles
    values (990009, 'LL9 50X', 'Dodge', 'Charger', '2018', 'Blue', 7820, 'Available', 'Full-size', 'A', 'Abbotsford');
INSERT INTO Vehicles
    values (990008, 'LL8 50X', 'Dodge', 'Charger', '2018', 'Silver', 26500, 'Rented', 'Full-size', 'C', 'Chilliwack');
INSERT INTO Vehicles
    values (990007, 'LL7 50X', 'Dodge', 'Charger', '2018', 'Grey', 6000, 'Available', 'Full-size', 'C', 'Chilliwack');
INSERT INTO Vehicles
    values (990006, 'LL6 50X', 'Dodge', 'Charger', '2017', 'Silver', 45000, 'Rented', 'Full-size', 'V', 'Vancouver');
INSERT INTO Vehicles
    values (990005, 'LL5 50X', 'Dodge', 'Charger', '2018', 'Silver', 25450, 'In Shop', 'Full-size', 'V', 'Vancouver');
INSERT INTO Vehicles
    values (990004, 'LL4 50X', 'Dodge', 'Charger', '2016', 'Red', 62000, 'Available', 'Full-size', 'N', 'North Vancouver');
INSERT INTO Vehicles
    values (990003, 'LL3 50X', 'Dodge', 'Charger', '2017', 'Black', 31200, 'Available', 'Full-size', 'N', 'North Vancouver');
INSERT INTO Vehicles
    values (990002, 'LL2 50X', 'Dodge', 'Charger', '2018', 'Black', 24200, 'Available', 'Full-size', 'W', 'West Vancouver');
INSERT INTO Vehicles
    values (990001, 'LL1 50X', 'Dodge', 'Charger', '2018', 'Black', 43200, 'Available', 'Full-size', 'W', 'West Vancouver');

--SUV
INSERT INTO Vehicles
    values (998090, 'TD1 50X', 'Toyota', '4Runner', '2019', 'Silver', 3100, 'Available', 'SUV', 'A', 'Abbotsford');
INSERT INTO Vehicles
    values (990099, 'TL9 50X', 'Toyota', '4Runner', '2018', 'Blue', 7820, 'Available', 'SUV', 'A', 'Abbotsford');
INSERT INTO Vehicles
    values (990098, 'TL8 50X', 'Toyota', '4Runner', '2018', 'Silver', 26500, 'Rented', 'SUV', 'C', 'Chilliwack');
INSERT INTO Vehicles
    values (990097, 'TL7 50X', 'Toyota', '4Runner', '2018', 'Grey', 6000, 'Available', 'SUV', 'C', 'Chilliwack');
INSERT INTO Vehicles
    values (990096, 'TL6 50X', 'Toyota', '4Runner', '2017', 'Silver', 45000, 'Rented', 'SUV', 'V', 'Vancouver');
INSERT INTO Vehicles
    values (990095, 'TL5 50X', 'Toyota', '4Runner', '2018', 'Silver', 25450, 'In Shop', 'SUV', 'V', 'Vancouver');
INSERT INTO Vehicles
    values (990094, 'TL4 50X', 'Toyota', '4Runner', '2016', 'Red', 62000, 'Available', 'SUV', 'N', 'North Vancouver');
INSERT INTO Vehicles
    values (990093, 'TL3 50X', 'Toyota', '4Runner', '2017', 'Black', 31200, 'Available', 'SUV', 'N', 'North Vancouver');
INSERT INTO Vehicles
    values (990092, 'TL2 50X', 'Toyota', '4Runner', '2018', 'Black', 24200, 'Available', 'SUV', 'W', 'West Vancouver');
INSERT INTO Vehicles
    values (990091, 'TL1 50X', 'Toyota', '4Runner', '2018', 'Black', 43200, 'Available', 'SUV', 'W', 'West Vancouver');



