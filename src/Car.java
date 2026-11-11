public class Car extends Vehicle {
    public Car(String licensePlate, String ownerName) { super(licensePlate, ownerName); }
    @Override
    public String getType() { return "Car"; }
}