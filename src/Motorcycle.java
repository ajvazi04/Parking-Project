public class Motorcycle extends Vehicle {
    public Motorcycle(String licensePlate, String ownerName) {
        super(licensePlate, ownerName);
    }

    @Override
    public String getType() {
        return "Motorcycle";
    }
}
