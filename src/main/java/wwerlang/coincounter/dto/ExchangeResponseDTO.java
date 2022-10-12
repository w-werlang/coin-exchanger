package wwerlang.coincounter.dto;

import lombok.Getter;
import lombok.Setter;
import wwerlang.coincounter.domain.Coin;

import java.util.Map;

public class ExchangeResponseDTO {

    @Getter
    @Setter
    private Map<Coin, Integer> exchangedCoins;

    public ExchangeResponseDTO(Map<Coin, Integer> exchangedCoins) {
        this.exchangedCoins = exchangedCoins;
    }
}
