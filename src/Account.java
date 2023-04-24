import java.util.ArrayList;

public class Account {

    /**
     * The name of the account.
     */

    private String name;

    /**
     * The ID of the account.
     */

    private String uuid;

    /**
     * The User objects that holds this account.
     */

    private User holder;

    /**
     * List of transactions for this account.
     */

    private ArrayList<Transaction> transactions;

    /**
     * Create a new Account
     * @param name      the name of the account.
     * @param holder    the User objects that holds this account.
     * @param theBank   the bank that issues the account.
     */

    public Account(String name, User holder, Bank theBank) {

        // set the account name and holder.
        this.name = name;
        this.holder = holder;

        // get new account ID.
        this.uuid = theBank.getNewAccountUUID();

        // create empty list of transactions for this account.
        this.transactions = new ArrayList<Transaction>();

    }

    /**
     * Get the account ID
     * @return  the uuid
     */

    public String getUuid() {
        return uuid;
    }
}
