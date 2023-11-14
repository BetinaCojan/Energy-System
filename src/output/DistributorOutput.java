package output;

import java.util.ArrayList;

public final class DistributorOutput {
    private final int id;
    private final long budget;
    private final boolean isBankrupt;
    private final ArrayList<ContractOutput> contracts;

    public DistributorOutput(final int id, final long budget, final boolean isBankrupt,
                             final ArrayList<ContractOutput> contrast) {
        this.id = id;
        this.budget = budget;
        this.isBankrupt = isBankrupt;
        this.contracts = contrast;
    }

    public int getId() {
        return id;
    }

    public long getBudget() {
        return budget;
    }

    public boolean getIsBankrupt() {
        return isBankrupt;
    }

    public ArrayList<ContractOutput> getContracts() {
        return contracts;
    }
}
