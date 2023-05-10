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

    /**
     * Get summary line for account
     * @return the summary as String
     */

    public String getSummaryLine() {

        // get the account balance
        double balance = this.getBalance();

        //format the summary line, depending on whether the balance is negative
        if (balance >= 0) {
            return String.format("%s : R%.02f : %s", this.uuid, balance, this.name);
        }

        else {
            return String.format("%s : R(%.02f) : %s", this.uuid, balance, this.name);
        }
    }

    /**
     * Get the balance of this account by adding the amounts of the transactions
     * @return the balance
     */

    public double getBalance() {

        double balance = 0;

        for (Transaction t : this.transactions) {
            balance += t.getAmount();
        }
        return balance;
    }

    /**
     * Print the transaction history of the account
     */

    public void printTransHistory() {

        System.out.printf("\n Transaction history for account %s\n", this.uuid);
        for (int t = this.transactions.size()-1; t >= 0; t--) {
            System.out.printf(this.transactions.get(t).getSummaryLine());
        }
        System.out.println();
    }

    /**
     * Add a new transaction to the account
     * @param amount    the amount transacted
     * @param memo      the transaction memo.
     */

    public void addTransaction(double amount, String memo) {

        // create new transaction and add it to the list
        Transaction newTrans = new Transaction(amount, memo, this);
        this.transactions.add(newTrans);
    }
}
