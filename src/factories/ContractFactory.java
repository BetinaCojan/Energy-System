package factories;

import entities.Consumer;
import entities.Contract;
import entities.Distributor;

import java.util.ArrayList;

public final class ContractFactory {
    private ContractFactory() {

    }

    public static Contract getContractInstance(final Distributor distributor,
                                               final ArrayList<Consumer> consumers,
                                               final int lastMonthNumberOfClients) {
        return new Contract(distributor, consumers, lastMonthNumberOfClients);
    }
}
