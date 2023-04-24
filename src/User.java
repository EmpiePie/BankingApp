import java.nio.charset.StandardCharsets;
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
}
