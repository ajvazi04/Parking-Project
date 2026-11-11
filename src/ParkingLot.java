import java.util.ArrayList;
import java.util.List;

class ParkingLot {
    private List<ParkingSlot> slots = new ArrayList<>();
    private List<Vehicle> history = new ArrayList<>();

    public ParkingLot(int initialSlots) {
        for (int i = 1; i <= initialSlots; i++) slots.add(new ParkingSlot(i));
    }

    public ParkingSlot parkVehicle(Vehicle vehicle) throws ParkingFullException {
        for (ParkingSlot slot : slots) {
            if (!slot.isOccupied()) {
                slot.parkVehicle(vehicle);
                return slot;
            }
        }
        throw new ParkingFullException("Parking is full!");
    }

    public Vehicle unparkVehicle(int slotNumber) throws TicketNotFoundException {
        for (ParkingSlot slot : slots) {
            if (slot.getSlotNumber() == slotNumber && slot.isOccupied()) {
                Vehicle v = slot.unparkVehicle();
                history.add(v);
                return v;
            }
        }
        throw new TicketNotFoundException("Ticket not found!");
    }

    public ParkingSlot searchVehicle(String licensePlate) {
        for (ParkingSlot slot : slots) {
            if (slot.isOccupied() && slot.getVehicle().getLicensePlate().equalsIgnoreCase(licensePlate))
                return slot;
        }
        return null;
    }

    public void addSlot() {
        int nextSlot = 1;
        for (ParkingSlot slot : slots) {
            nextSlot = Math.max(nextSlot, slot.getSlotNumber() + 1);
        }
        slots.add(new ParkingSlot(nextSlot));
        System.out.println("Slot " + nextSlot + " added.");
    }

    public void removeSlot(int slotNumber) {
        for (int i = 0; i < slots.size(); i++) {
            ParkingSlot slot = slots.get(i);
            if (slot.getSlotNumber() == slotNumber && !slot.isOccupied()) {
                slots.remove(i);
                System.out.println("Slot " + slotNumber + " removed.");
                return;
            }
        }
        System.out.println("Cannot remove occupied or non-existent slot.");
    }

    public void listSlots() {
        for (ParkingSlot slot : slots)
            System.out.println("Slot " + slot.getSlotNumber() + " | " + (slot.isOccupied() ? slot.getVehicle() : "Empty"));
    }

    public void listParkedVehicles() {
        for (ParkingSlot slot : slots) if (slot.isOccupied()) System.out.println("Slot " + slot.getSlotNumber() + " | " + slot.getVehicle());
    }

    public void viewHistory() {
        if (history.isEmpty()) System.out.println("No completed records.");
        for (Vehicle v : history) System.out.println(v);
    }

    public int getOccupiedCount() {
        int count = 0;
        for (ParkingSlot slot : slots) if (slot.isOccupied()) count++;
        return count;
    }

    public int getTotalSlots() { return slots.size(); }
}