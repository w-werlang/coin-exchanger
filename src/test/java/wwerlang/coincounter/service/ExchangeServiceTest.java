package wwerlang.coincounter.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import wwerlang.coincounter.domain.Bill;
import wwerlang.coincounter.domain.Coin;
import wwerlang.coincounter.domain.ExchangeRequest;
import wwerlang.coincounter.domain.ExchangeStrategy;
import wwerlang.coincounter.domain.exceptions.NotEnoughCoinsException;

import java.util.*;

import static java.util.Map.entry;
import static org.junit.jupiter.api.Assertions.*;

class ExchangeServiceTest {

    private Map<Coin, Integer> initialCoinMap100;

    private Map<Coin, Integer> initialCoinMap500;

    @BeforeEach
    void initializeCoinMap() {
        initialCoinMap100 = new HashMap<>(Map.ofEntries(
                entry(Coin.COIN_5, 100),
                entry(Coin.COIN_1, 100),
                entry(Coin.COIN_25, 100),
                entry(Coin.COIN_10, 100)
        ));

        initialCoinMap500 = new HashMap<>(Map.ofEntries(
                entry(Coin.COIN_5, 500),
                entry(Coin.COIN_1, 500),
                entry(Coin.COIN_25, 500),
                entry(Coin.COIN_10, 500)
        ));
    }

    @Test
    void exchangeLeastAmountOfCoins1() {
        int exchangeAmount = 41;

        Map<Coin, Integer> expectedCoinsExchanged = new TreeMap<>(Map.ofEntries(
                entry(Coin.COIN_1, 100),
                entry(Coin.COIN_5, 100),
                entry(Coin.COIN_10, 100),
                entry(Coin.COIN_25, 100)
        ));

        Map<Coin, Integer> expectedCoinsAvailable = new TreeMap<>(Map.ofEntries(
                entry(Coin.COIN_1, 0),
                entry(Coin.COIN_5, 0),
                entry(Coin.COIN_10, 0),
                entry(Coin.COIN_25, 0)
        ));

        ExchangeService exchangeService = new ExchangeService(initialCoinMap100);
        Map<Coin, Integer> actualCoinsExchanged = exchangeService.exchangeCoins(
                new ExchangeRequest(exchangeAmount));

        assertEquals(expectedCoinsExchanged, actualCoinsExchanged);
        assertEquals(expectedCoinsAvailable, exchangeService.getCoinMap());
    }

    @Test
    void exchangeLeastAmountOfCoins2() {
        int exchangeAmount = 37;

        Map<Coin, Integer> expectedCoinsExchanged = new TreeMap<>(Map.ofEntries(
                entry(Coin.COIN_1, 0),
                entry(Coin.COIN_5, 40),
                entry(Coin.COIN_10, 100),
                entry(Coin.COIN_25, 100)
        ));

        Map<Coin, Integer> expectedCoinsAvailable = new TreeMap<>(Map.ofEntries(
                entry(Coin.COIN_1, 100),
                entry(Coin.COIN_5, 60),
                entry(Coin.COIN_10, 0),
                entry(Coin.COIN_25, 0)
        ));

        ExchangeService exchangeService = new ExchangeService(initialCoinMap100);
        Map<Coin, Integer> actualCoinMap = exchangeService.exchangeCoins(
                new ExchangeRequest(exchangeAmount));

        assertEquals(expectedCoinsExchanged, actualCoinMap);
        assertEquals(expectedCoinsAvailable, exchangeService.getCoinMap());
    }

    @Test
    void exchangeLeastAmountOfCoins3() {
        int exchangeAmount = 35;

        Map<Coin, Integer> expectedCoinsExchanged = new TreeMap<>(Map.ofEntries(
                entry(Coin.COIN_1, 0),
                entry(Coin.COIN_5, 0),
                entry(Coin.COIN_10, 100),
                entry(Coin.COIN_25, 100)
        ));

        Map<Coin, Integer> expectedCoinsAvailable = new TreeMap<>(Map.ofEntries(
                entry(Coin.COIN_1, 100),
                entry(Coin.COIN_5, 100),
                entry(Coin.COIN_10, 0),
                entry(Coin.COIN_25, 0)
        ));

        ExchangeService exchangeService = new ExchangeService(initialCoinMap100);
        Map<Coin, Integer> actualCoinMap = exchangeService.exchangeCoins(
                new ExchangeRequest(exchangeAmount));

        assertEquals(expectedCoinsExchanged, actualCoinMap);
        assertEquals(expectedCoinsAvailable, exchangeService.getCoinMap());
    }

