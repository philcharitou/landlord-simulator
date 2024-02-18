public class Person {
    protected String firstName;
    protected String lastName;
    protected int age; // in months
    protected double monthlyIncome;
    protected boolean healthy;
    protected double bankBalance;
    protected String socialSecurityNumber;

    public Person(String firstName, String lastName, int age, double monthlyIncome) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.monthlyIncome = monthlyIncome;

        bankBalance = monthlyIncome;
        healthy = true;
    }

    protected void sayHello() {
        System.out.println("Hello! My name is " + firstName);
    }

    public void getRaise() {
        monthlyIncome = monthlyIncome * 1.1;
    }

    public void getPaid() {
        bankBalance += monthlyIncome;
    }

    public void age(int months) {
        age += months;
    }

    public String getFirstName() {
        return firstName;
    }

    protected void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    protected void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    protected void setAge(int age) {
        this.age = age;
    }

    public double getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(double monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public double getBankBalance() {
        return bankBalance;
    }

    public void setBankBalance(double bankBalance) {
        this.bankBalance = bankBalance;
    }

    public boolean isHealthy() {
        return healthy;
    }

    public void setHealthy(boolean healthy) {
        this.healthy = healthy;
    }

    protected String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    protected void setSocialSecurityNumber(String socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
    }
}
