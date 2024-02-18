import java.time.LocalDate;
import java.util.Random;
public class Tenant extends Person {
    private static final Random RAND = new Random();
    private LocalDate  leaseStartDate;
    private LocalDate leaseEndDate;
    private int creditScore;

    private House house;

    public Tenant(String firstName, String lastName, int monthlyIncome) {
        super(firstName, lastName, RAND.nextInt(85), monthlyIncome);

        creditScore = -1;
    }

    public boolean requestRepairs() {
        return RAND.nextInt(101) > 50;
    }

    public void payRent(double rent) {
        bankBalance -= rent;
    }

    public int creditCheck() {
        if (creditScore == -1 && socialSecurityNumber.isEmpty()) {
            creditScore = RAND.nextInt(901);
        }

        return creditScore;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }
}
