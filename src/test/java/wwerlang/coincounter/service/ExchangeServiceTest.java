package wwerlang.coincounter.service;

import org.junit.jupiter.api.Test;
import wwerlang.coincounter.domain.Bill;
import wwerlang.coincounter.domain.Coin;
import wwerlang.coincounter.domain.ExchangeStrategy;
import wwerlang.coincounter.domain.exceptions.NotEnoughCoinsException;

import java.util.*;

import static java.util.Map.entry;
import static org.junit.jupiter.api.Assertions.*;

class ExchangeServiceTest {

    private final Map<Coin, Integer> initialMap100Coins;

    private final Map<Coin, Integer> initialMap500Coins;

    ExchangeServiceTest() {
        initialMap100Coins = new HashMap<>(Map.ofEntries(
                entry(Coin.COIN_5, 100),
                entry(Coin.COIN_1, 100),
                entry(Coin.COIN_25, 100),
                entry(Coin.COIN_10, 100)
        ));

        initialMap500Coins = new HashMap<>(Map.ofEntries(
                entry(Coin.COIN_5, 500),
                entry(Coin.COIN_1, 500),
                entry(Coin.COIN_25, 500),
                entry(Coin.COIN_10, 500)
        ));
    }

    @Test
    void exchangeLeastAmountOfCoins1() {
        int exchangedAmount = 41;
        Map<Coin, Integer> expectedCoinMap = new TreeMap<>(Map.ofEntries(
                entry(Coin.COIN_1, 100),
                entry(Coin.COIN_5, 100),
                entry(Coin.COIN_10, 100),
                entry(Coin.COIN_25, 100)
        ));

        ExchangeService exchangeService = new ExchangeService(initialMap100Coins);
        Map<Coin, Integer> actualCoinMap = exchangeService.convertToCoins(exchangedAmount);

        assertEquals(expectedCoinMap, actualCoinMap);
    }

    @Test
    void exchangeLeastAmountOfCoins2() {
        int exchangedAmount = 37;
        Map<Coin, Integer> expectedCoinMap = new TreeMap<>(Map.ofEntries(
                entry(Coin.COIN_5, 40),
                entry(Coin.COIN_10, 100),
                entry(Coin.COIN_25, 100)
        ));

        ExchangeService exchangeService = new ExchangeService(initialMap100Coins);
        Map<Coin, Integer> actualCoinMap = exchangeService.convertToCoins(exchangedAmount);

        assertEquals(expectedCoinMap, actualCoinMap);
    }

    @Test
    void exchangeLeastAmountOfCoins3() {
        int exchangedAmount = 35;
        Map<Coin, Integer> expectedCoinMap = new TreeMap<>(Map.ofEntries(
                entry(Coin.COIN_10, 100),
                entry(Coin.COIN_25, 100)
        ));

        ExchangeService exchangeService = new ExchangeService(initialMap100Coins);
        Map<Coin, Integer> actualCoinMap = exchangeService.convertToCoins(exchangedAmount);

        assertEquals(expectedCoinMap, actualCoinMap);
    }

    @Test
    void exchangeLeastAmountOfCoins4() {
        int exchangedAmount = 24;
        Map<Coin, Integer> expectedCoinMap = new TreeMap<>(Map.ofEntries(
                entry(Coin.COIN_25, 96)
        ));

        ExchangeService exchangeService = new ExchangeService(initialMap100Coins);
        Map<Coin, Integer> actualCoinMap = exchangeService.convertToCoins(exchangedAmount, ExchangeStrategy.LEAST_AMOUNT_OF_COINS);

        assertEquals(expectedCoinMap, actualCoinMap);
    }

    @Test
    void exchangeLeastAmountOfCoins5() {
        int exchangedAmount = 1;
        Map<Coin, Integer> expectedCoinMap = new TreeMap<>(Map.ofEntries(
                entry(Coin.COIN_25, 4)
        ));

        ExchangeService exchangeService = new ExchangeService(initialMap100Coins);
        Map<Coin, Integer> actualCoinMap = exchangeService.convertToCoins(exchangedAmount, ExchangeStrategy.LEAST_AMOUNT_OF_COINS);

        assertEquals(expectedCoinMap, actualCoinMap);
    }