    @Test
    void exchangeLeastAmountOfCoins4() {
        int exchangeAmount = 24;

        Map<Coin, Integer> expectedCoinsExchanged = new TreeMap<>(Map.ofEntries(
                entry(Coin.COIN_1, 0),
                entry(Coin.COIN_5, 0),
                entry(Coin.COIN_10, 0),
                entry(Coin.COIN_25, 96)
        ));

        Map<Coin, Integer> expectedCoinsAvailable = new TreeMap<>(Map.ofEntries(
                entry(Coin.COIN_1, 100),
                entry(Coin.COIN_5, 100),
                entry(Coin.COIN_10, 100),
                entry(Coin.COIN_25, 4)
        ));

        ExchangeService exchangeService = new ExchangeService(initialCoinMap100);
        Map<Coin, Integer> actualCoinMap = exchangeService.exchangeCoins(
                new ExchangeRequest(exchangeAmount, ExchangeStrategy.LEAST_AMOUNT_OF_COINS));

        assertEquals(expectedCoinsExchanged, actualCoinMap);
        assertEquals(expectedCoinsAvailable, exchangeService.getCoinMap());
    }

    @Test
    void exchangeLeastAmountOfCoins5() {
        int exchangeAmount = 1;

        Map<Coin, Integer> expectedCoinsExchanged = new TreeMap<>(Map.ofEntries(
                entry(Coin.COIN_1, 0),
                entry(Coin.COIN_5, 0),
                entry(Coin.COIN_10, 0),
                entry(Coin.COIN_25, 4)
        ));

        Map<Coin, Integer> expectedCoinsAvailable = new TreeMap<>(Map.ofEntries(
                entry(Coin.COIN_1, 100),
                entry(Coin.COIN_5, 100),
                entry(Coin.COIN_10, 100),
                entry(Coin.COIN_25, 96)
        ));

        ExchangeService exchangeService = new ExchangeService(initialCoinMap100);
        Map<Coin, Integer> actualCoinMap = exchangeService.exchangeCoins(
                new ExchangeRequest(exchangeAmount, ExchangeStrategy.LEAST_AMOUNT_OF_COINS));

        assertEquals(expectedCoinsExchanged, actualCoinMap);
        assertEquals(expectedCoinsAvailable, exchangeService.getCoinMap());
    }

    @Test
    void exchangeLeastAmountOfCoinsFromBills1() {
        Map<Bill, Integer> billMap = new HashMap<>(Map.ofEntries(
                entry(Bill.BILL_1, 11),
                entry(Bill.BILL_2, 5),
                entry(Bill.BILL_20, 1)
        ));

        Map<Coin, Integer> expectedCoinsExchanged = new TreeMap<>(Map.ofEntries(
                entry(Coin.COIN_1, 100),
                entry(Coin.COIN_5, 100),
                entry(Coin.COIN_10, 100),
                entry(Coin.COIN_25, 100)
        ));

        Map<Coin, Integer> expectedCoinsAvailable = new TreeMap<>(Map.ofEntries(
                entry(Coin.COIN_1, 0),
                entry(Coin.COIN_5, 0),
                entry(Coin.COIN_10, 0),
                entry(Coin.COIN_25, 0)
        ));

        ExchangeService exchangeService = new ExchangeService(initialCoinMap100);
        Map<Coin, Integer> actualCoinMap = exchangeService.exchangeCoins(billMap);

        assertEquals(expectedCoinsExchanged, actualCoinMap);
        assertEquals(expectedCoinsAvailable, exchangeService.getCoinMap());
    }

