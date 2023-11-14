package factories;

import entities.Consumer;
import input.ConsumerInput;

public final class ConsumerFactory {
    private ConsumerFactory() {

    }

    public static Consumer getConsumerInstance(final ConsumerInput consumerInput) {
        return new Consumer(
                consumerInput.getId(),
                consumerInput.getInitialBudget(),
                consumerInput.getMonthlyIncome()
        );
    }
}
