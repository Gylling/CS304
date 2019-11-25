-- DROP DATABASE SuperRentDB;
-- CREATE DATABASE   SuperRentDB;
-- USE               SuperRentDB;
--
drop table Rentals;
drop table Reservations;
drop table Vehicles;
drop table VehicleTypes;
drop table Customers;
drop table Branch;



-- This table could be added to the rent table, not sure how we should go about doing that
-- CREATE TABLE Returns(
-- --);

 CREATE TABLE Branch(
     location VARCHAR(20),
     city VARCHAR(20),
     PRIMARY KEY (location, city)
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
    PRIMARY KEY (vtname)
);

CREATE TABLE Customers(
    dLicense VARCHAR(50),
    name VARCHAR(50),
    address VARCHAR(50),
    PRIMARY KEY (dLicense)
);

CREATE TABLE Reservations (
    confNo INTEGER,
    vtName VARCHAR(20),
    dLicense VARCHAR(20),
    fromDate TIMESTAMP,
    toDate TIMESTAMP,
    PRIMARY KEY (confNo),
    FOREIGN KEY (vtname) REFERENCES VehicleTypes,
    FOREIGN KEY (dLicense) REFERENCES Customers ON DELETE CASCADE
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
    FOREIGN KEY (vtname) REFERENCES VehicleTypes (vtname) ON DELETE CASCADE,
    FOREIGN KEY (location, city) REFERENCES Branch (location, city) ON DELETE CASCADE
);



CREATE TABLE Rentals(
    rid INTEGER,
    vLicense VARCHAR(20),
    dLicense VARCHAR(20),
    fromDate TIMESTAMP,
    toDate TIMESTAMP,
    odometer INTEGER,
    cardName VARCHAR(20),
    cardNo INTEGER,
    expDate VARCHAR(20),
    confNo INTEGER,
    rOdometer INTEGER,
    rFullTank VARCHAR(20),
    value INTEGER,
    rDate TIMESTAMP,
    PRIMARY KEY (rid),
    FOREIGN KEY (dLicense) REFERENCES Customers (dLicense) ON DELETE CASCADE,
    FOREIGN KEY (vLicense) REFERENCES Vehicles (vLicense) ON DELETE CASCADE,
    FOREIGN KEY (confNo) REFERENCES Reservations (confNo)
);


-- Customers

insert into Customers
   values('A1456BZ','Megan Tumaro', '1234 George Street, Vancouver');
insert into Customers
   values('A3456TZ','Alison Fitzpatrick', '9834 Hastings Street, Vancouver');
insert into Customers
   values('B2456HG','Jessica DeTello', '4493 Georgia Street, Vancouver');
insert into Customers
   values('F1366BG','Maxwell Telluro', '2275 Lawson Avenue, West Vancouver');
insert into Customers
   values('W2356BZ','Jordan Fenix', '2234 11th Street, West Vancouver');
insert into Customers
   values('X1956XZ','Ivan Rogolich', '4563 42nd Street, Vancouver');

insert into Customers
   values('A1446RR','Lily Dubrovik', '123 Ontario Street, Vancouver');
insert into Customers
   values('W4456GZ','Alison Monroe', '2194 152nd Street, Surrey');
insert into Customers
   values('Y4446GZ','Alex Thind', '223 276th Street, Surrey');
insert into Customers
   values('F4436KZ','Alexis Westly', '3451 Fitzpatrick Street, Delta');
insert into Customers
   values('R2456GP','Alexander DeThomas', '2294 150th Street, Abbotsford');
insert into Customers
   values('B5456BZ','Morgan Gertide', '124 40th Street, Vancouver');

insert into Customers
   values('B3459BB','Alex Jansen', '834 Hastings Street, Vancouver');
insert into Customers
   values('F4456HG','Jessica Thomas', '493 Vedder Road, Chilliwack');
insert into Customers
   values('Z2366BZ','Aaron Pumello', '227 Ottowa Street, West Vancouver');
insert into Customers
   values('J2356BJ','Jordan Halo', '4234 Lonsdale Avenue, North Vancouver');
insert into Customers
   values('X4956ZZ','Mika Roland', '4263 11th Street, North Vancouver');
insert into Customers
   values('A1443BA','Natalie Shelland', '123 Old Yale Road, Abbotsford');
