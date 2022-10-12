package wwerlang.coincounter.domain.exceptions;

import wwerlang.coincounter.domain.Bill;

public class InputAmountNotSupportedException extends RuntimeException {

    public InputAmountNotSupportedException() {
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
