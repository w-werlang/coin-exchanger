package wwerlang.coincounter.domain;

public enum Coin {

    COIN_1(0.01),
    COIN_5(0.05),
    COIN_10(0.10),
    COIN_25(0.25);

    public final double value;

    Coin(double value) {
        this.value = value;
    }
}
