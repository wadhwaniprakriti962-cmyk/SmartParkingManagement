import exception.ParkingFullException;
import exception.VehicleNotFoundException;
import model.Bike;
import model.Car;
import model.ParkingRecord;
import model.Truck;
import model.Vehicle;
import service.ParkingManager;
import service.ParkingMonitor;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        ParkingManager manager = new ParkingManager(5);
        ParkingMonitor monitor =
        new ParkingMonitor(manager);

Thread monitorThread =
        new Thread(monitor);

monitorThread.start();

        boolean running = true;

        while (running) {

            System.out.println("\n================================");
            System.out.println("   SMART PARKING MANAGEMENT");
            System.out.println("================================");
            System.out.println("1. Park Vehicle");
            System.out.println("2. Remove Vehicle");
            System.out.println("3. View Parking Status");
            System.out.println("4. Search Vehicle");
            System.out.println("5. View Parking History");
            System.out.println("6. Exit");
            System.out.println("================================");
            System.out.print("Enter your choice: ");

            if (!scanner.hasNextInt()) {

                System.out.println(
                        "Invalid input. Please enter a number."
                );

                scanner.nextLine();
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    parkVehicle(scanner, manager);
                    break;

                case 2:
                    removeVehicle(scanner, manager);
                    break;

                case 3:
                    manager.displayParkingStatus();
                    break;

                case 4:
                    searchVehicle(scanner, manager);
                    break;

                case 5:
                    manager.displayParkingHistory();
                    break;

                case 6:
                    running = false;

                    System.out.println(
                            "Thank you for using Smart Parking Management."
                    );

                    break;

                default:
                    System.out.println(
                            "Invalid choice. Please choose between 1 and 6."
                    );
            }
        }
        monitor.stopMonitor();
        scanner.close();
    }


    public static void parkVehicle(
            Scanner scanner,
            ParkingManager manager) {

        System.out.println("\n--- Park Vehicle ---");

        System.out.println("1. Car");
        System.out.println("2. Bike");
        System.out.println("3. Truck");
        System.out.print("Enter vehicle type: ");

        if (!scanner.hasNextInt()) {

            System.out.println(
                    "Invalid vehicle type. Please enter a number."
            );

            scanner.nextLine();
            return;
        }

        int type = scanner.nextInt();
        scanner.nextLine();

        if (type < 1 || type > 3) {

            System.out.println(
                    "Invalid vehicle type. Please choose 1, 2 or 3."
            );

            return;
        }

        System.out.print("Enter vehicle number: ");

        String vehicleNumber =
                scanner.nextLine().trim();

        if (vehicleNumber.isEmpty()) {

            System.out.println(
                    "Vehicle number cannot be empty."
            );

            return;
        }

        if (!vehicleNumber.matches("[a-zA-Z0-9-]+")) {

            System.out.println(
                    "Vehicle number can contain only letters, numbers and hyphens."
            );

            return;
        }

        System.out.print("Enter owner name: ");

        String ownerName =
                scanner.nextLine().trim();

        if (ownerName.isEmpty()) {

            System.out.println(
                    "Owner name cannot be empty."
            );

            return;
        }

        if (!ownerName.matches("[a-zA-Z ]+")) {

            System.out.println(
                    "Owner name should contain only letters and spaces."
            );

            return;
        }

        Vehicle vehicle;

        switch (type) {

            case 1:
                vehicle = new Car(
                        vehicleNumber,
                        ownerName
                );
                break;

            case 2:
                vehicle = new Bike(
                        vehicleNumber,
                        ownerName
                );
                break;

            case 3:
                vehicle = new Truck(
                        vehicleNumber,
                        ownerName
                );
                break;

            default:
                return;
        }

        try {

            int slot =
                    manager.parkVehicle(vehicle);

            System.out.println(
                    "Vehicle parked successfully in slot: "
                    + slot
            );

        } catch (ParkingFullException e) {

            System.out.println(
                    "Error: " + e.getMessage()
            );

        } catch (IllegalArgumentException e) {

            System.out.println(
                    "Error: " + e.getMessage()
            );
        }
    }


    public static void removeVehicle(
            Scanner scanner,
            ParkingManager manager) {

        System.out.println("\n--- Remove Vehicle ---");

        System.out.print("Enter vehicle number: ");

        String vehicleNumber =
                scanner.nextLine().trim();

        if (vehicleNumber.isEmpty()) {

            System.out.println(
                    "Vehicle number cannot be empty."
            );

            return;
        }

        if (!vehicleNumber.matches("[a-zA-Z0-9-]+")) {

            System.out.println(
                    "Vehicle number can contain only letters, numbers and hyphens."
            );

            return;
        }

        try {

            double fee =
                    manager.removeVehicle(vehicleNumber);

            System.out.println(
                    "Vehicle removed successfully."
            );

            System.out.println(
                    "Parking fee: Rs." + fee
            );

        } catch (VehicleNotFoundException e) {

            System.out.println(
                    "Error: " + e.getMessage()
            );
        }
    }


    public static void searchVehicle(
            Scanner scanner,
            ParkingManager manager) {

        System.out.println("\n--- Search Vehicle ---");

        System.out.print("Enter vehicle number: ");

        String vehicleNumber =
                scanner.nextLine().trim();

        if (vehicleNumber.isEmpty()) {

            System.out.println(
                    "Vehicle number cannot be empty."
            );

            return;
        }

        if (!vehicleNumber.matches("[a-zA-Z0-9-]+")) {

            System.out.println(
                    "Vehicle number can contain only letters, numbers and hyphens."
            );

            return;
        }

        try {

            ParkingRecord record =
                    manager.searchVehicle(vehicleNumber);

            System.out.println(
                    "Vehicle found!"
            );

            System.out.println(
                    "Vehicle Number: "
                    + record.getVehicle().getVehicleNumber()
            );

            System.out.println(
                    "Vehicle Type: "
                    + record.getVehicle().getVehicleType()
            );

            System.out.println(
                    "Owner: "
                    + record.getVehicle().getOwnerName()
            );

            System.out.println(
                    "Parking Slot: "
                    + record.getSlotNumber()
            );

            System.out.println(
                    "Entry Time: "
                    + record.getFormattedEntryTime()
            );

        } catch (VehicleNotFoundException e) {

            System.out.println(
                    "Error: " + e.getMessage()
            );
        }
    }
}