insert into Customers
   values('Y3456YZ','Alexander Sharp', '214 45th Street, Surrey');
insert into Customers
   values('B5496GA','DeMarcus Felland', '2223 276th Street, Abbotsford');
insert into Customers
   values('J4449KZ','Alexis Thornhill', '3451 Holland Street, Vancouver');
insert into Customers
   values('J9456GH','Lauren Taylor', '1294 150th Street, Abbotsford');

insert into Customers
    values('AAAA111', 'John Doe', '111 41st Street, Vancouver');
insert into Customers
    values('AAAA222', 'Jane Doe', '123 42nd Avenue, Vancouver');
insert into Customers
    values('AAAA333', 'James Dohe', '246 150th Street, Abbotsford');
insert into Customers
    values('AAAA444', 'Jordan Dowd', '111 53rd Street, Chilliwack');
insert into Customers
    values('AAAA555', 'Jane Macleod', '123 43nd Avenue, Vancouver');
insert into Customers
    values('AAAA777', 'James Gordan', '256 150th Street, Abbotsford');
insert into Customers
    values('AAAA888', 'Julien Dean', '121 41st Street, Vancouver');
insert into Customers
    values('AAAA999', 'Marc Anthony', '4523 42nd Avenue, Vancouver');
insert into Customers
    values('AAAA100', 'Louis Down', '346 150th Street, Abbotsford');
insert into Customers
    values('AAAA000', 'Melanie Down', '5546 150th Street, Abbotsford');

insert into Customers
    values('AAAB666', 'Alex Macleod', '1235 41st Avenue, Vancouver');
insert into Customers
    values('AAAB777', 'Alex Gordan', '26 152th Street, Abbotsford');
insert into Customers
    values('AAAB888', 'Alex Dean', '1211 41st Street, Vancouver');
insert into Customers
    values('AAAB999', 'Alex Anthony', '523 42nd Avenue, Vancouver');
insert into Customers
    values('AAAB100', 'Alex Down', '3463 150th Street, Abbotsford');
insert into Customers
    values('AAAB000', 'Alex Down', '5546 150th Street, Abbotsford');


-- VehicleTypes
-- vtname VARCHAR(20),
--     features VARCHAR(20),
--     wrate INTEGER,
--     drate INTEGER,
--     hrate INTEGER,
--     wirate INTEGER,
--     dirate INTEGER,
--     hirate INTEGER,
--     krate INTEGER,
insert into VehicleTypes
   values('ECONOMY', 'No Heated-seats', 300, 50, 4, 50, 10, 1, 1);
insert into VehicleTypes
   values('COMPACT', 'Heated-seats', 350, 60, 4, 50, 10, 1, 1);
insert into VehicleTypes
   values('STANDARD', 'Heated-seats', 400, 65, 4, 50, 10, 1, 1);
insert into VehicleTypes
   values('MID-SIZE', 'Heated-seats', 420, 70, 4, 50, 10, 1, 1);
insert into VehicleTypes
   values('FULL-SIZE', 'Heated-seats', 450, 80, 6, 50, 10, 1, 1);
insert into VehicleTypes
   values('SUV', 'Heated-seats', 500, 85, 10, 60, 10, 1, 2);
insert into VehicleTypes
   values('TRUCK', 'Heated-seats', 580, 100, 10, 60, 10, 1, 2);

--Reservations
insert into Reservations
   values(2456, 'STANDARD', 'J9456GH', TIMESTAMP '2019-12-22 13:00:00', TIMESTAMP '2019-12-29 13:00:00');
insert into Reservations
   values(9656, 'TRUCK', 'J4449KZ', TIMESTAMP '2020-01-02 20:00:00', TIMESTAMP '2020-01-18 11:00:00');
insert into Reservations
   values(4201, 'SUV', 'Y3456YZ', TIMESTAMP '2020-01-02 20:00:00', TIMESTAMP '2020-01-10 9:00:00');
insert into Reservations
   values(8230, 'SUV', 'A1443BA', TIMESTAMP '2019-11-29 14:00:00', TIMESTAMP '2020-01-02 8:00:00');
insert into Reservations
   values(8234, 'COMPACT', 'A1443BA', TIMESTAMP '2020-03-15 13:00:00', TIMESTAMP '2020-03-27 10:00:00');
