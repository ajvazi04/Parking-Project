import java.util.Locale;
import java.util.Optional;
import java.util.Scanner;

public class ParkingApplication {
    private final Scanner scanner;
    private final ParkingLot parkingLot;

    public ParkingApplication(Scanner scanner, ParkingLot parkingLot) {
        this.scanner = scanner;
        this.parkingLot = parkingLot;
    }

    public void run() {
        while (scanner.hasNextLine()) {
            printMenu();
            String input = scanner.nextLine().trim();

            try {
                int option = Integer.parseInt(input);
                if (!handleOption(option)) {
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number from 0 to 6.");
            }

            if (scanner.hasNextLine()) {
                System.out.print("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }
    }

    private void printMenu() {
        System.out.println("\n==================================");
        System.out.println("       PARKING MANAGEMENT");
        System.out.println("==================================");
        System.out.printf("Occupied spaces: %d/%d%n",
                parkingLot.getOccupiedCount(), parkingLot.getTotalSlots());
        System.out.println("1. Park a vehicle");
        System.out.println("2. Remove a vehicle");
        System.out.println("3. List parked vehicles");
        System.out.println("4. Search by license plate");
        System.out.println("5. Manage parking spaces");
        System.out.println("6. View parking history");
        System.out.println("0. Exit");
        System.out.print("\nSelect an option: ");
    }

    private boolean handleOption(int option) {
        switch (option) {
            case 1 -> parkVehicle();
            case 2 -> removeVehicle();
            case 3 -> listParkedVehicles();
            case 4 -> searchVehicle();
            case 5 -> manageSpaces();
            case 6 -> showHistory();
            case 0 -> {
                System.out.println("Goodbye!");
                return false;
            }
            default -> System.out.println("Invalid option. Choose a number from 0 to 6.");
        }
        return true;
    }

    private void parkVehicle() {
        String plate = readRequired("License plate: ");
        String owner = readRequired("Owner name: ");
        String type = readRequired("Vehicle type (car, motorcycle, truck): ");
        if (plate == null || owner == null || type == null) {
            return;
        }

        try {
            Vehicle vehicle = createVehicle(type, plate, owner);
            Ticket ticket = parkingLot.parkVehicle(vehicle);
            System.out.println("Vehicle parked successfully.");
            System.out.println(ticket);
        } catch (InvalidVehicleTypeException | ParkingFullException
                 | VehicleAlreadyParkedException | IllegalArgumentException e) {
            System.out.println("Could not park vehicle: " + e.getMessage());
        }
    }

    private Vehicle createVehicle(String type, String plate, String owner)
            throws InvalidVehicleTypeException {
        return switch (type.trim().toLowerCase(Locale.ROOT)) {
            case "car" -> new Car(plate, owner);
            case "motorcycle" -> new Motorcycle(plate, owner);
            case "truck" -> new Truck(plate, owner);
            default -> throw new InvalidVehicleTypeException(
                    "Choose car, motorcycle, or truck.");
        };
    }

    private void removeVehicle() {
        Integer slotNumber = readInteger("Parking space number: ");
        if (slotNumber == null) {
            return;
        }

        try {
            Ticket ticket = parkingLot.unparkVehicle(slotNumber);
            System.out.println("Vehicle removed. " + ticket);
        } catch (ParkingSpaceNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private void listParkedVehicles() {
        boolean foundVehicle = false;
        for (ParkingSlot slot : parkingLot.getSlots()) {
            if (slot.isOccupied()) {
                System.out.printf("Space %d | %s%n", slot.getSlotNumber(), slot.getVehicle());
                foundVehicle = true;
            }
        }
        if (!foundVehicle) {
            System.out.println("No vehicles are currently parked.");
        }
    }

    private void searchVehicle() {
        String plate = readRequired("License plate: ");
        if (plate == null) {
            return;
        }

        Optional<ParkingSlot> result = parkingLot.searchVehicle(plate);
        if (result.isPresent()) {
            ParkingSlot slot = result.get();
            System.out.printf("Found in space %d: %s%n", slot.getSlotNumber(), slot.getVehicle());
        } else {
            System.out.println("No parked vehicle matches that license plate.");
        }
    }

    private void manageSpaces() {
        System.out.println("1. Add a space");
        System.out.println("2. Remove an empty space");
        System.out.println("3. List all spaces");
        Integer option = readInteger("Choose an option: ");
        if (option == null) {
            return;
        }

        switch (option) {
            case 1 -> System.out.println("Added parking space "
                    + parkingLot.addSlot() + ".");
            case 2 -> {
                Integer slotNumber = readInteger("Space number to remove: ");
                if (slotNumber != null) {
                    try {
                        parkingLot.removeSlot(slotNumber);
                        System.out.println("Parking space removed.");
                    } catch (ParkingSpaceNotFoundException | IllegalStateException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
            case 3 -> {
                for (ParkingSlot slot : parkingLot.getSlots()) {
                    String status = slot.isOccupied()
                            ? "Occupied by " + slot.getVehicle()
                            : "Available";
                    System.out.printf("Space %d | %s%n", slot.getSlotNumber(), status);
                }
            }
            default -> System.out.println("Invalid option. Choose 1, 2, or 3.");
        }
    }

    private void showHistory() {
        if (parkingLot.getHistory().isEmpty()) {
            System.out.println("No completed parking records yet.");
            return;
        }
        for (Ticket ticket : parkingLot.getHistory()) {
            System.out.println(ticket);
        }
    }

    private String readRequired(String prompt) {
        while (scanner.hasNextLine()) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("This field cannot be empty.");
        }
        return null;
    }

    private Integer readInteger(String prompt) {
        if (!scanner.hasNextLine()) {
            return null;
        }
        System.out.print(prompt);
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid whole number.");
            return null;
        }
    }
}
