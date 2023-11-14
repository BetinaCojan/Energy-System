package entities;

public final class Distributor {
    private final int id;
    private final int contractLength;
    private long budget;
    private int infrastructureCost;
    private int productionCost;

    private boolean bankrupt = false;

    public int getId() {
        return id;
    }

    public int getContractLength() {
        return contractLength;
    }

    public long getBudget() {
        return budget;
    }

    public int getInfrastructureCost() {
        return infrastructureCost;
    }

    public void setInfrastructureCost(final int infrastructureCost) {
        this.infrastructureCost = infrastructureCost;
    }

    public int getProductionCost() {
        return productionCost;
    }

    public void setProductionCost(final int productionCost) {
        this.productionCost = productionCost;
    }

    public boolean isBankrupt() {
        return bankrupt;
    }

    public void declareBankrupcy() {
        this.bankrupt = true;
    }


    public Distributor(final int id, final int contractLength, final int initialBudget,
                       final int initialInfrastructureCost, final int initialProductionCost) {
        this.id = id;
        this.contractLength = contractLength;
        this.budget = initialBudget;
        this.infrastructureCost = initialInfrastructureCost;
        this.productionCost = initialProductionCost;
    }

    public void receiveNewIncome(final long income) {
        this.budget += income;
    }

    private long computeProfit() {
        return Math.round(Math.floor(0.2 * this.productionCost));
    }

    public long computeUtilityValue(final int numberOfConsumers) {
        return this.infrastructureCost + this.productionCost * numberOfConsumers;
    }

    /**
     * Calculeaza pretul lunar pe care distribuitorul l-ar oferi, in functie de numarul
     * de consumatori pe care ii are
     * @param numberOfConsumers = numarul de utilizatori ai distribuitorului
     * @return = valoarea finala a contractului
     */
    public long computeFinalBillValue(final int numberOfConsumers) {
        long profit = this.computeProfit();
        if (numberOfConsumers == 0) {
            return this.infrastructureCost + this.productionCost + profit;
        }
        return Math.round(Math.floor(this.infrastructureCost / numberOfConsumers))
                + this.productionCost + profit;
    }

    public void payUtilities(final long utilitiesValue) {
        this.budget -= utilitiesValue;
    }

//    @Override
//    public String toString() {
//        return "Distributor{" +
//                "id=" + id +
//                ", contractLength=" + contractLength +
//                ", budget=" + budget +
//                ", infrastructureCost=" + infrastructureCost +
//                ", productionCost=" + productionCost +
//                ", isBankrupt=" + bankrupt +
//                '}';
//    }
}
