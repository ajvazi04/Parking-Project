import java.util.Optional;

public class ParkingLotTest {
    public static void main(String[] args) throws Exception {
        testParkingAndHistory();
        testFullLotAndDuplicatePlate();
        testSpaceManagement();
        testValidation();
        System.out.println("All ParkingLot tests passed.");
    }

    private static void testParkingAndHistory() throws Exception {
        ParkingLot lot = new ParkingLot(2);
        Ticket ticket = lot.parkVehicle(new Car(" ABC123 ", "Alex"));

        check(ticket.getVehicle().getLicensePlate().equals("ABC123"),
                "Vehicle input should be trimmed.");
        check(lot.getOccupiedCount() == 1, "Parking should occupy one space.");
        Optional<ParkingSlot> found = lot.searchVehicle("abc123");
        check(found.isPresent() && found.get().getSlotNumber() == 1,
                "Search should ignore license-plate case.");

        Ticket completedTicket = lot.unparkVehicle(1);
        check(completedTicket == ticket, "Unparking should return the original ticket.");
        check(lot.getOccupiedCount() == 0, "Unparking should free the space.");
        check(lot.getHistory().size() == 1 && lot.getHistory().get(0) == ticket,
                "History should retain the completed ticket.");
        expectThrows(ParkingSpaceNotFoundException.class, () -> lot.unparkVehicle(1));
    }

    private static void testFullLotAndDuplicatePlate() throws Exception {
        ParkingLot lot = new ParkingLot(1);
        lot.parkVehicle(new Motorcycle("MOTO1", "Sam"));
        expectThrows(VehicleAlreadyParkedException.class,
                () -> lot.parkVehicle(new Car("moto1", "Another owner")));
        expectThrows(ParkingFullException.class,
                () -> lot.parkVehicle(new Truck("TRUCK1", "Taylor")));
    }

    private static void testSpaceManagement() throws Exception {
        ParkingLot lot = new ParkingLot(2);
        lot.removeSlot(2);
        check(lot.addSlot() == 3, "Added spaces should not reuse removed numbers.");
        lot.parkVehicle(new Car("CAR1", "Jordan"));
        expectThrows(IllegalStateException.class, () -> lot.removeSlot(1));
        expectThrows(ParkingSpaceNotFoundException.class, () -> lot.removeSlot(9));
    }

    private static void testValidation() {
        expectThrows(IllegalArgumentException.class, () -> new ParkingLot(0));
        expectThrows(IllegalArgumentException.class, () -> new Car(" ", "Owner"));
        expectThrows(IllegalArgumentException.class, () -> new Truck("TRUCK1", ""));
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    private static void expectThrows(Class<? extends Exception> expectedType,
                                    ThrowingAction action) {
        try {
            action.run();
        } catch (Exception exception) {
            if (expectedType.isInstance(exception)) {
                return;
            }
            throw new AssertionError("Expected " + expectedType.getSimpleName()
                    + " but got " + exception.getClass().getSimpleName(), exception);
        }
        throw new AssertionError("Expected " + expectedType.getSimpleName()
                + " to be thrown.");
    }

    @FunctionalInterface
    private interface ThrowingAction {
        void run() throws Exception;
    }
}
