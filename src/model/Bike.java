package model;

public class Bike extends Vehicle {

    public Bike(String vehicleNumber, String ownerName) {
        super(vehicleNumber, ownerName);
    }

    @Override
    public String getVehicleType() {
        return "Bike";
    }
}