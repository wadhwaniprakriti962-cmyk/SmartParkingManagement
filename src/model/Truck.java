package model;

public class Truck extends Vehicle {

    public Truck(String vehicleNumber, String ownerName) {
        super(vehicleNumber, ownerName);
    }

    @Override
    public String getVehicleType() {
        return "Truck";
    }
}
