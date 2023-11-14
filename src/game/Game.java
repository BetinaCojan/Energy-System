package game;

import entities.Consumer;
import entities.Contract;
import entities.Distributor;
import factories.ConsumerFactory;
import factories.ContractFactory;
import factories.DistributorFactory;
import factories.GamestateFactory;
import input.*;

import java.util.*;

public final class Game {
    private int numberOfTurns;
    private HashMap<Integer, Consumer> consumers;
    private HashMap<Integer, Distributor> distributors;
    private ArrayList<Contract> contracts;
    private HashMap<Consumer, Contract> overdueContracts;
    private HashMap<Distributor, Integer> numberOfClientsPreviousMonth;

    public Game(final int numberOfTurns, final InitialDataInput initialDataInput) {
        this.numberOfTurns = numberOfTurns;

        this.consumers = new HashMap<>();
        for (ConsumerInput consumerInput : initialDataInput.getConsumers()) {
            this.consumers.put(
                    consumerInput.getId(),
                    ConsumerFactory.getConsumerInstance(consumerInput)
            );
        }

        this.distributors = new HashMap<>();
        for (DistributorInput distributorInput : initialDataInput.getDistributors()) {
            this.distributors.put(
                    distributorInput.getId(),
                    DistributorFactory.getDistributorInstance(distributorInput)
            );
        }

        this.contracts = new ArrayList<>();
        this.overdueContracts = new HashMap<>();
    }

    public HashMap<Integer, Consumer> getConsumers() {
        return consumers;
    }

    public HashMap<Integer, Distributor> getDistributors() {
        return distributors;
    }

    public ArrayList<Contract> getContracts() {
        return this.contracts;
    }

    /**
     * @param costsChanges Eventuale schimbari ale costurilor unui distribuitor,
     *                     specificate in input
     */
    private void updateDistributorsCosts(final ArrayList<CostsChangesInput> costsChanges) {
        for (CostsChangesInput costsChangesInput : costsChanges) {
            Distributor distributor = this.distributors.get(costsChangesInput.getId());
            distributor.setInfrastructureCost(costsChangesInput.getInfrastructureCost());
            distributor.setProductionCost(costsChangesInput.getProductionCost());
        }
    }

    /**
     * @param consumerInputs = Informatii despre noi utilizatori ce vor fi adaugati
     *                       in joc in aceasta luna
     */
    private void addNewConsumers(final ArrayList<ConsumerInput> consumerInputs) {
        for (ConsumerInput consumerInput : consumerInputs) {
            this.consumers.put(
                    consumerInput.getId(),
                    ConsumerFactory.getConsumerInstance(consumerInput)
            );
        }
    }

    /**
     * Vefificam cati clienti are fiecare distribuitor in luna curenta,
     * pentru a putea recalcula valorile contractelor in luna urmatoare.
     */
    private void updateNumberOfClients() {
        this.numberOfClientsPreviousMonth = new HashMap<>();
        for (Distributor distributor : this.distributors.values()) {
            int clients = 0;
            for (Contract contract : this.contracts) {
                if (contract.getDistributor().equals(distributor)) {
                    clients += contract.getConsumers().size();
                }
            }
            numberOfClientsPreviousMonth.put(distributor, clients);
        }
    }

    /**
     * Consumatorii isi vor primi salariile la inceput de luna
     */
    private void payConsumersSalaries() {
        for (Consumer consumer : this.consumers.values()) {
            if (! consumer.isBankrupt()) {
                consumer.payMonthlySalary();
            }
        }
    }

    /**
     * Verificam daca un consumator are sau nu un contract activ
     */
    private boolean hasActiveContract(final Consumer consumer) {
        for (Contract contract : this.contracts) {
            if (contract.getConsumers().contains(consumer)
                    && !contract.isExpired()
                    && !contract.getDistributor().isBankrupt()) {
                return true;
            }
        }

        return false;
    }

