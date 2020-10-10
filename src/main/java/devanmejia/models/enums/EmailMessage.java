package devanmejia.models.enums;

public enum EmailMessage {
    ErrorMessage("We can not take yor order because of error"),
    SuccessfulMessage("We take your order!"),
    ReadyMessage("You order is collected");
    private final String s;
    EmailMessage(String s){
        this.s= s;
    }

    @Override
    public String toString() {
        return s;
    }
}