    @Test
    void exchangeLeastAmountOfCoinsFromBills2() {
        Map<Bill, Integer> billMap = new HashMap<>(Map.ofEntries(
                entry(Bill.BILL_1, 1),
                entry(Bill.BILL_2, 3),
                entry(Bill.BILL_5, 4),
                entry(Bill.BILL_10, 1)
        ));

        Map<Coin, Integer> expectedCoinsExchanged = new TreeMap<>(Map.ofEntries(
                entry(Coin.COIN_1, 0),
                entry(Coin.COIN_5, 40),
                entry(Coin.COIN_10, 100),
                entry(Coin.COIN_25, 100)
        ));

        Map<Coin, Integer> expectedCoinsAvailable = new TreeMap<>(Map.ofEntries(
                entry(Coin.COIN_1, 100),
                entry(Coin.COIN_5, 60),
                entry(Coin.COIN_10, 0),
                entry(Coin.COIN_25, 0)
        ));

        ExchangeService exchangeService = new ExchangeService(initialCoinMap100);
        Map<Coin, Integer> actualCoinMap = exchangeService.exchangeCoins(billMap);

        assertEquals(expectedCoinsExchanged, actualCoinMap);
        assertEquals(expectedCoinsAvailable, exchangeService.getCoinMap());
    }

    @Test
    void exchangeLeastAmountOfCoinsFromBills3() {
        Map<Bill, Integer> billMap = new HashMap<>(Map.ofEntries(
                entry(Bill.BILL_5, 3),
                entry(Bill.BILL_20, 1)
        ));

        Map<Coin, Integer> expectedCoinsExchanged = new TreeMap<>(Map.ofEntries(
                entry(Coin.COIN_1, 0),
                entry(Coin.COIN_5, 0),
                entry(Coin.COIN_10, 100),
                entry(Coin.COIN_25, 100)
        ));

        Map<Coin, Integer> expectedCoinsAvailable = new TreeMap<>(Map.ofEntries(
                entry(Coin.COIN_1, 100),
                entry(Coin.COIN_5, 100),
                entry(Coin.COIN_10, 0),
                entry(Coin.COIN_25, 0)
        ));

        ExchangeService exchangeService = new ExchangeService(initialCoinMap100);
        Map<Coin, Integer> actualCoinMap = exchangeService.exchangeCoins(billMap);

        assertEquals(expectedCoinsExchanged, actualCoinMap);
        assertEquals(expectedCoinsAvailable, exchangeService.getCoinMap());
    }

    @Test
    void exchangeLeastAmountOfCoinsFromBills4() {
        Map<Bill, Integer> billMap = new HashMap<>(Map.ofEntries(
                entry(Bill.BILL_1, 17),
                entry(Bill.BILL_2, 1),
                entry(Bill.BILL_5, 1)
        ));

        Map<Coin, Integer> expectedCoinsExchanged = new TreeMap<>(Map.ofEntries(
                entry(Coin.COIN_1, 0),
                entry(Coin.COIN_5, 0),
                entry(Coin.COIN_10, 0),
                entry(Coin.COIN_25, 96)
        ));

        Map<Coin, Integer> expectedCoinsAvailable = new TreeMap<>(Map.ofEntries(
                entry(Coin.COIN_1, 100),
                entry(Coin.COIN_5, 100),
                entry(Coin.COIN_10, 100),
                entry(Coin.COIN_25, 4)
        ));

        ExchangeService exchangeService = new ExchangeService(initialCoinMap100);
        Map<Coin, Integer> actualCoinMap = exchangeService.exchangeCoins(billMap);

        assertEquals(expectedCoinsExchanged, actualCoinMap);
        assertEquals(expectedCoinsAvailable, exchangeService.getCoinMap());
    }

    @Test
    void exchangeLeastAmountOfCoinsFromBills5() {
        Map<Bill, Integer> billMap = new HashMap<>(Map.ofEntries(
                entry(Bill.BILL_1, 1)
        ));

        Map<Coin, Integer> expectedCoinsExchanged = new TreeMap<>(Map.ofEntries(
                entry(Coin.COIN_1, 0),
                entry(Coin.COIN_5, 0),
                entry(Coin.COIN_10, 0),
                entry(Coin.COIN_25, 4)
        ));

        Map<Coin, Integer> expectedCoinsAvailable = new TreeMap<>(Map.ofEntries(
                entry(Coin.COIN_1, 100),
                entry(Coin.COIN_5, 100),
                entry(Coin.COIN_10, 100),
                entry(Coin.COIN_25, 96)
        ));

        ExchangeService exchangeService = new ExchangeService(initialCoinMap100);
        Map<Coin, Integer> actualCoinMap = exchangeService.exchangeCoins(billMap);

        assertEquals(expectedCoinsExchanged, actualCoinMap);
        assertEquals(expectedCoinsAvailable, exchangeService.getCoinMap());
    }

