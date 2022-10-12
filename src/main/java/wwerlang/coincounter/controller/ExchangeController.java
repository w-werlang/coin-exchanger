package wwerlang.coincounter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import wwerlang.coincounter.domain.Coin;
import wwerlang.coincounter.domain.ExchangeRequest;
import wwerlang.coincounter.domain.exceptions.AmountInputNotSupportedException;
import wwerlang.coincounter.domain.exceptions.NotEnoughCoinsException;
import wwerlang.coincounter.domain.exceptions.StrategyInputNotSupportedException;
import wwerlang.coincounter.dto.ExchangeRequestDTO;
import wwerlang.coincounter.dto.ExchangeResponseDTO;
import wwerlang.coincounter.service.ExchangeService;

import java.util.Map;

@RestController()
@CrossOrigin("*")
public class ExchangeController {

    @Autowired
    private ExchangeService exchangeService;

    @PostMapping("/exchangeAmount")
    public ResponseEntity<?> exchangeFromAmount(@RequestBody ExchangeRequestDTO exchangeRequestDTO) {
        try {
            ExchangeRequest exchangeRequest = exchangeRequestDTO.getExchangeRequest();
            Map<Coin, Integer> exchangedCoins = exchangeService.exchangeCoins(exchangeRequest);

            return ResponseEntity.ok(new ExchangeResponseDTO(exchangedCoins));
        } catch (NotEnoughCoinsException | AmountInputNotSupportedException | StrategyInputNotSupportedException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
