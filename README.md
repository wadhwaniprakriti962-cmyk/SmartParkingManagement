# Smart Parking Management System

## About the Project

Smart Parking Management System is a command-line based Java application for managing vehicles in a parking area.

The system allows the user to park and remove vehicles, check available parking slots, search for a currently parked vehicle, and view previous parking records.

I built this project to practice Java concepts such as object-oriented programming, inheritance, polymorphism, exception handling, collections, file handling, and multithreading in one application.

## Features

- Park a Car, Bike, or Truck
- Automatically assign an available parking slot
- Prevent the same vehicle from being parked twice
- Remove a parked vehicle
- Calculate parking fees according to vehicle type and parking duration
- View the current parking status
- Search for a parked vehicle using its vehicle number
- View completed parking history
- Save parking records in a file
- Load saved parking history when the program starts
- Validate user input
- Handle parking-related errors using custom exceptions
- Run a background parking monitor using a separate thread

## Technologies Used

- Java
- Java Collections
- Java I/O
- Java Time API
- Multithreading
- Command Line Interface (CLI)

No external libraries are required.

## Project Structure

```text
SmartParkingManagement/
│
├── src/
│   ├── model/
│   │   ├── Vehicle.java
│   │   ├── Car.java
│   │   ├── Bike.java
│   │   ├── Truck.java
│   │   ├── ParkingSlot.java
│   │   └── ParkingRecord.java
│   │
│   ├── service/
│   │   ├── ParkingManager.java
│   │   ├── FeeCalculator.java
│   │   └── ParkingMonitor.java
│   │
│   ├── exception/
│   │   ├── ParkingFullException.java
│   │   └── VehicleNotFoundException.java
│   │
│   ├── util/
│   │   └── ParkingFileManager.java
│   │
│   └── Main.java
│
├── data/
│   └── parking_history.txt
│
├── .gitignore
└── README.md