    @Test
    void exchangeLeastAmountOfCoinsFromBills1() {
        Map<Bill, Integer> billMap = new HashMap<>(Map.ofEntries(
                entry(Bill.BILL_1, 11),
                entry(Bill.BILL_2, 5),
                entry(Bill.BILL_20, 1)
        ));

        Map<Coin, Integer> expectedCoinMap = new TreeMap<>(Map.ofEntries(
                entry(Coin.COIN_1, 100),
                entry(Coin.COIN_5, 100),
                entry(Coin.COIN_10, 100),
                entry(Coin.COIN_25, 100)
        ));

        ExchangeService exchangeService = new ExchangeService(initialMap100Coins);
        Map<Coin, Integer> actualCoinMap = exchangeService.convertToCoins(billMap);

        assertEquals(expectedCoinMap, actualCoinMap);
    }

    @Test
    void exchangeLeastAmountOfCoinsFromBills2() {
        Map<Bill, Integer> billMap = new HashMap<>(Map.ofEntries(
                entry(Bill.BILL_1, 1),
                entry(Bill.BILL_2, 3),
                entry(Bill.BILL_5, 4),
                entry(Bill.BILL_10, 1)
        ));

        Map<Coin, Integer> expectedCoinMap = new TreeMap<>(Map.ofEntries(
                entry(Coin.COIN_5, 40),
                entry(Coin.COIN_10, 100),
                entry(Coin.COIN_25, 100)
        ));

        ExchangeService exchangeService = new ExchangeService(initialMap100Coins);
        Map<Coin, Integer> actualCoinMap = exchangeService.convertToCoins(billMap);

        assertEquals(expectedCoinMap, actualCoinMap);
    }

    @Test
    void exchangeLeastAmountOfCoinsFromBills3() {
        Map<Bill, Integer> billMap = new HashMap<>(Map.ofEntries(
                entry(Bill.BILL_5, 3),
                entry(Bill.BILL_20, 1)
        ));

        Map<Coin, Integer> expectedCoinMap = new TreeMap<>(Map.ofEntries(
                entry(Coin.COIN_10, 100),
                entry(Coin.COIN_25, 100)
        ));

        ExchangeService exchangeService = new ExchangeService(initialMap100Coins);
        Map<Coin, Integer> actualCoinMap = exchangeService.convertToCoins(billMap);

        assertEquals(expectedCoinMap, actualCoinMap);
    }

    @Test
    void exchangeLeastAmountOfCoinsFromBills4() {
        Map<Bill, Integer> billMap = new HashMap<>(Map.ofEntries(
                entry(Bill.BILL_1, 17),
                entry(Bill.BILL_2, 1),
                entry(Bill.BILL_5, 1)
        ));

        Map<Coin, Integer> expectedCoinMap = new TreeMap<>(Map.ofEntries(
                entry(Coin.COIN_25, 96)
        ));

        ExchangeService exchangeService = new ExchangeService(initialMap100Coins);
        Map<Coin, Integer> actualCoinMap = exchangeService.convertToCoins(billMap);

        assertEquals(expectedCoinMap, actualCoinMap);
    }

