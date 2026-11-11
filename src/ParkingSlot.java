public class ParkingSlot {
    private final int slotNumber;
    private Ticket ticket;

    public ParkingSlot(int slotNumber) {
        if (slotNumber < 1) {
            throw new IllegalArgumentException("Space number must be positive.");
        }
        this.slotNumber = slotNumber;
    }

    public boolean isOccupied() {
        return ticket != null;
    }

    void parkVehicle(Ticket ticket) {
        if (isOccupied()) {
            throw new IllegalStateException("Parking space is already occupied.");
        }
        if (ticket == null || ticket.getSlotNumber() != slotNumber) {
            throw new IllegalArgumentException("Ticket does not match this parking space.");
        }
        this.ticket = ticket;
    }

    Ticket unparkVehicle() {
        if (!isOccupied()) {
            throw new IllegalStateException("Parking space is empty.");
        }
        Ticket completedTicket = ticket;
        ticket = null;
        return completedTicket;
    }

    public int getSlotNumber() {
        return slotNumber;
    }

    public Vehicle getVehicle() {
        return ticket == null ? null : ticket.getVehicle();
    }

    public Ticket getTicket() {
        return ticket;
    }
}
