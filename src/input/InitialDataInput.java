package input;


import java.util.ArrayList;

public final class InitialDataInput {
    private ArrayList<ConsumerInput> consumers;
    private ArrayList<DistributorInput> distributors;

    public ArrayList<ConsumerInput> getConsumers() {
        return consumers;
    }

    public void setConsumers(final ArrayList<ConsumerInput> consumers) {
        this.consumers = consumers;
    }

    public ArrayList<DistributorInput> getDistributors() {
        return distributors;
    }

    public void setDistributors(final ArrayList<DistributorInput> distributors) {
        this.distributors = distributors;
    }
}
