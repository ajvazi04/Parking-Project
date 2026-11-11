public class Truck extends Vehicle {
    public Truck(String licensePlate, String ownerName) { super(licensePlate, ownerName); }
    @Override
    public String getType() { return "Truck"; }
}