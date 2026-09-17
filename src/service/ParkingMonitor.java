package service;

import model.ParkingSlot;

import java.util.List;

public class ParkingMonitor implements Runnable {

    private ParkingManager manager;
    private boolean running;
    private int previousOccupied;

    public ParkingMonitor(ParkingManager manager) {
        this.manager = manager;
        this.running = true;
        this.previousOccupied = -1;
    }

    @Override
    public void run() {

        while (running) {

            int occupied = 0;

            List<ParkingSlot> slots =
                    manager.getParkingSlots();

            for (ParkingSlot slot : slots) {

                if (slot.isOccupied()) {
                    occupied++;
                }
            }

            if (occupied != previousOccupied) {

                int available =
                        slots.size() - occupied;

                System.out.println(
                        "\n[Parking Monitor] Occupied: "
                        + occupied
                        + " | Available: "
                        + available
                );

                previousOccupied = occupied;
            }

            try {

                Thread.sleep(10000);

            } catch (InterruptedException e) {

                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public void stopMonitor() {
        running = false;
    }
}