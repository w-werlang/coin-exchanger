package wwerlang.coincounter.domain.exceptions;

import wwerlang.coincounter.domain.ExchangeStrategy;

public class StrategyInputNotSupportedException extends RuntimeException {

    public StrategyInputNotSupportedException() {
        super();
    }

    @Override
    public String getMessage() {
        StringBuilder message = new StringBuilder("Input strategy not supported. The supported strategies are: ");

        for (ExchangeStrategy strategy : ExchangeStrategy.values()) {
            message.append(strategy).append(", ");
        }

        return message.substring(0, message.length() - 2);
    }
}