insert into Reservations
   values(3230, 'ECONOMY', 'Z2366BZ', TIMESTAMP '2019-11-29 13:00:00', TIMESTAMP '2019-12-25 14:00:00');
insert into Reservations
   values(2275, 'TRUCK', 'X4956ZZ', TIMESTAMP '2020-02-19 12:00:00', TIMESTAMP '2020-02-25 21:00:00');
insert into Reservations
   values(2455, 'COMPACT', 'J2356BJ', TIMESTAMP '2020-02-19 9:00:00', TIMESTAMP '2020-02-23 8:00:00');

-- Branch
  insert into Branch values('A', 'ABBOTSFORD' );
  insert into Branch values('C', 'CHILLIWACK' );
  insert into Branch values('V', 'VANCOUVER' );


 -- Vehicles
 --ECONOMY
 insert into Vehicles
     values(228975, 'AG3 45H', 'Toyota', 'Prius', '2019', 'Silver', 3000, 'AVAILABLE', 'ECONOMY', 'A', 'ABBOTSFORD');
 insert into Vehicles
     values(328975, 'BF3 43H', 'Toyota', 'Prius', '2018', 'Blue', 3090, 'AVAILABLE', 'ECONOMY', 'A', 'ABBOTSFORD');
 insert into Vehicles
     values(228976, 'JG9 69Z', 'Hyundai', 'Kona', '2018', 'Silver', 4500, 'AVAILABLE', 'ECONOMY', 'C', 'CHILLIWACK');
 insert into Vehicles
     values(228977, 'TG3 95H', 'Chevrolet', 'Bolt', '2018', 'Grey', 5000, 'AVAILABLE', 'ECONOMY', 'C', 'CHILLIWACK');
 insert into Vehicles
     values(391452, 'XG3 42H', 'Ford', 'Focus', '2017', 'Silver', 10000, 'AVAILABLE', 'ECONOMY', 'V', 'VANCOUVER');
 insert into Vehicles
     values(491452, 'HG9 42H', 'Ford', 'Focus', '2018', 'Silver', 10000, 'AVAILABLE', 'ECONOMY', 'V', 'VANCOUVER');
insert into Vehicles
    values(191452, 'XG3 42R', 'Ford', 'Focus', '2017', 'Silver', 20000, 'RENTED', 'ECONOMY', 'V', 'VANCOUVER');
insert into Vehicles
    values(191457, 'HG9 42R', 'Ford', 'Focus', '2018', 'Silver', 14000, 'RENTED', 'ECONOMY', 'V', 'VANCOUVER');


 -- COMPACT
 insert into Vehicles
     values(848975, 'GH9 45H', 'Toyota', 'Carolla', '2019', 'Silver', 4000, 'AVAILABLE', 'COMPACT', 'A', 'ABBOTSFORD');
 insert into Vehicles
     values(938275, 'PH9 43H', 'Honda', 'Civic', '2018', 'Blue', 9820, 'AVAILABLE', 'COMPACT', 'A', 'ABBOTSFORD');
 insert into Vehicles
     values(283941, 'JG9 89R', 'Honda', 'Civic', '2018', 'Silver', 2500, 'AVAILABLE', 'COMPACT', 'C', 'CHILLIWACK');
 insert into Vehicles
     values(293975, 'TG3 69P', 'Kia', 'Soul', '2018', 'Grey', 4500, 'AVAILABLE', 'COMPACT', 'C', 'CHILLIWACK');
 insert into Vehicles
     values(991562, 'TG3 45H', 'Kia', 'Soul', '2017', 'Silver', 23000, 'AVAILABLE', 'COMPACT', 'V', 'VANCOUVER');
 insert into Vehicles
     values(491442, 'HQ9 43H', 'Hyundai', 'Elantra', '2018', 'Silver', 30450, 'AVAILABLE', 'COMPACT', 'V', 'VANCOUVER');
insert into Vehicles
    values(191562, 'TG3 45R', 'Kia', 'Soul', '2017', 'Silver', 23000, 'RENTED', 'COMPACT', 'V', 'VANCOUVER');
insert into Vehicles
    values(191442, 'HQ9 43R', 'Hyundai', 'Elantra', '2018', 'Silver', 30450, 'RENTED', 'COMPACT', 'V', 'VANCOUVER');

