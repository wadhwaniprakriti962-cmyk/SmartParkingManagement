package service;

import model.ParkingRecord;
import model.Vehicle;

import java.time.Duration;
import java.time.LocalDateTime;

public class FeeCalculator {

    public double calculateFee(ParkingRecord record) {

        Vehicle vehicle = record.getVehicle();

        LocalDateTime entryTime = record.getEntryTime();
        LocalDateTime exitTime = LocalDateTime.now();

        long minutes = Duration.between(entryTime, exitTime).toMinutes();

        // Minimum chargeable duration is 1 hour
        long hours = (long) Math.ceil(minutes / 60.0);

        if (hours < 1) {
            hours = 1;
        }

        double rate;

        switch (vehicle.getVehicleType()) {

            case "Bike":
                rate = 20;
                break;

            case "Car":
                rate = 40;
                break;

            case "Truck":
                rate = 60;
                break;

            default:
                rate = 40;
        }

        return hours * rate;
    }
}