    @Test
    void exchangeLeastAmountOfCoinsFromBills6() {
        Map<Bill, Integer> billMap = new HashMap<>(Map.ofEntries(
                entry(Bill.BILL_1, 0),
                entry(Bill.BILL_2, 0),
                entry(Bill.BILL_5, 1),
                entry(Bill.BILL_50, 2),
                entry(Bill.BILL_100, 1)
        ));

        Map<Coin, Integer> expectedCoinsExchanged = new TreeMap<>(Map.ofEntries(
                entry(Coin.COIN_25, 500),
                entry(Coin.COIN_10, 500),
                entry(Coin.COIN_5, 500),
                entry(Coin.COIN_1, 500)
        ));

        Map<Coin, Integer> expectedCoinsAvailable = new TreeMap<>(Map.ofEntries(
                entry(Coin.COIN_1, 0),
                entry(Coin.COIN_5, 0),
                entry(Coin.COIN_10, 0),
                entry(Coin.COIN_25, 0)
        ));

        ExchangeService exchangeService = new ExchangeService(initialCoinMap500);
        Map<Coin, Integer> actualCoinMap = exchangeService.exchangeCoins(billMap, ExchangeStrategy.LEAST_AMOUNT_OF_COINS);

        assertEquals(expectedCoinsExchanged, actualCoinMap);
        assertEquals(expectedCoinsAvailable, exchangeService.getCoinMap());
    }

    @Test
    void exchangeLeastAmountOfCoinsFromBills7() {
        Map<Bill, Integer> billMap = new HashMap<>(Map.ofEntries(
                entry(Bill.BILL_1, 1),
                entry(Bill.BILL_2, 2),
                entry(Bill.BILL_5, 0),
                entry(Bill.BILL_50, 4),
                entry(Bill.BILL_100, 0)
        ));

        Map<Coin, Integer> expectedCoinsExchanged = new TreeMap<>(Map.ofEntries(
                entry(Coin.COIN_25, 500),
                entry(Coin.COIN_10, 500),
                entry(Coin.COIN_5, 500),
                entry(Coin.COIN_1, 500)
        ));

        Map<Coin, Integer> expectedCoinsAvailable = new TreeMap<>(Map.ofEntries(
                entry(Coin.COIN_1, 0),
                entry(Coin.COIN_5, 0),
                entry(Coin.COIN_10, 0),
                entry(Coin.COIN_25, 0)
        ));

        ExchangeService exchangeService = new ExchangeService(initialCoinMap500);
        Map<Coin, Integer> actualCoinMap = exchangeService.exchangeCoins(
                billMap, ExchangeStrategy.LEAST_AMOUNT_OF_COINS);

        assertEquals(expectedCoinsExchanged, actualCoinMap);
        assertEquals(expectedCoinsAvailable, exchangeService.getCoinMap());
    }

    @Test
    void exchangeMostAmountOfCoins1() {
        int exchangeAmount = 41;

        Map<Coin, Integer> expectedCoinsExchanged = new TreeMap<>(Map.ofEntries(
                entry(Coin.COIN_1, 100),
                entry(Coin.COIN_5, 100),
                entry(Coin.COIN_10, 100),
                entry(Coin.COIN_25, 100)
        ));

        Map<Coin, Integer> expectedCoinsAvailable = new TreeMap<>(Map.ofEntries(
                entry(Coin.COIN_1, 0),
                entry(Coin.COIN_5, 0),
                entry(Coin.COIN_10, 0),
                entry(Coin.COIN_25, 0)
        ));

        ExchangeService exchangeService = new ExchangeService(initialCoinMap100);
        Map<Coin, Integer> actualCoinMap = exchangeService.exchangeCoins(
                new ExchangeRequest(exchangeAmount, ExchangeStrategy.MOST_AMOUNT_OF_COINS));

        assertEquals(expectedCoinsExchanged, actualCoinMap);
        assertEquals(expectedCoinsAvailable, exchangeService.getCoinMap());
    }