--MID-SIZE
insert into Vehicles
    values(998000, 'LD1 33J', 'Hyundai', 'Sonata', '2019', 'Silver', 4100, 'AVAILABLE', 'MID-SIZE', 'A', 'ABBOTSFORD');
insert into Vehicles
    values(990009, 'LL9 22I', 'Hyundai', 'Sonata', '2018', 'Blue', 5820, 'AVAILABLE', 'MID-SIZE', 'A', 'ABBOTSFORD');
insert into Vehicles
    values(990008, 'LL8 99H', 'Hyundai', 'Sonata', '2018', 'Silver', 22500, 'AVAILABLE', 'MID-SIZE', 'C', 'CHILLIWACK');
insert into Vehicles
    values(990007, 'LL7 69G', 'Hyundai', 'Sonata', '2018', 'Grey', 9000, 'AVAILABLE', 'MID-SIZE', 'C', 'CHILLIWACK');
insert into Vehicles
    values(990006, 'LL6 45F', 'Hyundai', 'Sonata', '2017', 'Silver', 35000, 'AVAILABLE', 'MID-SIZE', 'V', 'VANCOUVER');
insert into Vehicles
    values(990005, 'LL5 43E', 'Hyundai', 'Sonata', '2018', 'Silver', 23450, 'AVAILABLE', 'MID-SIZE', 'V', 'VANCOUVER');

insert into Vehicles
    values(198000, 'RD1 33J', 'Hyundai', 'Sonata', '2019', 'Silver', 4100, 'RENTED', 'MID-SIZE', 'A', 'ABBOTSFORD');
insert into Vehicles
    values(190009, 'RL9 22I', 'Hyundai', 'Sonata', '2018', 'Blue', 5820, 'RENTED', 'MID-SIZE', 'A', 'ABBOTSFORD');
insert into Vehicles
    values(190006, 'RL6 45F', 'Hyundai', 'Sonata', '2017', 'Silver', 35000, 'RENTED', 'MID-SIZE', 'V', 'VANCOUVER');
insert into Vehicles
    values(190005, 'RL5 43E', 'Hyundai', 'Sonata', '2018', 'Silver', 23450, 'RENTED', 'MID-SIZE', 'V', 'VANCOUVER');

-- STANDARD
 insert into Vehicles
     values(998235, 'GG3 33J', 'Mazda', '3', '2019', 'Silver', 4100, 'AVAILABLE', 'STANDARD', 'A', 'ABBOTSFORD');
 insert into Vehicles
     values(900009, 'GG9 22I', 'Mazda', '3', '2018', 'Blue', 5820, 'AVAILABLE', 'STANDARD', 'A', 'ABBOTSFORD');
 insert into Vehicles
     values(900008, 'XX1 99H', 'Mazda', '3', '2018', 'Silver', 22500, 'AVAILABLE', 'STANDARD', 'C', 'CHILLIWACK');
 insert into Vehicles
     values(900007, 'XX2 69G', 'Mazda', '3', '2018', 'Grey', 9000, 'AVAILABLE', 'STANDARD', 'C', 'CHILLIWACK');
 insert into Vehicles
     values(900006, 'RR3 45F', 'Mazda', '3', '2017', 'Silver', 35000, 'AVAILABLE', 'STANDARD', 'V', 'VANCOUVER');
 insert into Vehicles
     values(900005, 'FF9 43E', 'Mazda', '3', '2018', 'Silver', 23450, 'AVAILABLE', 'STANDARD', 'V', 'VANCOUVER');
insert into Vehicles
    values(198235, 'RG3 33J', 'Mazda', '3', '2019', 'Silver', 4100, 'RENTED', 'STANDARD', 'A', 'ABBOTSFORD');
