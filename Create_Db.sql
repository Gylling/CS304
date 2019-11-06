CREATE DATABASE IF NOT EXISTS `SuperRent`;
USE `SuperRent`;

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `Reservations`;
DROP TABLE IF EXISTS `Rentals`;
DROP TABLE IF EXISTS `Vehicles`;
DROP TABLE IF EXISTS `VehicleTypes`;
DROP TABLE IF EXISTS `Customers`;
DROP TABLE IF EXISTS `Returns`;
DROP TABLE IF EXISTS `TimePeriod`;

CREATE TABLE `Reservations` (
    `confNo` INTEGER,
    `vtName` VARCHAR(20),
    `cellphone` VARCHAR(20),
    `fromDate` VARCHAR (20),
    `fromTime` VARCHAR (20),
    `toDate` VARCHAR (20),
    `toTime` VARCHAR (20),
    PRIMARY KEY (`confNo`),
    FOREIGN KEY (`vtName`) REFERENCES VehicleTypes,
    FOREIGN KEY (`cellphone`) REFERENCES Customers,
    FOREIGN Key (`fromDate`, `fromTime`, `toDate`, `toTime`) REFERENCES TimePeriod
);

CREATE TABLE `Rentals`(
    `rid` INTEGER,
    `vid` INTEGER,
    `cellphone` VARCHAR(20),
    `fromDate` VARCHAR(20),
    `fromTime` VARCHAR(20),
    `toDate` VARCHAR(20),
    `toTime` VARCHAR(20),
    `odometer` INTEGER,
    `cardName` VARCHAR(20),
    `cardNo` INTEGER,
    `expDate` VARCHAR(20),
    `confNo` INTEGER,
    PRIMARY KEY (`rid`),
    FOREIGN KEY (`cellphone`) REFERENCES Customers,
    FOREIGN Key (`fromDate`, `fromTime`, `toDate`, `toTime`) REFERENCES TimePeriod,
    FOREIGN KEY (`confNo`) REFERENCES Reservations (`confNo`)
);

CREATE TABLE `Vehicles`(
    `vid` INTEGER,
    `vlicense` VARCHAR(20),
    `make` VARCHAR(20),
    `model` VARCHAR(20),


);