    @Test
    void exchangeMostAmountOfCoins2() {
        int exchangeAmount = 35;

        Map<Coin, Integer> expectedCoinsExchanged = new TreeMap<>(Map.ofEntries(
                entry(Coin.COIN_1, 100),
                entry(Coin.COIN_5, 100),
                entry(Coin.COIN_10, 100),
                entry(Coin.COIN_25, 76)
        ));

        Map<Coin, Integer> expectedCoinsAvailable = new TreeMap<>(Map.ofEntries(
                entry(Coin.COIN_1, 0),
                entry(Coin.COIN_5, 0),
                entry(Coin.COIN_10, 0),
                entry(Coin.COIN_25, 24)
        ));

        ExchangeService exchangeService = new ExchangeService(initialCoinMap100);
        Map<Coin, Integer> actualCoinMap = exchangeService.exchangeCoins(
                new ExchangeRequest(exchangeAmount, ExchangeStrategy.MOST_AMOUNT_OF_COINS));

        assertEquals(expectedCoinsExchanged, actualCoinMap);
        assertEquals(expectedCoinsAvailable, exchangeService.getCoinMap());
    }

    @Test
    void exchangeMostAmountOfCoins3() {
        int exchangeAmount = 14;

        Map<Coin, Integer> expectedCoinsExchanged = new TreeMap<>(Map.ofEntries(
                entry(Coin.COIN_1, 100),
                entry(Coin.COIN_5, 100),
                entry(Coin.COIN_10, 80),
                entry(Coin.COIN_25, 0)
        ));

        Map<Coin, Integer> expectedCoinsAvailable = new TreeMap<>(Map.ofEntries(
                entry(Coin.COIN_1, 0),
                entry(Coin.COIN_5, 0),
                entry(Coin.COIN_10, 20),
                entry(Coin.COIN_25, 100)
        ));

        ExchangeService exchangeService = new ExchangeService(initialCoinMap100);
        Map<Coin, Integer> actualCoinMap = exchangeService.exchangeCoins(
                new ExchangeRequest(exchangeAmount, ExchangeStrategy.MOST_AMOUNT_OF_COINS));

        assertEquals(expectedCoinsExchanged, actualCoinMap);
        assertEquals(expectedCoinsAvailable, exchangeService.getCoinMap());
    }

    @Test
    void exchangeMostAmountOfCoins4() {
        int exchangeAmount = 5;

        Map<Coin, Integer> expectedCoinsExchanged = new TreeMap<>(Map.ofEntries(
                entry(Coin.COIN_1, 100),
                entry(Coin.COIN_5, 80),
                entry(Coin.COIN_10, 0),
                entry(Coin.COIN_25, 0)
        ));

        Map<Coin, Integer> expectedCoinsAvailable = new TreeMap<>(Map.ofEntries(
                entry(Coin.COIN_1, 0),
                entry(Coin.COIN_5, 20),
                entry(Coin.COIN_10, 100),
                entry(Coin.COIN_25, 100)
        ));

        ExchangeService exchangeService = new ExchangeService(initialCoinMap100);
        Map<Coin, Integer> actualCoinMap = exchangeService.exchangeCoins(
                new ExchangeRequest(exchangeAmount, ExchangeStrategy.MOST_AMOUNT_OF_COINS));

        assertEquals(expectedCoinsExchanged, actualCoinMap);
        assertEquals(expectedCoinsAvailable, exchangeService.getCoinMap());
    }