insert into Vehicles
    values(100009, 'RG9 22I', 'Mazda', '3', '2018', 'Blue', 5820, 'RENTED', 'STANDARD', 'A', 'ABBOTSFORD');

 --FULL-SIZE
 insert into Vehicles
     values(998000, 'LD1 50X', 'Dodge', 'Charger', '2019', 'Silver', 3100, 'AVAILABLE', 'FULL-SIZE', 'A', 'ABBOTSFORD');
 insert into Vehicles
     values(990009, 'LL9 50X', 'Dodge', 'Charger', '2018', 'Blue', 7820, 'AVAILABLE', 'FULL-SIZE', 'A', 'ABBOTSFORD');
 insert into Vehicles
     values(990008, 'LL8 50X', 'Dodge', 'Charger', '2018', 'Silver', 26500, 'AVAILABLE', 'FULL-SIZE', 'C', 'CHILLIWACK');
 insert into Vehicles
     values(990007, 'LL7 50X', 'Dodge', 'Charger', '2018', 'Grey', 6000, 'AVAILABLE', 'FULL-SIZE', 'C', 'CHILLIWACK');
 insert into Vehicles
     values(990006, 'LL6 50X', 'Dodge', 'Charger', '2017', 'Silver', 45000, 'AVAILABLE', 'FULL-SIZE', 'V', 'VANCOUVER');
 insert into Vehicles
     values(990005, 'LL5 50X', 'Dodge', 'Charger', '2018', 'Silver', 25450, 'AVAILABLE', 'FULL-SIZE', 'V', 'VANCOUVER');

insert into Vehicles
    values(198000, 'LD1 50R', 'Dodge', 'Charger', '2019', 'Silver', 3100, 'RENTED', 'FULL-SIZE', 'A', 'ABBOTSFORD');
insert into Vehicles
    values(190009, 'LL9 50R', 'Dodge', 'Charger', '2018', 'Blue', 7820, 'RENTED', 'FULL-SIZE', 'A', 'ABBOTSFORD');
insert into Vehicles
    values(190008, 'LL8 50R', 'Dodge', 'Charger', '2018', 'Silver', 26500, 'RENTED', 'FULL-SIZE', 'C', 'CHILLIWACK');
insert into Vehicles
    values(190007, 'LL7 50R', 'Dodge', 'Charger', '2018', 'Grey', 6000, 'RENTED', 'FULL-SIZE', 'C', 'CHILLIWACK');

 --SUV
 insert into Vehicles
     values(998090, 'TD1 50X', 'Toyota', '4Runner', '2019', 'Silver', 3100, 'AVAILABLE', 'SUV', 'A', 'ABBOTSFORD');
 insert into Vehicles
     values(990099, 'TL9 50X', 'Toyota', '4Runner', '2018', 'Blue', 7820, 'AVAILABLE', 'SUV', 'A', 'ABBOTSFORD');
 insert into Vehicles
     values(990098, 'TL8 50X', 'Toyota', '4Runner', '2018', 'Silver', 26500, 'AVAILABLE', 'SUV', 'C', 'CHILLIWACK');
 insert into Vehicles
     values(990097, 'TL7 50X', 'Toyota', '4Runner', '2018', 'Grey', 6000, 'AVAILABLE', 'SUV', 'C', 'CHILLIWACK');
 insert into Vehicles
     values(990096, 'TL6 50X', 'Toyota', '4Runner', '2017', 'Silver', 45000, 'AVAILABLE', 'SUV', 'V', 'VANCOUVER');
 insert into Vehicles
     values(990095, 'TL5 50X', 'Toyota', '4Runner', '2018', 'Silver', 25450, 'AVAILABLE', 'SUV', 'V', 'VANCOUVER');

insert into Vehicles
    values(198090, 'TD1 50R', 'Toyota', '4Runner', '2019', 'Silver', 3100, 'RENTED', 'SUV', 'A', 'ABBOTSFORD');
insert into Vehicles
    values(190099, 'TL9 50R', 'Toyota', '4Runner', '2018', 'Blue', 7820, 'RENTED', 'SUV', 'A', 'ABBOTSFORD');
insert into Vehicles
    values(190098, 'TL8 50R', 'Toyota', '4Runner', '2018', 'Silver', 26500, 'RENTED', 'SUV', 'C', 'CHILLIWACK');
insert into Vehicles
    values(190097, 'TL7 50R', 'Toyota', '4Runner', '2018', 'Grey', 6000, 'RENTED', 'SUV', 'C', 'CHILLIWACK');

--TRUCK
insert into Vehicles
    values(998990, 'TR1 50Z', 'Toyota', 'Tacoma', '2019', 'Silver', 3100, 'AVAILABLE', 'TRUCK', 'A', 'ABBOTSFORD');
insert into Vehicles
    values(990999, 'TR9 50Z', 'Toyota', 'Tacoma', '2018', 'Blue', 7820, 'AVAILABLE', 'TRUCK', 'A', 'ABBOTSFORD');
