import java.util.ArrayList;
import java.util.List;

public class House implements Building {
    private int buildingHeath;
    private double monthlyRent;
    private double buildingValue;
    private String address;

    private List<Tenant> tenants;

    private Landlord landlord;

    public House(double monthlyRent, String address) {
        buildingHeath = 100;
        this.monthlyRent = monthlyRent;
        this.address = address;
        this.buildingValue = monthlyRent * 12 * 11;
        this.tenants = new ArrayList<>();
    }

    @Override
    public void burnItToTheGround() {
        buildingHeath -= 100;

        double bankBalance = landlord.getBankBalance();
        bankBalance += buildingValue;
        landlord.setBankBalance(bankBalance*1.5);
    }

    @Override
    public void repair(int percentage) {
        buildingHeath += percentage;
        if (buildingHeath > 100) buildingHeath = 100;
    }

    public boolean isOccupied() {
        return !tenants.isEmpty();
    }

    public String listTenants() {
        String list = "";
        int i = 1;

        for (Tenant tenant : tenants) {
            if(i < tenants.size()) {
                list = list.concat(tenant.getFirstName()+" "+tenant.getLastName()+", ");
            } else {
                list = list.concat(tenant.getFirstName()+" "+tenant.getLastName());
            }
            i++;
        }

        return list;
    }

    public void chargeRent() {
        if (!tenants.isEmpty()) {
            Tenant tenant = tenants.getFirst();

            if (tenant.getBankBalance() < 0) {
                evict(tenant);
                System.out.println("It seems "+tenant.firstName+" couldn't pay rent this month.. EVICTED. Good luck finding a new tenant!");
            } else {
                tenant.payRent(monthlyRent);
                double bankBalance = landlord.getBankBalance();
                bankBalance += monthlyRent;
                landlord.setBankBalance(bankBalance);
            }
        }
    }

    public void rentOut(Tenant tenant) {
        tenants.add(tenant);
        tenant.setHouse(this);

        double monthlyIncome = landlord.getMonthlyIncome();
        monthlyIncome += monthlyRent;
        landlord.setMonthlyIncome(monthlyIncome);

    }

    public void evict(Tenant tenant) {
        tenants.remove(tenant);
        tenant.setHouse(null);

        double monthlyIncome = landlord.getMonthlyIncome();
        monthlyIncome -= monthlyRent;
        landlord.setMonthlyIncome(monthlyIncome);
    }

    public int getBuildingHeath() {
        return buildingHeath;
    }

    public void setBuildingHeath(int buildingHeath) {
        this.buildingHeath = buildingHeath;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getMonthlyRent() {
        return monthlyRent;
    }

    public double getBuildingValue() {
        return buildingValue;
    }

    public void setBuildingValue(double buildingValue) {
        this.buildingValue = buildingValue;
    }

    public void setMonthlyRent(double monthlyRent) {
        double monthlyIncome = landlord.getMonthlyIncome();
        monthlyIncome -= monthlyRent;

        this.monthlyRent = monthlyRent;

        monthlyIncome += monthlyRent;
        landlord.setMonthlyIncome(monthlyIncome);
    }

    public List<Tenant> getTenants() {
        return tenants;
    }

    public void setTenants(List<Tenant> tenants) {
        this.tenants = tenants;
    }

    public Landlord getLandlord() {
        return landlord;
    }

    public void setLandlord(Landlord landlord) {
        this.landlord = landlord;
    }
}