    @Test
    void exchangeMostAmountOfCoins5() {
        int exchangeAmount = 1;

        Map<Coin, Integer> expectedCoinsExchanged = new TreeMap<>(Map.ofEntries(
                entry(Coin.COIN_1, 100),
                entry(Coin.COIN_5, 0),
                entry(Coin.COIN_10, 0),
                entry(Coin.COIN_25, 0)
        ));

        Map<Coin, Integer> expectedCoinsAvailable = new TreeMap<>(Map.ofEntries(
                entry(Coin.COIN_1, 0),
                entry(Coin.COIN_5, 100),
                entry(Coin.COIN_10, 100),
                entry(Coin.COIN_25, 100)
        ));

        ExchangeService exchangeService = new ExchangeService(initialCoinMap100);
        Map<Coin, Integer> actualCoinMap = exchangeService.exchangeCoins(
                new ExchangeRequest(exchangeAmount, ExchangeStrategy.MOST_AMOUNT_OF_COINS));

        assertEquals(expectedCoinsExchanged, actualCoinMap);
        assertEquals(expectedCoinsAvailable, exchangeService.getCoinMap());
    }

    @Test
    void exchangeMostAmountOfCoinsFromBills1() {
        Map<Bill, Integer> billMap = new HashMap<>(Map.ofEntries(
                entry(Bill.BILL_1, 11),
                entry(Bill.BILL_2, 5),
                entry(Bill.BILL_20, 1)
        ));

        Map<Coin, Integer> expectedCoinsExchanged = new TreeMap<>(Map.ofEntries(
                entry(Coin.COIN_1, 100),
                entry(Coin.COIN_5, 100),
                entry(Coin.COIN_10, 100),
                entry(Coin.COIN_25, 100)
        ));

        Map<Coin, Integer> expectedCoinsAvailable = new TreeMap<>(Map.ofEntries(
                entry(Coin.COIN_1, 0),
                entry(Coin.COIN_5, 0),
                entry(Coin.COIN_10, 0),
                entry(Coin.COIN_25, 0)
        ));

        ExchangeService exchangeService = new ExchangeService(initialCoinMap100);
        Map<Coin, Integer> actualCoinMap = exchangeService.exchangeCoins(billMap, ExchangeStrategy.MOST_AMOUNT_OF_COINS);

        assertEquals(expectedCoinsExchanged, actualCoinMap);
        assertEquals(expectedCoinsAvailable, exchangeService.getCoinMap());
    }

    @Test
    void exchangeMostAmountOfCoinsFromBills2() {
        Map<Bill, Integer> billMap = new HashMap<>(Map.ofEntries(
                entry(Bill.BILL_1, 1),
                entry(Bill.BILL_2, 3),
                entry(Bill.BILL_5, 4),
                entry(Bill.BILL_10, 1)
        ));

        Map<Coin, Integer> expectedCoinsExchanged = new TreeMap<>(Map.ofEntries(
                entry(Coin.COIN_1, 0),
                entry(Coin.COIN_5, 40),
                entry(Coin.COIN_10, 100),
                entry(Coin.COIN_25, 100)
        ));

        Map<Coin, Integer> expectedCoinsAvailable = new TreeMap<>(Map.ofEntries(
                entry(Coin.COIN_1, 100),
                entry(Coin.COIN_5, 60),
                entry(Coin.COIN_10, 0),
                entry(Coin.COIN_25, 0)
        ));

        ExchangeService exchangeService = new ExchangeService(initialCoinMap100);
        Map<Coin, Integer> actualCoinMap = exchangeService.exchangeCoins(billMap);

        assertEquals(expectedCoinsExchanged, actualCoinMap);
        assertEquals(expectedCoinsAvailable, exchangeService.getCoinMap());
    }

    @Test
    void exchangeMostAmountOfCoinsFromBills3() {
        Map<Bill, Integer> billMap = new HashMap<>(Map.ofEntries(
                entry(Bill.BILL_5, 3),
                entry(Bill.BILL_20, 1)
        ));

        Map<Coin, Integer> expectedCoinsExchanged = new TreeMap<>(Map.ofEntries(
                entry(Coin.COIN_1, 0),
                entry(Coin.COIN_5, 0),
                entry(Coin.COIN_10, 100),
                entry(Coin.COIN_25, 100)
        ));

        Map<Coin, Integer> expectedCoinsAvailable = new TreeMap<>(Map.ofEntries(
                entry(Coin.COIN_1, 100),
                entry(Coin.COIN_5, 100),
                entry(Coin.COIN_10, 0),
                entry(Coin.COIN_25, 0)
        ));

        ExchangeService exchangeService = new ExchangeService(initialCoinMap100);
        Map<Coin, Integer> actualCoinMap = exchangeService.exchangeCoins(billMap);

        assertEquals(expectedCoinsExchanged, actualCoinMap);
        assertEquals(expectedCoinsAvailable, exchangeService.getCoinMap());
    }

