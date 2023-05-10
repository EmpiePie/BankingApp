import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class User {

   /**
    * The first name of the user.
    */

    private String firstName;

    /**
     * The last name of user.
     */

    private String lastName;

     /**
      * The ID number of user.
      */

    private String uuid;

     /**
      * The MD5 hash of the user's pin code..
      */

    private byte pinHash[];

     /**
      * List of accounts for this user.
      */

    private ArrayList<Account> accounts;

    public User(String firstName, String lastName, String pin, Bank theBank) throws NoSuchAlgorithmException {

        // set user first and last name.
        this.firstName = firstName;
        this.lastName = lastName;

        // stores the pin's MD5 hash, instead of the original value.
        MessageDigest md = MessageDigest.getInstance("MD5");
        this.pinHash = md.digest(pin.getBytes());

        // get a new, unique ID for the user.
        this.uuid = theBank.getNewUserUUID();

        // create empty list of accounts.
        this.accounts = new ArrayList<Account>();

        // pring log message
        System.out.printf("New user %s, %s with ID %s created. \n", lastName, firstName, this.uuid);

    }

    /**
     * Add an account for the user
     * @param anAcct    the account to add
     */

    public void addAccount(Account anAcct){

        this.accounts.add(anAcct);
    }

    /**
     * Return the user's ID
     * @return the uuid
     */

    public String getUuid() {

        return uuid;
    }

    public String getFirstName() {
        return this.firstName;
    }

    /**
     * Check whether a given pin matches the true User pin
     * @param aPin  the pin to check
     * @return      whether the pin is valid or not
     * @throws NoSuchAlgorithmException
     */

    public boolean validatePin(String aPin) throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("MD5");

        return MessageDigest.isEqual(md.digest(aPin.getBytes()), this.pinHash);

    }


    public void printAccountsSummary() {

        System.out.printf("\n\n%s's accounts summary\n", this.firstName);
        for (int a = 0; a < this.accounts.size(); a++) {

            System.out.printf("%d) %s\n", a+1,
                    this.accounts.get(a).getSummaryLine());
        }
        System.out.println();
    }

    /**
     * Get the number of accounts for this user
     * @return number of accounts
     */

    public int numAccounts() {
        return this.accounts.size();
    }

    /**
     * Print transaction history for a particular account.
     * @param acctIdx the index of the account to use
     */

    public void printAcctTransHistory(int acctIdx) {

        this.accounts.get(acctIdx).printTransHistory();
    }

    /**
     * Get the balance of a particular account
     * @param acctIdx   Index of the account to use
     * @return          the balance of the account
     */

    public double getAcctBalance(int acctIdx) {
        return this.accounts.get(acctIdx).getBalance();
    }

    /**
     * Get the UUID of a specific account
     * @param acctIdx the Index of the account to use
     * @return the UUID of the account
     */

    public Object getAcctUUID(int acctIdx) {
        return this.accounts.get(acctIdx).getUuid();
    }

    /**
     * Add a transaction to a specific account
     * @param acctIdx   the Index of the account
     * @param amount    the amount of the transaction
     * @param memo      the memo of the transaction
     */

    public void addAcctTransaction(int acctIdx, double amount, String memo) {
        this.accounts.get(acctIdx).addTransaction(amount, memo);
    }
}
