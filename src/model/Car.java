package model;

public class Car extends Vehicle {

    public Car(String vehicleNumber, String ownerName) {
        super(vehicleNumber, ownerName);
    }

    @Override
    public String getVehicleType() {
        return "Car";
    }
}