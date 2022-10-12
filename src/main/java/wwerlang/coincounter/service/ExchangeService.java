package wwerlang.coincounter.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import wwerlang.coincounter.domain.Bill;
import wwerlang.coincounter.domain.Coin;
import wwerlang.coincounter.domain.ExchangeRequest;
import wwerlang.coincounter.domain.ExchangeStrategy;
import wwerlang.coincounter.domain.exceptions.NotEnoughCoinsException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import static java.util.Map.entry;

@Service
public class ExchangeService {

    @Getter
    @Setter
    private Map<Coin, Integer> coinMap;

    @Value("${COIN_1_COUNT}")
    private int INITIAL_COIN_1_COUNT = 100;
    @Value("${COIN_5_COUNT}")
    private int INITIAL_COIN_5_COUNT = 100;
    @Value("${COIN_10_COUNT}")
    private int INITIAL_COIN_10_COUNT = 100;
    @Value("${COIN_25_COUNT}")
    private int INITIAL_COIN_25_COUNT = 100;

    public ExchangeService() {
        coinMap = new TreeMap<>(Map.ofEntries(
                entry(Coin.COIN_1, INITIAL_COIN_1_COUNT),
                entry(Coin.COIN_5, INITIAL_COIN_5_COUNT),
                entry(Coin.COIN_10, INITIAL_COIN_10_COUNT),
                entry(Coin.COIN_25, INITIAL_COIN_25_COUNT)
        ));
    }

    public ExchangeService(Map<Coin, Integer> coinMap) {
        this.coinMap = coinMap;
    }

    public Map<Coin, Integer> exchangeCoins(ExchangeRequest exchangeRequest) {
        ExchangeStrategy exchangeStrategy = exchangeRequest.getStrategy();
        double amount = exchangeRequest.getAmount();

        coinMap = sortCoinMap(coinMap, exchangeStrategy);
        Map<Coin, Integer> exchangedCoins = new TreeMap<>(Map.ofEntries(
                entry(Coin.COIN_1, 0),
                entry(Coin.COIN_5, 0),
                entry(Coin.COIN_10, 0),
                entry(Coin.COIN_25, 0)
        ));
        BigDecimal pendingAmount = BigDecimal.valueOf(amount);

        for (Coin coin : coinMap.keySet()) {
            int coinsAvailable = coinMap.get(coin);
            BigDecimal amountAvailable = BigDecimal.valueOf(coinsAvailable * coin.value);
            boolean allCoinsAreNeeded = pendingAmount.compareTo(amountAvailable) >= 0;

            if (allCoinsAreNeeded) {
                exchangedCoins.put(coin, coinsAvailable);
                pendingAmount = pendingAmount.subtract(amountAvailable);
                coinMap.put(coin, 0);
            } else {
                int coinsUsed = pendingAmount.divide(BigDecimal.valueOf(coin.value), RoundingMode.DOWN).intValue();
                exchangedCoins.put(coin, coinsUsed);
                pendingAmount = pendingAmount.remainder(BigDecimal.valueOf(coin.value));
                coinMap.put(coin, coinsAvailable - coinsUsed);
            }

            if (pendingAmount.compareTo(BigDecimal.ZERO) == 0) {
                break;
            }
        }

        if (pendingAmount.compareTo(BigDecimal.ZERO) > 0) {
            throw new NotEnoughCoinsException(pendingAmount.doubleValue());
        }

        return exchangedCoins;
    }

    public Map<Coin, Integer> exchangeCoins(Map<Bill, Integer> billMap) {
        return exchangeCoins(billMap, ExchangeStrategy.LEAST_AMOUNT_OF_COINS);
    }

    public Map<Coin, Integer> exchangeCoins(Map<Bill, Integer> billMap, ExchangeStrategy exchangeStrategy) {
        int exchangeAmount = 0;

        for (Bill bill : billMap.keySet()) {
            exchangeAmount += bill.value * billMap.get(bill);
        }

        ExchangeRequest exchangeRequest = new ExchangeRequest(exchangeAmount, exchangeStrategy);
        return exchangeCoins(exchangeRequest);
    }

    private Map<Coin, Integer> sortCoinMap(Map<Coin, Integer> initialCoinMap, ExchangeStrategy strategy) {
        TreeMap<Coin, Integer> sortedCoinMap;

        switch (strategy) {
            case LEAST_AMOUNT_OF_COINS:
                sortedCoinMap = new TreeMap<>(Collections.reverseOrder());
                break;
            case MOST_AMOUNT_OF_COINS:
                sortedCoinMap = new TreeMap<>();
                break;
            default:
                sortedCoinMap = new TreeMap<>(Collections.reverseOrder());
                break;
        }

        sortedCoinMap.putAll(initialCoinMap);
        return sortedCoinMap;
    }
}
