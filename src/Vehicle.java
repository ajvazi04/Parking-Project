// Vehicle.java
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Vehicle {
    private String licensePlate;
    private String ownerName;
    private LocalDateTime entryTime;

    public Vehicle(String licensePlate, String ownerName) {
        this.licensePlate = licensePlate;
        this.ownerName = ownerName;
        this.entryTime = LocalDateTime.now();
    }

    public String getLicensePlate() { return licensePlate; }
    public String getOwnerName() { return ownerName; }
    public LocalDateTime getEntryTime() { return entryTime; }

    public abstract String getType();

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a");
        return String.format("%s | %s | Entry: %s", licensePlate, getType(), entryTime.format(formatter));
    }
}
