package wwerlang.coincounter.dto;

import lombok.Getter;
import lombok.Setter;
import wwerlang.coincounter.domain.ExchangeRequest;
import wwerlang.coincounter.domain.ExchangeStrategy;
import wwerlang.coincounter.domain.exceptions.AmountInputNotSupportedException;
import wwerlang.coincounter.domain.exceptions.StrategyInputNotSupportedException;

public class ExchangeRequestDTO {

    @Getter
    @Setter
    private String exchangeAmount;

    @Getter
    @Setter
    private String exchangeStrategy;

    public ExchangeRequest getExchangeRequest()
            throws AmountInputNotSupportedException, StrategyInputNotSupportedException {

        if (exchangeStrategy == null || exchangeStrategy.isEmpty()) {
            return new ExchangeRequest(
                    parseAmount(exchangeAmount)
            );
        } else {
            return new ExchangeRequest(
                    parseAmount(exchangeAmount),
                    parseStrategy(exchangeStrategy)
            );
        }
    }

    private int parseAmount(String exchangeAmountString) {
        try {
            return Integer.parseInt(exchangeAmountString);
        } catch (NumberFormatException e) {
            throw new AmountInputNotSupportedException();
        }
    }

    private ExchangeStrategy parseStrategy(String exchangeStrategyString) {
        try {
            return ExchangeStrategy.valueOf(exchangeStrategyString);
        } catch (IllegalArgumentException e) {
            throw new StrategyInputNotSupportedException();
        }
    }
}
