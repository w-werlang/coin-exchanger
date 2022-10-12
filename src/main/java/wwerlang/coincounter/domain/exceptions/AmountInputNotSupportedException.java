package wwerlang.coincounter.domain.exceptions;

import wwerlang.coincounter.domain.Bill;

public class AmountInputNotSupportedException extends RuntimeException {

    public AmountInputNotSupportedException() {
        super();
    }

    @Override
    public String getMessage() {
        StringBuilder message = new StringBuilder("Input amount not supported. The supported bills are: ");

        for (Bill bill : Bill.values()) {
            message.append(bill.value).append(", ");
        }

        return message.substring(0, message.length() - 2);
    }
}
