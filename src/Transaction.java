import java.util.Date;

public class Transaction {

    /**
     * The amount of this transaction.
     */

    private double amount;

    /**
     * The time and date of this transaction.
     */

    private Date timestamp;

    /**
     * A memo for this transaction.
     */

    private String memo;

    /**
     * The account in which the transaction took place.
     */

    private Account inAccount;

    public Transaction(double amount, Account inAccount) {
        this.amount = amount;
        this.inAccount = inAccount;
        this.timestamp = new Date();
        this.memo = "";
    }

    public Transaction(double amount, String memo, Account inAccount) {
        this(amount, inAccount);
        this.memo = memo;
    }
}
