package wwerlang.coincounter.domain.exceptions;

import java.text.NumberFormat;
import java.util.Locale;

public class NotEnoughCoinsException extends RuntimeException {

    private final double missingAmount;

    public NotEnoughCoinsException(double missingAmount) {
        super();
        this.missingAmount = missingAmount;
    }

    @Override
    public String getMessage() {
        NumberFormat formatter = NumberFormat.getInstance(Locale.US);
        formatter.setMaximumFractionDigits(2);
        formatter.setMinimumFractionDigits(2);

        return "Not enough coins, missing R$" + formatter.format(missingAmount);
    }
}
