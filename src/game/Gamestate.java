package game;

import output.ConsumerOutput;
import output.DistributorOutput;

import java.util.ArrayList;

public final class Gamestate {
    private final ArrayList<ConsumerOutput> consumers;
    private final ArrayList<DistributorOutput> distributors;

    public Gamestate() {
        this.consumers = new ArrayList<>();
        this.distributors =  new ArrayList<>();
    }

    public void addConsumer(final ConsumerOutput consumerOutput) {
        this.consumers.add(consumerOutput);
    }

    public void addDistributor(final DistributorOutput distributorOutput) {
        this.distributors.add(distributorOutput);
    }

    public ArrayList<DistributorOutput> getDistributors() {
        return distributors;
    }

    public ArrayList<ConsumerOutput> getConsumers() {
        return consumers;
    }
}
