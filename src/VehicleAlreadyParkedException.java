public class VehicleAlreadyParkedException extends Exception {
    private static final long serialVersionUID = 1L;

    public VehicleAlreadyParkedException(String message) {
        super(message);
    }
}