    @Test
    void exchangeLeastAmountOfCoinsFromBills5() {
        Map<Bill, Integer> billMap = new HashMap<>(Map.ofEntries(
                entry(Bill.BILL_1, 1)
        ));

        Map<Coin, Integer> expectedCoinMap = new TreeMap<>(Map.ofEntries(
                entry(Coin.COIN_25, 4)
        ));

        ExchangeService exchangeService = new ExchangeService(initialMap100Coins);
        Map<Coin, Integer> actualCoinMap = exchangeService.convertToCoins(billMap);

        assertEquals(expectedCoinMap, actualCoinMap);
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

        Map<Coin, Integer> expectedCoinMap = new TreeMap<>(Map.ofEntries(
                entry(Coin.COIN_25, 500),
                entry(Coin.COIN_10, 500),
                entry(Coin.COIN_5, 500),
                entry(Coin.COIN_1, 500)
        ));

        ExchangeService exchangeService = new ExchangeService(initialMap500Coins);
        Map<Coin, Integer> actualCoinMap = exchangeService.convertToCoins(billMap, ExchangeStrategy.LEAST_AMOUNT_OF_COINS);

        assertEquals(expectedCoinMap, actualCoinMap);
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

        Map<Coin, Integer> expectedCoinMap = new TreeMap<>(Map.ofEntries(
                entry(Coin.COIN_25, 500),
                entry(Coin.COIN_10, 500),
                entry(Coin.COIN_5, 500),
                entry(Coin.COIN_1, 500)
        ));

        ExchangeService exchangeService = new ExchangeService(initialMap500Coins);
        Map<Coin, Integer> actualCoinMap = exchangeService.convertToCoins(billMap, ExchangeStrategy.LEAST_AMOUNT_OF_COINS);

        assertEquals(expectedCoinMap, actualCoinMap);
    }

    @Test
    void exchangeMostAmountOfCoins1() {
        int exchangedAmount = 41;
        Map<Coin, Integer> expectedCoinMap = new TreeMap<>(Map.ofEntries(
                entry(Coin.COIN_1, 100),
                entry(Coin.COIN_5, 100),
                entry(Coin.COIN_10, 100),
                entry(Coin.COIN_25, 100)
        ));

        ExchangeService exchangeService = new ExchangeService(initialMap100Coins);
        Map<Coin, Integer> actualCoinMap = exchangeService.convertToCoins(exchangedAmount, ExchangeStrategy.MOST_AMOUNT_OF_COINS);

        assertEquals(expectedCoinMap, actualCoinMap);
    }

    @Test
    void exchangeMostAmountOfCoins2() {
        int exchangedAmount = 35;
        Map<Coin, Integer> expectedCoinMap = new TreeMap<>(Map.ofEntries(
                entry(Coin.COIN_1, 100),
                entry(Coin.COIN_5, 100),
                entry(Coin.COIN_10, 100),
                entry(Coin.COIN_25, 76)
        ));

        ExchangeService exchangeService = new ExchangeService(initialMap100Coins);
        Map<Coin, Integer> actualCoinMap = exchangeService.convertToCoins(exchangedAmount, ExchangeStrategy.MOST_AMOUNT_OF_COINS);

        assertEquals(expectedCoinMap, actualCoinMap);
    }

    @Test
    void exchangeMostAmountOfCoins3() {
        int exchangedAmount = 14;
        Map<Coin, Integer> expectedCoinMap = new TreeMap<>(Map.ofEntries(
                entry(Coin.COIN_1, 100),
                entry(Coin.COIN_5, 100),
                entry(Coin.COIN_10, 80)
        ));

        ExchangeService exchangeService = new ExchangeService(initialMap100Coins);
        Map<Coin, Integer> actualCoinMap = exchangeService.convertToCoins(exchangedAmount, ExchangeStrategy.MOST_AMOUNT_OF_COINS);

        assertEquals(expectedCoinMap, actualCoinMap);
    }

    @Test
    void exchangeMostAmountOfCoins4() {
        int exchangedAmount = 5;
        Map<Coin, Integer> expectedCoinMap = new TreeMap<>(Map.ofEntries(
                entry(Coin.COIN_1, 100),
                entry(Coin.COIN_5, 80)
        ));

        ExchangeService exchangeService = new ExchangeService(initialMap100Coins);
        Map<Coin, Integer> actualCoinMap = exchangeService.convertToCoins(exchangedAmount, ExchangeStrategy.MOST_AMOUNT_OF_COINS);

        assertEquals(expectedCoinMap, actualCoinMap);
    }

