import java.time.LocalDateTime;

class ParkingSlot {
    private int slotNumber;
    private Vehicle vehicle;
    private Ticket ticket;

    public ParkingSlot(int slotNumber) {
        this.slotNumber = slotNumber;
        this.vehicle = null;
    }

    public boolean isOccupied() { return vehicle != null; }

    public void parkVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
        this.ticket = new Ticket(this);
    }

    public Vehicle unparkVehicle() {
        Vehicle v = vehicle;
        vehicle = null;
        ticket = null;
        return v;
    }

    public String getTicketID() {
        return ticket == null ? null : ticket.getTicketID();
    }

    public int getSlotNumber() { return slotNumber; }
    public Vehicle getVehicle() { return vehicle; }
    public LocalDateTime getEntryTime() { return ticket == null ? null : ticket.getEntryTime(); }
}