package output;

public final class ContractOutput {
    private final int consumerId;
    private final long price;
    private final int remainedContractMonths;

    public ContractOutput(final int consumerId, final long price,
                          final int remainedContractMonths) {
        this.consumerId = consumerId;
        this.price = price;
        this.remainedContractMonths = remainedContractMonths;
    }

    public int getConsumerId() {
        return this.consumerId;
    }

    public long getPrice() {
        return this.price;
    }

    public int getRemainedContractMonths() {
        return this.remainedContractMonths;
    }
}