    @Test
    void exchangeMostAmountOfCoins5() {
        int exchangedAmount = 1;
        Map<Coin, Integer> expectedCoinMap = new TreeMap<>(Map.ofEntries(
                entry(Coin.COIN_1, 100)
        ));

        ExchangeService exchangeService = new ExchangeService(initialMap100Coins);
        Map<Coin, Integer> actualCoinMap = exchangeService.convertToCoins(exchangedAmount, ExchangeStrategy.MOST_AMOUNT_OF_COINS);

        assertEquals(expectedCoinMap, actualCoinMap);
    }

    @Test
    void exchangeMostAmountOfCoinsFromBills1() {
        Map<Bill, Integer> billMap = new HashMap<>(Map.ofEntries(
                entry(Bill.BILL_1, 11),
                entry(Bill.BILL_2, 5),
                entry(Bill.BILL_20, 1)
        ));

        Map<Coin, Integer> expectedCoinMap = new TreeMap<>(Map.ofEntries(
                entry(Coin.COIN_1, 100),
                entry(Coin.COIN_5, 100),
                entry(Coin.COIN_10, 100),
                entry(Coin.COIN_25, 100)
        ));

        ExchangeService exchangeService = new ExchangeService(initialMap100Coins);
        Map<Coin, Integer> actualCoinMap = exchangeService.convertToCoins(billMap, ExchangeStrategy.MOST_AMOUNT_OF_COINS);

        assertEquals(expectedCoinMap, actualCoinMap);
    }

    @Test
    void exchangeMostAmountOfCoinsFromBills2() {
        Map<Bill, Integer> billMap = new HashMap<>(Map.ofEntries(
                entry(Bill.BILL_1, 1),
                entry(Bill.BILL_2, 3),
                entry(Bill.BILL_5, 4),
                entry(Bill.BILL_10, 1)
        ));

        Map<Coin, Integer> expectedCoinMap = new TreeMap<>(Map.ofEntries(
                entry(Coin.COIN_5, 40),
                entry(Coin.COIN_10, 100),
                entry(Coin.COIN_25, 100)
        ));

        ExchangeService exchangeService = new ExchangeService(initialMap100Coins);
        Map<Coin, Integer> actualCoinMap = exchangeService.convertToCoins(billMap);

        assertEquals(expectedCoinMap, actualCoinMap);
    }

    @Test
    void exchangeMostAmountOfCoinsFromBills3() {
        Map<Bill, Integer> billMap = new HashMap<>(Map.ofEntries(
                entry(Bill.BILL_5, 3),
                entry(Bill.BILL_20, 1)
        ));

        Map<Coin, Integer> expectedCoinMap = new TreeMap<>(Map.ofEntries(
                entry(Coin.COIN_10, 100),
                entry(Coin.COIN_25, 100)
        ));

        ExchangeService exchangeService = new ExchangeService(initialMap100Coins);
        Map<Coin, Integer> actualCoinMap = exchangeService.convertToCoins(billMap);

        assertEquals(expectedCoinMap, actualCoinMap);
    }

    @Test
    void exchangeMostAmountOfCoinsFromBills4() {
        Map<Bill, Integer> billMap = new HashMap<>(Map.ofEntries(
                entry(Bill.BILL_1, 17),
                entry(Bill.BILL_2, 1),
                entry(Bill.BILL_5, 1)
        ));

        Map<Coin, Integer> expectedCoinMap = new TreeMap<>(Map.ofEntries(
                entry(Coin.COIN_1, 100),
                entry(Coin.COIN_5, 100),
                entry(Coin.COIN_10, 100),
                entry(Coin.COIN_25, 32)
        ));

        ExchangeService exchangeService = new ExchangeService(initialMap100Coins);
        Map<Coin, Integer> actualCoinMap = exchangeService.convertToCoins(billMap, ExchangeStrategy.MOST_AMOUNT_OF_COINS);

        assertEquals(expectedCoinMap, actualCoinMap);
    }

