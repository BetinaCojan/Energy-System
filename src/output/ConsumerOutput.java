package output;

public final class ConsumerOutput {
    private final int id;
    private final boolean isBankrupt;
    private final long budget;

    public ConsumerOutput(final int id, final boolean isBankrupt, final long budget) {
        this.id = id;
        this.isBankrupt = isBankrupt;
        this.budget = budget;
    }

    public int getId() {
        return this.id;
    }

    public boolean getIsBankrupt() {
        return this.isBankrupt;
    }

    public long getBudget() {
        return this.budget;
    }
}
