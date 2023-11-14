package factories;

import entities.Consumer;
import entities.Contract;
import entities.Distributor;
import game.Game;
import game.Gamestate;
import output.ConsumerOutput;
import output.ContractOutput;
import output.DistributorOutput;

import java.util.ArrayList;

public final class GamestateFactory {

    private GamestateFactory() {

    }

    private static ConsumerOutput createConsumerOutput(final Consumer consumer) {
        return new ConsumerOutput(
                consumer.getId(),
                consumer.isBankrupt(),
                consumer.getBudget()
        );
    }

    private static ContractOutput createContractOutput(final Contract contract,
                                                       final Consumer consumer) {
        return new ContractOutput(
                consumer.getId(),
                contract.getFinalContractValue(),
                contract.getRemainedMonths()
        );
    }

    private static DistributorOutput createDistributorOutput(final Game game,
                                                             final Distributor distributor) {
        ArrayList<ContractOutput> contractOutputs = new ArrayList<>();
        for (Contract contract : game.getContracts()) {
            if (contract.getDistributor().equals(distributor)) {
                for (Consumer consumer : contract.getConsumers()) {
                    contractOutputs.add(createContractOutput(contract, consumer));
                }
            }
        }

        return new DistributorOutput(
                distributor.getId(),
                distributor.getBudget(),
                distributor.isBankrupt(),
                contractOutputs
        );
    }

    /**
     * Aduna datele unui gamestate pentru a fi printate in final ca raspuns.
     * @param game = instanta de joc folosita
     */
    public static Gamestate getGamestate(final Game game) {
        Gamestate gamestate = new Gamestate();

        for (Consumer consumer : game.getConsumers().values()) {
            gamestate.addConsumer(createConsumerOutput(consumer));
        }

        for (Distributor distributor : game.getDistributors().values()) {
            gamestate.addDistributor(createDistributorOutput(game, distributor));
        }

        return gamestate;
    }
}