    /**
     * Verificam daca un distribuitor are sau nu un ontract deschis.
     * Vom folosi aceasta informatie in cazul in care dorim ca un fistribuitor sa aiba deschis un
     * contract fara clienti, pentru a calcula mai usor costul utilitatilor sale.
     */
    private boolean hasActiveContract(final Distributor distributor) {
        for (Contract contract : this.contracts) {
            if (contract.getDistributor().equals(distributor)
                    && !contract.getDistributor().isBankrupt()) {
                return true;
            }
        }

        return false;
    }

    /**
     * @return cel mai bun distribuitor in functie de valoarea minima lunara de plata
     */
    private Distributor getBestDistributor() {
        Distributor bestDistributor = null;
        for (Distributor distributor : this.distributors.values()) {
            if (!distributor.isBankrupt()) {
                if (bestDistributor == null) {
                    bestDistributor = distributor;
                } else {
                    long bestValue = bestDistributor.computeFinalBillValue(
                            this.numberOfClientsPreviousMonth.get(bestDistributor)
                    );
                    long newValue = distributor.computeFinalBillValue(
                            this.numberOfClientsPreviousMonth.get(distributor)
                    );
                    if (newValue < bestValue) {
                        bestDistributor = distributor;
                    }
                }
            }
        }

        return bestDistributor;
    }

    /**
     * Consumatorii care nu au un contract valid vor semna un nou contract
     * cu cel mai bun distribuitor
     * @param bestDistributor = distribuitorul cu cel mai mic cost lunar
     */
    private void signNewContracts(final Distributor bestDistributor) {
        ArrayList<Consumer> consumersWithoutContract = new ArrayList<>();
        for (Consumer consumer : this.consumers.values()) {
            if (!consumer.isBankrupt() && !this.hasActiveContract(consumer)) {
                consumersWithoutContract.add(consumer);
            }
        }
        if (!consumersWithoutContract.isEmpty()) {
            this.contracts.add(ContractFactory.getContractInstance(
                    bestDistributor,
                    consumersWithoutContract,
                    this.numberOfClientsPreviousMonth.get(bestDistributor)
            ));
        }
    }

    /**
     * Efectuarea unei plati de la un consumer catre un distribuitor cu valoarea value
     */
    private void executePayment(final Consumer consumer, final Distributor distributor,
                                final long value) {
        if (consumer.hasEnoughMoney(value)) {
            consumer.makePayment(value);
            distributor.receiveNewIncome(value);
        }
    }

    /**
     * Consumatorii isi vor plati contractele.
     * In cazul in care acesta are o plata amanata, atunci aceasta se va aduna la costul total.
     * Daca clientul are suficienti bani, va plati factura curenta si eventual si pe cea amanata,
     * daca exxista o amanare.
     * Daca nu are suficienti bani, consumatorul este trecut in lista contractelor amanata. Daca
     * insa consumatorul era deja pe aceasta lista, atunci el va declara falimentul si va fi scos
     * din contracte si din joc.
     */
    private void payConsumersContracts() {
        for (Consumer consumer : this.consumers.values()) {
            for (Contract currentContract : this.contracts) {
                if (!currentContract.getConsumers().contains(consumer)) {
                    continue;
                }

                Contract overdueContract = this.overdueContracts.getOrDefault(consumer, null);
                long currentBillValue = currentContract.getFinalContractValue();
                long overdueBillValue = 0;
                if (overdueContract != null) {
                    overdueBillValue = Math.round(Math.floor(
                            overdueContract.getFinalContractValue() * 1.2
                    ));
                }
                long totalBillValue = currentBillValue + overdueBillValue;

                if (consumer.hasEnoughMoney(totalBillValue)) {
                    this.executePayment(consumer, currentContract.getDistributor(),
                            currentBillValue);
                    if (overdueContract != null) {
                        this.executePayment(consumer, overdueContract.getDistributor(),
                                overdueBillValue);
                        this.overdueContracts.remove(consumer);
                    }
                } else {
                    if (overdueContract == null) {
                        this.overdueContracts.put(consumer, currentContract);
                    } else {
                        consumer.declareBankrupcy();
                        currentContract.removeBankruptConsumer(consumer);
                        overdueContract.removeBankruptConsumer(consumer);
                        this.overdueContracts.remove(consumer);
                    }
                }
            }
        }

        for (Contract contract : this.contracts) {
            contract.incrementMonth();
        }
    }