insert into Vehicles
    values(990998, 'TR8 50Z', 'Toyota', 'Tacoma', '2018', 'Silver', 26500, 'AVAILABLE', 'TRUCK', 'C', 'CHILLIWACK');
insert into Vehicles
    values(990997, 'TR7 50Z', 'Toyota', 'Tacoma', '2018', 'Grey', 6000, 'AVAILABLE', 'TRUCK', 'C', 'CHILLIWACK');
insert into Vehicles
    values(990996, 'TR6 50Z', 'Toyota', 'Tacoma', '2017', 'Silver', 45000, 'AVAILABLE', 'TRUCK', 'V', 'VANCOUVER');
insert into Vehicles
    values(990995, 'TR5 50Z', 'Toyota', 'Tacoma', '2018', 'Silver', 25450, 'AVAILABLE', 'TRUCK', 'V', 'VANCOUVER');

insert into Vehicles
    values(198990, 'TR1 51Z', 'Toyota', 'Tacoma', '2019', 'Silver', 3100, 'RENTED', 'TRUCK', 'A', 'ABBOTSFORD');
insert into Vehicles
    values(190999, 'TR9 51Z', 'Toyota', 'Tacoma', '2018', 'Blue', 7820, 'RENTED', 'TRUCK', 'A', 'ABBOTSFORD');
insert into Vehicles
    values(190998, 'TR8 51Z', 'Toyota', 'Tacoma', '2018', 'Silver', 26500, 'RENTED', 'TRUCK', 'C', 'CHILLIWACK');
insert into Vehicles
    values(190997, 'TR7 51Z', 'Toyota', 'Tacoma', '2018', 'Grey', 6000, 'RENTED', 'TRUCK', 'C', 'CHILLIWACK');



-- Rentals for today @ Abbotsford
insert into Rentals
    values(0001, 'TR1 51Z', 'AAAA111', SYSDATE, SYSDATE + 5, 3100,
    'mastercard', 51813344, '2020/02/22', NULL, NULL, NULL, NULL, NULL );
insert into Rentals
    values(0002, 'TR9 51Z', 'AAAA222', SYSDATE, SYSDATE + 3, 7820,
       'mastercard', 51814444, '2022/02/22', NULL, NULL, NULL, NULL, NULL);
insert into Rentals
    values(0003, 'TD1 50R', 'AAAA333', SYSDATE, SYSDATE + 5, 3100,
       'visa', 57813344, '2020/02/22', NULL, NULL, NULL, NULL, NULL );
insert into Rentals
    values(0004, 'TL9 50R', 'AAAA444', SYSDATE, SYSDATE + 7, 7820,
       'visa', 51814344, '2022/02/22', NULL, NULL, NULL, NULL, NULL);
insert into Rentals
    values(0005, 'LD1 50R', 'AAAA555', SYSDATE, SYSDATE + 2, 3100,
       'mastercard', 51819344, '2020/05/20', NULL, NULL, NULL, NULL, NULL );
insert into Rentals
    values(0006, 'LL9 50R', 'AAAA100', SYSDATE, SYSDATE + 4, 7820,
       'visa', 51814394, '2021/04/22', NULL, NULL, NULL, NULL, NULL);
insert into Rentals
    values(0007, 'RG3 33J', 'AAAA777', SYSDATE, SYSDATE + 7, 4100,
       'visa', 59129344, '2020/03/20', NULL, NULL, NULL, NULL, NULL );
insert into Rentals
    values(0008, 'RG9 22I', 'AAAA888', SYSDATE, SYSDATE + 10, 5820,
       'visa', 51814294, '2021/02/22', NULL, NULL, NULL, NULL, NULL);
insert into Rentals
    values(0009, 'RD1 33J', 'AAAA999', SYSDATE, SYSDATE + 2, 4100,
       'visa', 59129300, '2020/01/20', NULL, NULL, NULL, NULL, NULL );
insert into Rentals
    values(0010, 'RL9 22I', 'AAAA000', SYSDATE, SYSDATE + 12, 5820,
       'visa', 51814004, '2023/02/22', NULL, NULL, NULL, NULL, NULL);

