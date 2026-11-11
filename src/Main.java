import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        new ParkingApplication(new Scanner(System.in), new ParkingLot(8)).run();
    }
}