    /**
     * Distribuitorii isi vor plati utilitatiile in functie de numarul
     * de consumatori pe care ii au in aceasta luna.
     */
    private void payDistributorsUtilities() {
        for (Distributor distributor : this.distributors.values()) {
            if (distributor.isBankrupt()) {
                continue;
            }
            int numberOfClients = 0;
            for (Contract contract : this.contracts) {
                if (contract.getDistributor().equals(distributor)) {
                    numberOfClients += contract.getConsumers().size();
                }
            }
            distributor.payUtilities(distributor.computeUtilityValue(numberOfClients));
        }

    }

    /**
     * La final de luna, distribuitorii ramasi fara bani isi vor decalra falimentul.
     * Consumatorii care aveau contracte amanate catre acestia vor avea datoria stearsa.
     */
    private void declareBankruptcyForDistributors() {
        for (Distributor distributor : this.distributors.values()) {
            if (distributor.getBudget() < 0) {
                distributor.declareBankrupcy();
            }
        }

        for (Consumer consumer : this.overdueContracts.keySet()) {
            if (this.overdueContracts.get(consumer).getDistributor().isBankrupt()) {
                this.overdueContracts.remove(consumer);
            }
        }
    }

    private void removeBankruptConsumersFromContracts() {
        for (Consumer consumer : this.consumers.values()) {
            if (consumer.isBankrupt()) {
                for (Contract contract : this.contracts) {
                    contract.removeBankruptConsumer(consumer);
                }
            }
        }
    }

    /**
     * Contractele expirate sunt eliminate din lista de contracte.
     * Un contract este expirat daca distribuitorul sau consumatorul
     * au dat faliment, sau daca toate luniile au fost platite.
     */
    private void eraseExpiredContracts() {
        Iterator it = this.contracts.iterator();
        while (it.hasNext()) {
            Contract contract = (Contract) it.next();
            if (contract.isExpired() || contract.getDistributor().isBankrupt()) {
                it.remove();
            }
        }
    }

    private int getNumberOfActiveDistributors() {
        int numberOfDistributors = 0;
        for (Distributor distributor : this.distributors.values()) {
            if (!distributor.isBankrupt()) {
                numberOfDistributors++;
            }
        }

        return numberOfDistributors;
    }

    public void runGame(final ArrayList<MonthlyUpdatesInput> monthlyUpdates) {
        this.updateNumberOfClients();

        for (int month = 0; month <= this.numberOfTurns; month++) {
            if (month > 0) {
                this.updateDistributorsCosts(monthlyUpdates.get(month).getCostsChanges());
                this.addNewConsumers(monthlyUpdates.get(month).getNewConsumers());
            }
            this.removeBankruptConsumersFromContracts();
            this.eraseExpiredContracts();

            this.payConsumersSalaries();
            this.signNewContracts(this.getBestDistributor());

            this.payDistributorsUtilities();
            this.payConsumersContracts();
            this.declareBankruptcyForDistributors();
            this.updateNumberOfClients();

            if (this.getNumberOfActiveDistributors() == 0) {
                return;
            }
        }
    }

    public Gamestate getGamestate() {
        return GamestateFactory.getGamestate(this);
    }

    @Override
    public String toString() {
        String result = "";
        result += "Consumers = " + this.consumers.values().size() + "\n";
        for (Consumer consumer : this.consumers.values()) {
            result += consumer.toString() + "\n";
        }

        result += "Distributors = " + this.distributors.values().size() + "\n";
        for (Distributor distributor : this.distributors.values()) {
            result += distributor.toString() + "\n";
        }

        result += "Contracts = " + this.contracts.size() + "\n";
        for (Contract contract : this.contracts) {
            result += contract.toString() + "\n";
        }

        result += "\n";

        return result;
    }
}