    @Test
    void exchangeMostAmountOfCoinsFromBills4() {
        Map<Bill, Integer> billMap = new HashMap<>(Map.ofEntries(
                entry(Bill.BILL_1, 17),
                entry(Bill.BILL_2, 1),
                entry(Bill.BILL_5, 1)
        ));

        Map<Coin, Integer> expectedCoinsExchanged = new TreeMap<>(Map.ofEntries(
                entry(Coin.COIN_1, 100),
                entry(Coin.COIN_5, 100),
                entry(Coin.COIN_10, 100),
                entry(Coin.COIN_25, 32)
        ));

        Map<Coin, Integer> expectedCoinsAvailable = new TreeMap<>(Map.ofEntries(
                entry(Coin.COIN_1, 0),
                entry(Coin.COIN_5, 0),
                entry(Coin.COIN_10, 0),
                entry(Coin.COIN_25, 68)
        ));

        ExchangeService exchangeService = new ExchangeService(initialCoinMap100);
        Map<Coin, Integer> actualCoinMap = exchangeService.exchangeCoins(billMap, ExchangeStrategy.MOST_AMOUNT_OF_COINS);

        assertEquals(expectedCoinsExchanged, actualCoinMap);
        assertEquals(expectedCoinsAvailable, exchangeService.getCoinMap());
    }

    @Test
    void exchangeMostAmountOfCoinsFromBills5() {
        Map<Bill, Integer> billMap = new HashMap<>(Map.ofEntries(
                entry(Bill.BILL_1, 1)
        ));

        Map<Coin, Integer> expectedCoinsExchanged = new TreeMap<>(Map.ofEntries(
                entry(Coin.COIN_1, 100),
                entry(Coin.COIN_5, 0),
                entry(Coin.COIN_10, 0),
                entry(Coin.COIN_25, 0)
        ));

        Map<Coin, Integer> expectedCoinsAvailable = new TreeMap<>(Map.ofEntries(
                entry(Coin.COIN_1, 0),
                entry(Coin.COIN_5, 100),
                entry(Coin.COIN_10, 100),
                entry(Coin.COIN_25, 100)
        ));

        ExchangeService exchangeService = new ExchangeService(initialCoinMap100);
        Map<Coin, Integer> actualCoinMap = exchangeService.exchangeCoins(billMap, ExchangeStrategy.MOST_AMOUNT_OF_COINS);

        assertEquals(expectedCoinsExchanged, actualCoinMap);
        assertEquals(expectedCoinsAvailable, exchangeService.getCoinMap());
    }

    @Test
    void exchangeMostAmountOfCoinsFromBills6() {
        Map<Bill, Integer> billMap = new HashMap<>(Map.ofEntries(
                entry(Bill.BILL_1, 0),
                entry(Bill.BILL_2, 0),
                entry(Bill.BILL_50, 2),
                entry(Bill.BILL_100, 1)
        ));

        Map<Coin, Integer> expectedCoinsExchanged = new TreeMap<>(Map.ofEntries(
                entry(Coin.COIN_25, 480),
                entry(Coin.COIN_10, 500),
                entry(Coin.COIN_5, 500),
                entry(Coin.COIN_1, 500)
        ));

        Map<Coin, Integer> expectedCoinsAvailable = new TreeMap<>(Map.ofEntries(
                entry(Coin.COIN_1, 0),
                entry(Coin.COIN_5, 0),
                entry(Coin.COIN_10, 0),
                entry(Coin.COIN_25, 20)
        ));

        ExchangeService exchangeService = new ExchangeService(initialCoinMap500);
        Map<Coin, Integer> actualCoinMap = exchangeService.exchangeCoins(billMap, ExchangeStrategy.MOST_AMOUNT_OF_COINS);

        assertEquals(expectedCoinsExchanged, actualCoinMap);
        assertEquals(expectedCoinsAvailable, exchangeService.getCoinMap());
    }

