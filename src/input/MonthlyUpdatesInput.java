package input;

import java.util.ArrayList;

public final class MonthlyUpdatesInput {
    private ArrayList<ConsumerInput> newConsumers;
    private ArrayList<CostsChangesInput> costsChanges;

    public ArrayList<ConsumerInput> getNewConsumers() {
        return newConsumers;
    }

    public void setNewConsumers(final ArrayList<ConsumerInput> newConsumers) {
        this.newConsumers = newConsumers;
    }

    public ArrayList<CostsChangesInput> getCostsChanges() {
        return costsChanges;
    }

    public void setCostsChanges(final ArrayList<CostsChangesInput> costsChanges) {
        this.costsChanges = costsChanges;
    }
}
