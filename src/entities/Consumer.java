package entities;

public final class Consumer {
    private final int id;
    private long budget;
    private int monthlyIncome;

    private boolean bankrupt = false;

    public Consumer(final int id, final int initialBudget, final int monthlyIncome) {
        this.id = id;
        this.budget = initialBudget;
        this.monthlyIncome = monthlyIncome;
    }

    public int getId() {
        return id;
    }

    public long getBudget() {
        return budget;
    }

    public int getMonthlyIncome() {
        return monthlyIncome;
    }

    public boolean isBankrupt() {
        return bankrupt;
    }

    public void payMonthlySalary() {
        this.budget += this.monthlyIncome;
    }

    public void declareBankrupcy() {
        this.bankrupt = true;
    }

    /**
     * Verifica daca acest consumator are suficienti bani pentru a efectua o plata
     * @param billValue = valoarea totala pe care ar trebui sa o plateasca in aceasta luna
     * @return true sau false
     */
    public boolean hasEnoughMoney(final long billValue) {
        return this.budget >= billValue;
    }

    /**
     * Plateste contractul pe care il are in acest moment
     * @param value = costul lunii curente
     * @return true sau false daca s-a putut sau nu plati contractul pe luna curenta
     */
    public void makePayment(final long value) {
        this.budget -= value;
    }

//    @Override
//    public String toString() {
//        return "Consumer{" +
//                "id=" + id +
//                ", budget=" + budget +
//                ", monthlyIncome=" + monthlyIncome +
//                ", isBankrupt=" + bankrupt+
//                '}';
//    }
}