--Rentals for today @ Chilliwack
insert into Rentals
    values(0011, 'TR8 51Z', 'A1456BZ', SYSDATE, SYSDATE + 5, 26500,
       'mastercard', 51913344, '2020/02/22', NULL, NULL, NULL, NULL, NULL );
insert into Rentals
    values(0012, 'TR7 51Z', 'A3456TZ', SYSDATE, SYSDATE + 3, 6000,
       'mastercard', 51914444, '2022/02/22', NULL, NULL, NULL, NULL, NULL);
insert into Rentals
    values(0013, 'TL8 50R', 'B2456HG', SYSDATE, SYSDATE + 5, 26500,
       'visa', 57913344, '2020/02/22', NULL, NULL, NULL, NULL, NULL );
insert into Rentals
    values(0014, 'TL7 50R', 'F1366BG', SYSDATE, SYSDATE + 7, 6000,
       'visa', 51914344, '2022/02/22', NULL, NULL, NULL, NULL, NULL);
insert into Rentals
    values(0015, 'LL8 50R', 'W2356BZ', SYSDATE, SYSDATE + 2, 26500,
       'mastercard', 59819344, '2020/05/20', NULL, NULL, NULL, NULL, NULL );
insert into Rentals
    values(0016, 'LL7 50R', 'X1956XZ', SYSDATE, SYSDATE + 5, 6000,
       'visa', 51914394, '2021/04/22', NULL, NULL, NULL, NULL, NULL);

--Rentals for today @ Vancouver
insert into Rentals
    values(0017, 'XG3 42R', 'A1446RR', SYSDATE, SYSDATE + 3, 20000,
       'mastercard', 51913374, '2020/02/22', NULL, NULL, NULL, NULL, NULL );
insert into Rentals
    values(0018, 'HG9 42R', 'W4456GZ', SYSDATE, SYSDATE + 3, 14000,
       'mastercard', 51914474, '2022/02/22', NULL, NULL, NULL, NULL, NULL);
insert into Rentals
    values(0019, 'TG3 45R', 'Y4446GZ', SYSDATE, SYSDATE + 3, 23000,
       'visa', 57913374, '2020/02/22', NULL, NULL, NULL, NULL, NULL );
insert into Rentals
    values(0020, 'HQ9 43R', 'F4436KZ', SYSDATE, SYSDATE + 7, 30450,
       'visa', 51914374, '2022/02/22', NULL, NULL, NULL, NULL, NULL);
insert into Rentals
    values(0021, 'RL6 45F', 'R2456GP', SYSDATE, SYSDATE + 2, 35000,
       'visa', 59819374, '2020/05/20', NULL, NULL, NULL, NULL, NULL );
insert into Rentals
    values(0022, 'RL5 43E', 'B5456BZ', SYSDATE, SYSDATE + 5, 23450,
       'visa', 51914374, '2021/04/22', NULL, NULL, NULL, NULL, NULL);

--Returns for today @ Abbotsford
insert into Rentals
    values(1000, 'TR1 50Z', 'AAAB666', SYSDATE - 5, SYSDATE, 2100,
       'visa', 51914374, '2021/04/22', NULL, 3100, NULL, 560, SYSDATE);
insert into Rentals
    values(1001, 'TR9 50Z', 'AAAB777', SYSDATE - 3, SYSDATE, 7320,
       'visa', 51914374, '2021/04/22', NULL, 7820, NULL, 360, SYSDATE);

insert into Rentals
    values(1002, 'TL8 50X', 'AAAB888', SYSDATE - 5, SYSDATE, 26000,
       'visa', 51914374, '2021/04/22', NULL, 26500, NULL, 560, SYSDATE);
insert into Rentals
    values(1003, 'LL7 50X', 'AAAB999', SYSDATE - 3, SYSDATE, 5800,
       'visa', 51914374, '2021/04/22', NULL, 6000, NULL, 295, SYSDATE);

insert into Rentals
    values(1004, 'TL6 50X', 'AAAB000', SYSDATE - 7, SYSDATE, 44300,
       'visa', 51914374, '2021/04/22', NULL, 45000, NULL, 860, SYSDATE);
insert into Rentals
    values(1005, 'TG3 45R', 'AAAB100', SYSDATE - 3, SYSDATE, 22500,
       'visa', 51914374, '2021/04/22', NULL, 23000, NULL, 295, SYSDATE);