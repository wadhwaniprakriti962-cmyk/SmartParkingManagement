package util;

import model.Bike;
import model.Car;
import model.ParkingRecord;
import model.Truck;
import model.Vehicle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ParkingFileManager {

    private static final String HISTORY_FILE =
            "data/parking_history.txt";

    public void saveParkingRecord(ParkingRecord record) {

        try {

            File dataFolder = new File("data");

            if (!dataFolder.exists()) {
                dataFolder.mkdir();
            }

            FileWriter writer =
                    new FileWriter(HISTORY_FILE, true);

            writer.write(
                    "Vehicle Number: "
                    + record.getVehicle().getVehicleNumber()
                    + "\n"
            );

            writer.write(
                    "Vehicle Type: "
                    + record.getVehicle().getVehicleType()
                    + "\n"
            );

            writer.write(
                    "Owner: "
                    + record.getVehicle().getOwnerName()
                    + "\n"
            );

            writer.write(
                    "Slot: "
                    + record.getSlotNumber()
                    + "\n"
            );

            writer.write(
                    "Entry: "
                    + record.getFormattedEntryTime()
                    + "\n"
            );

            writer.write(
                    "Exit: "
                    + record.getFormattedExitTime()
                    + "\n"
            );

            writer.write(
                    "Fee: Rs."
                    + record.getParkingFee()
                    + "\n"
            );

            writer.write(
                    "----------------------------\n"
            );

            writer.close();

        } catch (IOException e) {

            System.out.println(
                    "Error while saving parking record: "
                    + e.getMessage()
            );
        }
    }


    public List<ParkingRecord> loadParkingHistory() {

        List<ParkingRecord> records =
                new ArrayList<>();

        File historyFile =
                new File(HISTORY_FILE);

        if (!historyFile.exists()) {
            return records;
        }

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern(
                        "dd-MM-yyyy HH:mm:ss"
                );

        try {

            BufferedReader reader =
                    new BufferedReader(
                            new FileReader(historyFile)
                    );

            String line;

            String vehicleNumber = "";
            String vehicleType = "";
            String ownerName = "";
            int slotNumber = 0;
            LocalDateTime entryTime = null;
            LocalDateTime exitTime = null;
            double parkingFee = 0.0;

            while ((line = reader.readLine()) != null) {

                if (line.startsWith("Vehicle Number: ")) {

                    vehicleNumber =
                            line.substring(
                                    "Vehicle Number: ".length()
                            );
                }

                else if (line.startsWith("Vehicle Type: ")) {

                    vehicleType =
                            line.substring(
                                    "Vehicle Type: ".length()
                            );
                }

                else if (line.startsWith("Owner: ")) {

                    ownerName =
                            line.substring(
                                    "Owner: ".length()
                            );
                }

                else if (line.startsWith("Slot: ")) {

                    slotNumber =
                            Integer.parseInt(
                                    line.substring(
                                            "Slot: ".length()
                                    )
                            );
                }

                else if (line.startsWith("Entry: ")) {

                    entryTime =
                            LocalDateTime.parse(
                                    line.substring(
                                            "Entry: ".length()
                                    ),
                                    formatter
                            );
                }

                else if (line.startsWith("Exit: ")) {

                    String exitValue =
                            line.substring(
                                    "Exit: ".length()
                            );

                    if (!exitValue.equals("Still Parked")) {

                        exitTime =
                                LocalDateTime.parse(
                                        exitValue,
                                        formatter
                                );
                    }
                }

                else if (line.startsWith("Fee: Rs.")) {

                    parkingFee =
                            Double.parseDouble(
                                    line.substring(
                                            "Fee: Rs.".length()
                                    )
                            );
                }

                else if (line.startsWith("-")) {

                    Vehicle vehicle = null;

                    if (vehicleType.equals("Car")) {

                        vehicle =
                                new Car(
                                        vehicleNumber,
                                        ownerName
                                );

                    } else if (vehicleType.equals("Bike")) {

                        vehicle =
                                new Bike(
                                        vehicleNumber,
                                        ownerName
                                );

                    } else if (vehicleType.equals("Truck")) {

                        vehicle =
                                new Truck(
                                        vehicleNumber,
                                        ownerName
                                );
                    }

                    if (vehicle != null && entryTime != null) {

                        ParkingRecord record =
                                new ParkingRecord(
                                        vehicle,
                                        slotNumber
                                );

                        record.setEntryTime(entryTime);
                        record.setExitTime(exitTime);
                        record.setParkingFee(parkingFee);

                        records.add(record);
                    }

                    vehicleNumber = "";
                    vehicleType = "";
                    ownerName = "";
                    slotNumber = 0;
                    entryTime = null;
                    exitTime = null;
                    parkingFee = 0.0;
                }
            }

            reader.close();

        } catch (IOException | NumberFormatException e) {

            System.out.println(
                    "Error while loading parking history: "
                    + e.getMessage()
            );
        }

        return records;
    }
}