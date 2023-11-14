package factories;

import entities.Distributor;
import input.DistributorInput;

public final class DistributorFactory {
    private DistributorFactory() {

    }

    public static Distributor getDistributorInstance(final DistributorInput distributorInput) {
        return new Distributor(
                distributorInput.getId(),
                distributorInput.getContractLength(),
                distributorInput.getInitialBudget(),
                distributorInput.getInitialInfrastructureCost(),
                distributorInput.getInitialProductionCost()
        );
    }
}
