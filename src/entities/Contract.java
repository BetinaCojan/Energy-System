package entities;

import java.util.ArrayList;

/**
 * Contractul incheiat intre un client si un distribuitor, in care vom tine minte si cate luni
 * au fost platite, alaturi de durata contractului
 */
public final class Contract {
    private final Distributor distributor;
    private ArrayList<Consumer> consumers;

    private final int contractLength;
    private final long infrastructureCost;
    private final long productionCost;

    private final long finalContractValue;
    private int currentMonth = 0;

    private long computeProfit() {
        return Math.round(Math.floor(this.productionCost * 0.2));
    }

    private long computeFinalContractValue(final int numberOfCustomers) {
        long profit = this.computeProfit();
        if (numberOfCustomers == 0) {
            return this.infrastructureCost + this.productionCost + profit;
        } else {
            return Math.round(Math.floor(this.infrastructureCost / numberOfCustomers))
                    + this.productionCost + profit;
        }
    }

    public Contract(final Distributor distributor, final ArrayList<Consumer> consumers,
                    final int lastMonthNumberOfClients) {
        this.distributor = distributor;
        this.contractLength = distributor.getContractLength();
        this.infrastructureCost = distributor.getInfrastructureCost();
        this.productionCost = distributor.getProductionCost();

        this.consumers = consumers;
        this.finalContractValue = this.computeFinalContractValue(lastMonthNumberOfClients);
    }

//    @Override
//    public String toString() {
//        return "Contract{" +
//                "distributor=" + distributor.getId() +
//                ", consumers=" + consumers +
//                ", contractLength=" + contractLength +
//                ", infrastructureCost=" + infrastructureCost +
//                ", productionCost=" + productionCost +
//                ", finalContractValue=" + finalContractValue +
//                ", currentMonth=" + currentMonth +
//                '}';
//    }

    public long getFinalContractValue() {
        return this.finalContractValue;
    }

    public int getRemainedMonths() {
        return this.contractLength - this.currentMonth;
    }

    public boolean isExpired() {
        return this.contractLength == this.currentMonth;
    }

    public boolean isNew() {
        return this.currentMonth == 0;
    }

    /**
     * Eliminam din acest contract un consumator care a falimentat
     *
     * @param consumer = consumatorul falimentar
     */
    public void removeBankruptConsumer(final Consumer consumer) {
        if (consumer.isBankrupt()) {
            this.consumers.remove(consumer);
        }
    }

    public void incrementMonth() {
        this.currentMonth++;
    }

    public Distributor getDistributor() {
        return this.distributor;
    }

    public ArrayList<Consumer> getConsumers() {
        return consumers;
    }
}
