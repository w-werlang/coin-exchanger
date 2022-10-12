package wwerlang.coincounter.service;

import lombok.Getter;
import lombok.Setter;
import wwerlang.coincounter.domain.Bill;
import wwerlang.coincounter.domain.Coin;
import wwerlang.coincounter.domain.ExchangeStrategy;
import wwerlang.coincounter.domain.exceptions.NotEnoughCoinsException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class ExchangeService {

    @Getter
    @Setter
    private Map<Coin, Integer> coinMap;

    public ExchangeService(Map<Coin, Integer> coinMap) {
        this.coinMap = coinMap;
    }

    public Map<Coin, Integer> exchangeCoins(int amount) {
        return exchangeCoins(amount, ExchangeStrategy.LEAST_AMOUNT_OF_COINS);
    }

    public Map<Coin, Integer> exchangeCoins(Map<Bill, Integer> billMap) {
        return exchangeCoins(billMap, ExchangeStrategy.LEAST_AMOUNT_OF_COINS);
    }

    public Map<Coin, Integer> exchangeCoins(Map<Bill, Integer> billMap, ExchangeStrategy exchangeStrategy) {
        int exchangeAmount = 0;

        for (Bill bill : billMap.keySet()) {
            exchangeAmount += bill.value * billMap.get(bill);
        }

        return exchangeCoins(exchangeAmount, exchangeStrategy);
    }

    public Map<Coin, Integer> exchangeCoins(int amount, ExchangeStrategy exchangeStrategy) {
        coinMap = sortCoinMap(coinMap, exchangeStrategy);
        Map<Coin, Integer> exchangedCoins = new TreeMap<>();
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

    private Map<Coin, Integer> sortCoinMap(Map<Coin, Integer> initialCoinMap, ExchangeStrategy strategy) {
        TreeMap<Coin, Integer> sortedCoinMap;

        if (strategy == ExchangeStrategy.LEAST_AMOUNT_OF_COINS) {
            sortedCoinMap = new TreeMap<>(Collections.reverseOrder());
        } else {
            sortedCoinMap = new TreeMap<>();
        }

        sortedCoinMap.putAll(initialCoinMap);
        return sortedCoinMap;
    }
}
