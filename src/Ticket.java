import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Ticket {
    private static final DateTimeFormatter ID_TIME =
            DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss-SSS");
    private static final DateTimeFormatter DISPLAY_TIME =
            DateTimeFormatter.ofPattern("MMM d, yyyy h:mm a", Locale.US);
    private static long nextNumber = 1000;

    private final String ticketID;
    private final int slotNumber;
    private final Vehicle vehicle;
    private final LocalDateTime entryTime;

    public Ticket(int slotNumber, Vehicle vehicle) {
        if (slotNumber < 1) {
            throw new IllegalArgumentException("Space number must be positive.");
        }
        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle cannot be null.");
        }
        this.slotNumber = slotNumber;
        this.vehicle = vehicle;
        this.entryTime = LocalDateTime.now();
        this.ticketID = String.format(Locale.ROOT, "PK-%s-%04d",
                entryTime.format(ID_TIME), nextNumber++);
    }

    public String getTicketID() {
        return ticketID;
    }

    public int getSlotNumber() {
        return slotNumber;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    @Override
    public String toString() {
        return String.format(Locale.ROOT, "%s | Space %d | %s | Entry: %s",
                ticketID, slotNumber, vehicle, entryTime.format(DISPLAY_TIME));
    }
}
