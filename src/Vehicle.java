public abstract class Vehicle {
    private final String licensePlate;
    private final String ownerName;

    protected Vehicle(String licensePlate, String ownerName) {
        if (licensePlate == null || licensePlate.isBlank()) {
            throw new IllegalArgumentException("License plate cannot be empty.");
        }
        if (ownerName == null || ownerName.isBlank()) {
            throw new IllegalArgumentException("Owner name cannot be empty.");
        }
        this.licensePlate = licensePlate.trim();
        this.ownerName = ownerName.trim();
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public abstract String getType();

    @Override
    public String toString() {
        return String.format("%s | %s | Owner: %s", getType(), licensePlate, ownerName);
    }
}
