package wwerlang.coincounter.domain;

import lombok.Getter;
import lombok.Setter;

public class ExchangeRequest {

    @Getter
    @Setter
    private int amount;

    @Getter
    @Setter
    private ExchangeStrategy strategy;

    public ExchangeRequest(int amount, ExchangeStrategy strategy) {
        this.amount = amount;
        this.strategy = strategy;
    }

    public ExchangeRequest(int amount) {
        this.amount = amount;
        this.strategy = ExchangeStrategy.LEAST_AMOUNT_OF_COINS;
    }
}