    @Test
    void exchangeMostAmountOfCoinsFromBills7() {
        Map<Bill, Integer> billMap = new HashMap<>(Map.ofEntries(
                entry(Bill.BILL_1, 1),
                entry(Bill.BILL_2, 2),
                entry(Bill.BILL_5, 0),
                entry(Bill.BILL_50, 4),
                entry(Bill.BILL_100, 0)
        ));

        Map<Coin, Integer> expectedCoinsExchanged = new TreeMap<>(Map.ofEntries(
                entry(Coin.COIN_25, 500),
                entry(Coin.COIN_10, 500),
                entry(Coin.COIN_5, 500),
                entry(Coin.COIN_1, 500)
        ));

        Map<Coin, Integer> expectedCoinsAvailable = new TreeMap<>(Map.ofEntries(
                entry(Coin.COIN_1, 0),
                entry(Coin.COIN_5, 0),
                entry(Coin.COIN_10, 0),
                entry(Coin.COIN_25, 0)
        ));

        ExchangeService exchangeService = new ExchangeService(initialCoinMap500);
        Map<Coin, Integer> actualCoinMap = exchangeService.exchangeCoins(billMap, ExchangeStrategy.MOST_AMOUNT_OF_COINS);

        assertEquals(expectedCoinsExchanged, actualCoinMap);
        assertEquals(expectedCoinsAvailable, exchangeService.getCoinMap());
    }

    @Test
    void notEnoughCoinsExceptionLeastAmountOfCoins() {
        int exchangeAmount = 42;

        ExchangeService exchangeService = new ExchangeService(initialCoinMap100);

        NotEnoughCoinsException actualException = assertThrowsExactly(NotEnoughCoinsException.class,
                () -> exchangeService.exchangeCoins(
                        new ExchangeRequest(exchangeAmount, ExchangeStrategy.MOST_AMOUNT_OF_COINS))
        );
        assertEquals("Not enough coins, missing R$1.00", actualException.getMessage());
    }

    @Test
    void notEnoughCoinsExceptionMostAmountOfCoins() {
        int exchangeAmount = 43;

        ExchangeService exchangeService = new ExchangeService(initialCoinMap100);

        NotEnoughCoinsException actualException = assertThrowsExactly(NotEnoughCoinsException.class,
                () -> exchangeService.exchangeCoins(
                        new ExchangeRequest(exchangeAmount, ExchangeStrategy.MOST_AMOUNT_OF_COINS))
        );
        assertEquals("Not enough coins, missing R$2.00", actualException.getMessage());
    }

    @Test
    void notEnoughCoinsExceptionLeastAmountOfCoinsFromBills() {
        Map<Bill, Integer> billMap = new HashMap<>(Map.ofEntries(
                entry(Bill.BILL_1, 27),
                entry(Bill.BILL_5, 1),
                entry(Bill.BILL_10, 1)
        ));

        ExchangeService exchangeService = new ExchangeService(initialCoinMap100);

        NotEnoughCoinsException actualException = assertThrowsExactly(NotEnoughCoinsException.class,
                () -> exchangeService.exchangeCoins(billMap, ExchangeStrategy.LEAST_AMOUNT_OF_COINS)
        );
        assertEquals("Not enough coins, missing R$1.00", actualException.getMessage());
    }

    @Test
    void notEnoughCoinsExceptionMostAmountOfCoinsFromBills() {
        Map<Bill, Integer> billMap = new HashMap<>(Map.ofEntries(
                entry(Bill.BILL_1, 23),
                entry(Bill.BILL_20, 1)
        ));
        ExchangeService exchangeService = new ExchangeService(initialCoinMap100);

        NotEnoughCoinsException actualException = assertThrowsExactly(NotEnoughCoinsException.class,
                () -> exchangeService.exchangeCoins(billMap, ExchangeStrategy.MOST_AMOUNT_OF_COINS)
        );
        assertEquals("Not enough coins, missing R$2.00", actualException.getMessage());
    }
}