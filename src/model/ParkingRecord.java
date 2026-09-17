package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ParkingRecord {

    private Vehicle vehicle;
    private int slotNumber;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private double parkingFee;

    public ParkingRecord(Vehicle vehicle, int slotNumber) {
        this.vehicle = vehicle;
        this.slotNumber = slotNumber;
        this.entryTime = LocalDateTime.now();
        this.exitTime = null;
        this.parkingFee = 0.0;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public int getSlotNumber() {
        return slotNumber;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public double getParkingFee() {
        return parkingFee;
    }

    public void closeRecord(double parkingFee) {
        this.exitTime = LocalDateTime.now();
        this.parkingFee = parkingFee;
    }

    public void setEntryTime(LocalDateTime entryTime) {
        this.entryTime = entryTime;
    }

    public void setExitTime(LocalDateTime exitTime) {
        this.exitTime = exitTime;
    }

    public void setParkingFee(double parkingFee) {
        this.parkingFee = parkingFee;
    }

    public boolean isActive() {
        return exitTime == null;
    }

    public String getFormattedEntryTime() {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        return entryTime.format(formatter);
    }

    public String getFormattedExitTime() {

        if (exitTime == null) {
            return "Still Parked";
        }

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        return exitTime.format(formatter);
    }
}