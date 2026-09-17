package service;

import exception.ParkingFullException;
import exception.VehicleNotFoundException;
import model.ParkingRecord;
import model.ParkingSlot;
import model.Vehicle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import util.ParkingFileManager;

public class ParkingManager {

    private List<ParkingSlot> parkingSlots;
    private Map<String, ParkingRecord> activeRecords;
    private List<ParkingRecord> parkingHistory;

    public ParkingManager(int totalSlots) {

    parkingSlots = new ArrayList<>();
    activeRecords = new HashMap<>();
    parkingHistory = new ArrayList<>();

    for (int i = 1; i <= totalSlots; i++) {
        parkingSlots.add(new ParkingSlot(i));
    }

    ParkingFileManager fileManager =
            new ParkingFileManager();

    parkingHistory =
            fileManager.loadParkingHistory();
}

    public List<ParkingSlot> getParkingSlots() {
        return parkingSlots;
    }

    public Map<String, ParkingRecord> getActiveRecords() {
        return activeRecords;
    }

    public List<ParkingRecord> getParkingHistory() {
        return parkingHistory;
    }

    public int findAvailableSlot() {

        for (ParkingSlot slot : parkingSlots) {

            if (!slot.isOccupied()) {
                return slot.getSlotNumber();
            }
        }

        return -1;
    }

    public void displayParkingStatus() {

        System.out.println("\n--- Parking Status ---");

        for (ParkingSlot slot : parkingSlots) {

            if (slot.isOccupied()) {

                Vehicle vehicle = slot.getVehicle();

                System.out.println(
                        "Slot " + slot.getSlotNumber()
                        + " : Occupied | "
                        + vehicle.getVehicleType()
                        + " | "
                        + vehicle.getVehicleNumber()
                        + " | Owner: "
                        + vehicle.getOwnerName()
                );

            } else {

                System.out.println(
                        "Slot " + slot.getSlotNumber()
                        + " : Available"
                );
            }
        }
    }

    public int parkVehicle(Vehicle vehicle)
            throws ParkingFullException {

        if (activeRecords.containsKey(vehicle.getVehicleNumber())) {

            throw new IllegalArgumentException(
                    "Vehicle is already parked."
            );
        }

        int slotNumber = findAvailableSlot();

        if (slotNumber == -1) {

            throw new ParkingFullException(
                    "No parking slot is available."
            );
        }

        ParkingSlot slot =
                parkingSlots.get(slotNumber - 1);

        slot.parkVehicle(vehicle);

        ParkingRecord record =
                new ParkingRecord(vehicle, slotNumber);

        activeRecords.put(
                vehicle.getVehicleNumber(),
                record
        );

        return slotNumber;
    }

    public double removeVehicle(String vehicleNumber)
            throws VehicleNotFoundException {

        ParkingRecord record =
                activeRecords.get(vehicleNumber);

        if (record == null) {

            throw new VehicleNotFoundException(
                    "Vehicle " + vehicleNumber
                    + " is not currently parked."
            );
        }

        FeeCalculator feeCalculator =
                new FeeCalculator();

        double fee =
                feeCalculator.calculateFee(record);

        record.closeRecord(fee);

        int slotNumber =
                record.getSlotNumber();

        ParkingSlot slot =
                parkingSlots.get(slotNumber - 1);

        slot.removeVehicle();

        activeRecords.remove(vehicleNumber);

        parkingHistory.add(record);
        ParkingFileManager fileManager =
        new ParkingFileManager();
        fileManager.saveParkingRecord(record);

        return fee;
    }

    public ParkingRecord searchVehicle(String vehicleNumber)
            throws VehicleNotFoundException {

        ParkingRecord record =
                activeRecords.get(vehicleNumber);

        if (record == null) {

            throw new VehicleNotFoundException(
                    "Vehicle " + vehicleNumber
                    + " is not currently parked."
            );
        }

        return record;
    }

    public void displayParkingHistory() {

        System.out.println("\n--- Parking History ---");

        if (parkingHistory.isEmpty()) {

            System.out.println(
                    "No parking history available."
            );

            return;
        }

        for (ParkingRecord record : parkingHistory) {

            System.out.println(
                    "Vehicle: "
                    + record.getVehicle()
                            .getVehicleNumber()
            );

            System.out.println(
                    "Type: "
                    + record.getVehicle()
                            .getVehicleType()
            );

            System.out.println(
                    "Owner: "
                    + record.getVehicle()
                            .getOwnerName()
            );

            System.out.println(
                    "Slot: "
                    + record.getSlotNumber()
            );

            System.out.println(
                    "Entry: "
                    + record.getFormattedEntryTime()
            );

            System.out.println(
                    "Exit: "
                    + record.getFormattedExitTime()
            );

            System.out.println(
                    "Fee: Rs."
                    + record.getParkingFee()
            );

            System.out.println(
                    "----------------------------"
            );
        }
    }
}