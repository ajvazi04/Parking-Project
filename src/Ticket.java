import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Ticket {
    private static int counter = 1000;
    private String ticketID;
    private ParkingSlot slot;
    private LocalDateTime entryTime;

    public Ticket(ParkingSlot slot) {
        this.slot = slot;
        this.entryTime = LocalDateTime.now();
        this.ticketID = "PK-" + LocalDateTime.now().format(DateTimeFormatter.BASIC_ISO_DATE) + "-" + counter++;
    }

    public String getTicketID() { return ticketID; }
    public ParkingSlot getSlot() { return slot; }
    public LocalDateTime getEntryTime() { return entryTime; }

    @Override
    public String toString() {
        return ticketID + " | Slot: " + slot.getSlotNumber() + " | Entry: " +
                entryTime.format(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a"));
    }
}
