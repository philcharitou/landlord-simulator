import java.util.ArrayList;
import java.util.Random;
import java.util.List;
import java.util.Stack;

public class Landlord extends Person {
    private static final Random RAND = new Random();

    private List<House> properties;

    private Stack<Debt> owedMoney;

    public Landlord(String firstName, String lastName, double monthlyIncome) {
        super(firstName, lastName, RAND.nextInt(20 * 12, 30 * 12), monthlyIncome);

        properties = new ArrayList<>();
        owedMoney = new Stack<Debt>();
    }

    public void listProperties() {
        String format = "%-30s%s%n";

        if (properties.isEmpty()) {
            System.out.println("You own no properties!");
            return;
        }

        int i = 0;
        for (House house : properties) {
            String address = i++ +":\t"+house.getAddress();
            String value = "Tenants: "+house.listTenants();
            System.out.printf(format, address, value);
        }
    }

    public void listPropertiesWithRent() {
        String format = "%-30s%s%n";

        if (properties.isEmpty()) {
            System.out.println("You own no properties!");
            return;
        }

        int i = 0;
        for (House house : properties) {
            String address = i++ +":\t"+house.getAddress();
            String value = "Rent: $ "+house.getMonthlyRent();
            System.out.printf(format, address, value);
        }
    }

    public void listPropertiesWithoutTenants() {
        int i = 0;
        for (House house : properties) {
            if (!house.isOccupied()) {
                System.out.println(i++ +":\t"+house.getAddress());
            }
        }
    }

    public void listTenants() {
        for (House house : properties) {
            List<Tenant> tenants = house.getTenants();
            for (Tenant tenant : tenants) {
                System.out.println(tenant.getFirstName());
            }
        }
    }
    
    public double getMonthlyIncome() {
        double result = 0;
        for (House house : properties) {
            if (house.isOccupied()) {
                result += house.getMonthlyRent();
            }
        }
        return result;
    }

    public void commitInsuranceFraud(House house) {
        house.burnItToTheGround();
        properties.remove(house);
    }

    public boolean buyProperty(House house, boolean override) {
        if(!override) {
            if (bankBalance >= house.getBuildingValue()) {
                properties.add(house);
                house.setLandlord(this);
                bankBalance -= house.getBuildingValue();
                return true;
            }
        } else {
            properties.add(house);
            house.setLandlord(this);
            return true;
        }

        return false;
    }

    public void sellProperty(House house) {
        properties.remove(house);
        house.setLandlord(null);

        bankBalance += house.getBuildingValue();
    }

    public void loseProperty(House house) {
        properties.remove(house);
        house.setLandlord(null);
    }

    public void chargeRent() {
        for (House house : properties) {
            house.chargeRent();
        }
    }

    public void healthScare(Debt hospitalBill) {
        owedMoney.add(hospitalBill);
        setHealthy(false);
    }

    public void payDebt(Debt debt) {
        owedMoney.remove(debt);
    }

    public boolean hasExpiredDebt() {
        for (Debt debt : getOwedMoney()) {
            if (debt.getMonthsLeft() <= 0) {
                return true;
            }
        }
        return false;
    }

    public Stack<Debt> getOwedMoney() {
        return owedMoney;
    }

    public void setOwedMoney(Stack<Debt> owedMoney) {
        this.owedMoney = owedMoney;
    }

    public List<House> getPropertiesWithoutTenants() {
        List<House> withoutTenants = new ArrayList<House>();

        for (House house : properties) {
            if (!house.isOccupied()) {
                withoutTenants.add(house);
            }
        }

        return withoutTenants;
    }

    public List<House> getProperties() {
        return properties;
    }

    public void setProperties(List<House> properties) {
        this.properties = properties;
    }
}
