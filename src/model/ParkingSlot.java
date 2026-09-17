package model;

public class ParkingSlot {

    private int slotNumber;
    private Vehicle vehicle;

    public ParkingSlot(int slotNumber) {
        this.slotNumber = slotNumber;
        this.vehicle = null;
    }

    public int getSlotNumber() {
        return slotNumber;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public boolean isOccupied() {
        return vehicle != null;
    }

    public void parkVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public void removeVehicle() {
        this.vehicle = null;
    }
}