import java.util.Scanner;
import java.time.format.DateTimeFormatter;

class ParkingFullException extends Exception {
    public ParkingFullException(String message) { super(message); }
}

class TicketNotFoundException extends Exception {
    public TicketNotFoundException(String message) { super(message); }
}

class InvalidVehicleTypeException extends Exception {
    public InvalidVehicleTypeException(String message) { super(message); }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ParkingLot lot = new ParkingLot(8); // initial slots

        while (true) {
            System.out.println("\n==================================");
            System.out.println("       PARKING MANAGEMENT");
            System.out.println("==================================");
            System.out.println("Occupied slots: " + lot.getOccupiedCount() + "/" + lot.getTotalSlots());
            System.out.println("1) Park Vehicle");
            System.out.println("2) Unpark Vehicle (by Slot Number)");
            System.out.println("3) List Parked Vehicles");
            System.out.println("4) Search Vehicle (by License Plate)");
            System.out.println("5) Manage Slots (Add / Remove / List)");
            System.out.println("6) View History (completed records)");
            System.out.println("0) Exit");
            System.out.print("\nSelect an option: ");
            int option;
            try {
                option = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid option. Please enter a number from 0 to 6.");
                continue;
            }

            try {
                switch (option) {
                    case 1 -> {
                        System.out.println("~~~ Park Vehicle ~~~");
                        System.out.print("License Plate: ");
                        String plate = scanner.nextLine().trim();
                        System.out.print("Owner Name: ");
                        String owner = scanner.nextLine().trim();
                        if (plate.isEmpty() || owner.isEmpty()) {
                            System.out.println("License plate and owner name cannot be empty.");
                            break;
                        }
                        System.out.print("Vehicle Type (Car/Motorcycle/Truck): ");
                        String type = scanner.nextLine().trim().toLowerCase(java.util.Locale.ROOT);
                        Vehicle vehicle;
                        switch (type) {
                            case "car" -> vehicle = new Car(plate, owner);
                            case "motorcycle" -> vehicle = new Motorcycle(plate, owner);
                            case "truck" -> vehicle = new Truck(plate, owner);
                            default -> throw new InvalidVehicleTypeException("Invalid vehicle type!");
                        }
                        ParkingSlot slot = lot.parkVehicle(vehicle);
                        System.out.println("Vehicle parked successfully.");
                        System.out.println("Ticket: " + slot.getTicketID() + " | Slot: " + slot.getSlotNumber()
                                + " | Entry: " + slot.getEntryTime().format(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a")));
                    }
                    case 2 -> {
                        System.out.println("~~~ Unpark Vehicle ~~~");
                        System.out.print("Slot Number: ");
                        int slotNum = Integer.parseInt(scanner.nextLine().trim());
                        Vehicle vehicle = lot.unparkVehicle(slotNum);
                        System.out.println("Vehicle unparked: " + vehicle);
                    }
                    case 3 -> {
                        System.out.println("~~~ Parked Vehicles ~~~");
                        lot.listParkedVehicles();
                    }
                    case 4 -> {
                        System.out.println("~~~ Search Vehicle ~~~");
                        System.out.print("License Plate: ");
                        String plate = scanner.nextLine();
                        ParkingSlot found = lot.searchVehicle(plate);
                        if (found != null) System.out.println("Found at slot " + found.getSlotNumber() + ": " + found.getVehicle());
                        else System.out.println("Vehicle not found.");
                    }
                    case 5 -> {
                        System.out.println("~~~ Manage Slots ~~~");
                        System.out.println("1) Add Slot\n2) Remove Slot\n3) List Slots");
                        System.out.print("Choose: ");
                        int choice = Integer.parseInt(scanner.nextLine().trim());
                        if (choice == 1) lot.addSlot();
                        else if (choice == 2) {
                            System.out.print("Slot Number to Remove: ");
                            int s = Integer.parseInt(scanner.nextLine().trim());
                            lot.removeSlot(s);
                        } else if (choice == 3) lot.listSlots();
                        else System.out.println("Invalid option.");
                    }
                    case 6 -> {
                        System.out.println("~~~ Parking History ~~~");
                        lot.viewHistory();
                    }
                    case 0 -> { System.out.println("Exiting..."); return; }
                    default -> System.out.println("Invalid option.");
                }
            } catch (ParkingFullException | TicketNotFoundException | InvalidVehicleTypeException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter numbers where required.");
            }

            System.out.println("\nPress Enter to continue...");
            scanner.nextLine();
        }
    }
}
