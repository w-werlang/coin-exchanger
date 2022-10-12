package wwerlang.coincounter.dto;

import lombok.Getter;
import lombok.Setter;
import wwerlang.coincounter.domain.ExchangeStrategy;

public class ExchangeRequestDTO {

    @Getter
    @Setter
    private int exchangeAmount;

    @Getter
    @Setter
    private ExchangeStrategy exchangeStrategy;

}
