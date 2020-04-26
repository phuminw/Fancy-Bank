package fancybank.misc;

/**
 * Exception for corrupted interest rate database
 */

public class InterestRateDBCorruptException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
    public InterestRateDBCorruptException(String message, Throwable err) {
        super(message, err);
    }

    public InterestRateDBCorruptException(String message) {
        super(message);
    }
}