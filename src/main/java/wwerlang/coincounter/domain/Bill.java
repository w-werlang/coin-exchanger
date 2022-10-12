package wwerlang.coincounter.domain;

public enum Bill {

    BILL_1(1),
    BILL_2(2),
    BILL_5(5),
    BILL_10(10),
    BILL_20(20),
    BILL_50(50),
    BILL_100(100);

    public final int value;

    Bill(int value) {
        this.value = value;
    }

}
