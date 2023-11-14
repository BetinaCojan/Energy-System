package input;

public final class ConsumerInput {
    private int id;
    private int initialBudget;
    private int monthlyIncome;

    public int getId() {
        return id;
    }

    public int getInitialBudget() {
        return initialBudget;
    }

    public int getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public void setInitialBudget(final int initialBudget) {
        this.initialBudget = initialBudget;
    }

    public void setMonthlyIncome(final int monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }
}
