public class Debt {
    private double amount;
    private int monthsLeft;

    public Debt(double amount, int monthsLeft) {
        this.amount = amount;
        this.monthsLeft = monthsLeft;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getMonthsLeft() {
        return monthsLeft;
    }

    public void setMonthsLeft(int monthsLeft) {
        this.monthsLeft = monthsLeft;
    }
}
