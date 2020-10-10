package devanmejia.exceptions;

public class StockException extends Exception{
    public StockException(String message) {
        super(message);
    }

    public StockException() {
        super();
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
