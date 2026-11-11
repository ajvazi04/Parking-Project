import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ParkingLot {
    private final List<ParkingSlot> slots = new ArrayList<>();
    private final List<Ticket> history = new ArrayList<>();
    private int nextSlotNumber = 1;

    public ParkingLot(int initialSlotCount) {
        if (initialSlotCount < 1) {
            throw new IllegalArgumentException("A parking lot must have at least one space.");
        }
        for (int number = 1; number <= initialSlotCount; number++) {
            slots.add(new ParkingSlot(number));
            nextSlotNumber = number + 1;
        }
    }

    public Ticket parkVehicle(Vehicle vehicle)
            throws ParkingFullException, VehicleAlreadyParkedException {
        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle cannot be null.");
        }
        if (searchVehicle(vehicle.getLicensePlate()).isPresent()) {
            throw new VehicleAlreadyParkedException(
                    "That license plate is already in the parking lot.");
        }

        for (ParkingSlot slot : slots) {
            if (!slot.isOccupied()) {
                Ticket ticket = new Ticket(slot.getSlotNumber(), vehicle);
                slot.parkVehicle(ticket);
                return ticket;
            }
        }
        throw new ParkingFullException("The parking lot is full.");
    }

    public Ticket unparkVehicle(int slotNumber) throws ParkingSpaceNotFoundException {
        ParkingSlot slot = findSlot(slotNumber);
        if (slot == null || !slot.isOccupied()) {
            throw new ParkingSpaceNotFoundException(
                    "No parked vehicle was found in space " + slotNumber + ".");
        }
        Ticket ticket = slot.unparkVehicle();
        history.add(ticket);
        return ticket;
    }

    public Optional<ParkingSlot> searchVehicle(String licensePlate) {
        if (licensePlate == null || licensePlate.isBlank()) {
            return Optional.empty();
        }
        for (ParkingSlot slot : slots) {
            if (slot.isOccupied()
                    && slot.getVehicle().getLicensePlate().equalsIgnoreCase(licensePlate.trim())) {
                return Optional.of(slot);
            }
        }
        return Optional.empty();
    }

    public int addSlot() {
        int slotNumber = nextSlotNumber++;
        slots.add(new ParkingSlot(slotNumber));
        return slotNumber;
    }

    public void removeSlot(int slotNumber) throws ParkingSpaceNotFoundException {
        ParkingSlot slot = findSlot(slotNumber);
        if (slot == null) {
            throw new ParkingSpaceNotFoundException(
                    "Parking space " + slotNumber + " does not exist.");
        }
        if (slot.isOccupied()) {
            throw new IllegalStateException(
                    "Parking space " + slotNumber + " is occupied and cannot be removed.");
        }
        slots.remove(slot);
    }

    public int getOccupiedCount() {
        int count = 0;
        for (ParkingSlot slot : slots) {
            if (slot.isOccupied()) {
                count++;
            }
        }
        return count;
    }

    public int getTotalSlots() {
        return slots.size();
    }

    public List<ParkingSlot> getSlots() {
        return Collections.unmodifiableList(slots);
    }

    public List<Ticket> getHistory() {
        return Collections.unmodifiableList(history);
    }

    private ParkingSlot findSlot(int slotNumber) {
        for (ParkingSlot slot : slots) {
            if (slot.getSlotNumber() == slotNumber) {
                return slot;
            }
        }
        return null;
    }
}