    @Test
    void exchangeMostAmountOfCoinsFromBills5() {
        Map<Bill, Integer> billMap = new HashMap<>(Map.ofEntries(
                entry(Bill.BILL_1, 1)
        ));

        Map<Coin, Integer> expectedCoinMap = new TreeMap<>(Map.ofEntries(
                entry(Coin.COIN_1, 100)
        ));

        ExchangeService exchangeService = new ExchangeService(initialMap100Coins);
        Map<Coin, Integer> actualCoinMap = exchangeService.convertToCoins(billMap, ExchangeStrategy.MOST_AMOUNT_OF_COINS);

        assertEquals(expectedCoinMap, actualCoinMap);
    }

    @Test
    void exchangeMostAmountOfCoinsFromBills6() {
        Map<Bill, Integer> billMap = new HashMap<>(Map.ofEntries(
                entry(Bill.BILL_1, 0),
                entry(Bill.BILL_2, 0),
                entry(Bill.BILL_50, 2),
                entry(Bill.BILL_100, 1)
        ));

        Map<Coin, Integer> expectedCoinMap = new TreeMap<>(Map.ofEntries(
                entry(Coin.COIN_25, 480),
                entry(Coin.COIN_10, 500),
                entry(Coin.COIN_5, 500),
                entry(Coin.COIN_1, 500)
        ));

        ExchangeService exchangeService = new ExchangeService(initialMap500Coins);
        Map<Coin, Integer> actualCoinMap = exchangeService.convertToCoins(billMap, ExchangeStrategy.MOST_AMOUNT_OF_COINS);

        assertEquals(expectedCoinMap, actualCoinMap);
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

        Map<Coin, Integer> expectedCoinMap = new TreeMap<>(Map.ofEntries(
                entry(Coin.COIN_25, 500),
                entry(Coin.COIN_10, 500),
                entry(Coin.COIN_5, 500),
                entry(Coin.COIN_1, 500)
        ));

        ExchangeService exchangeService = new ExchangeService(initialMap500Coins);
        Map<Coin, Integer> actualCoinMap = exchangeService.convertToCoins(billMap, ExchangeStrategy.MOST_AMOUNT_OF_COINS);

        assertEquals(expectedCoinMap, actualCoinMap);
    }

    @Test
    void notEnoughCoinsExceptionLeastAmountOfCoins() {
        int exchangedAmount = 42;
        ExchangeService exchangeService = new ExchangeService(initialMap100Coins);

        NotEnoughCoinsException actualException = assertThrowsExactly(NotEnoughCoinsException.class,
                () -> exchangeService.convertToCoins(exchangedAmount, ExchangeStrategy.LEAST_AMOUNT_OF_COINS)
        );
        assertEquals("Not enough coins, missing R$1.00", actualException.getMessage());
    }

    @Test
    void notEnoughCoinsExceptionMostAmountOfCoins() {
        int exchangedAmount = 43;
        ExchangeService exchangeService = new ExchangeService(initialMap100Coins);

        NotEnoughCoinsException actualException = assertThrowsExactly(NotEnoughCoinsException.class,
                () -> exchangeService.convertToCoins(exchangedAmount, ExchangeStrategy.MOST_AMOUNT_OF_COINS)
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
        ExchangeService exchangeService = new ExchangeService(initialMap100Coins);

        NotEnoughCoinsException actualException = assertThrowsExactly(NotEnoughCoinsException.class,
                () -> exchangeService.convertToCoins(billMap, ExchangeStrategy.LEAST_AMOUNT_OF_COINS)
        );
        assertEquals("Not enough coins, missing R$1.00", actualException.getMessage());
    }

    @Test
    void notEnoughCoinsExceptionMostAmountOfCoinsFromBills() {
        Map<Bill, Integer> billMap = new HashMap<>(Map.ofEntries(
                entry(Bill.BILL_1, 23),
                entry(Bill.BILL_20, 1)
        ));
        ExchangeService exchangeService = new ExchangeService(initialMap100Coins);

        NotEnoughCoinsException actualException = assertThrowsExactly(NotEnoughCoinsException.class,
                () -> exchangeService.convertToCoins(billMap, ExchangeStrategy.MOST_AMOUNT_OF_COINS)
        );
        assertEquals("Not enough coins, missing R$2.00", actualException.getMessage());
    }
}