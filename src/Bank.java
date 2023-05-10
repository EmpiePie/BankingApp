import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Random;

public class Bank {

    /**
     * Name of the Bank
     */

    private String name;

    /**
     * List of users with accounts at this bank
     */

    private ArrayList<User> users;

    /**
     * List of accounts held by the bank.
     */

    private ArrayList<Account> accounts;

    /**
     * Create a new Bank object with empty lists of users and accounts
     * @param name  the name of the bank
     */

    public Bank(String name) {
        this.name = name;
        this.users = new ArrayList<>();
        this.accounts = new ArrayList<Account>();
    }

    /**
     * Generate a new universally unique ID for a user.
     * @return the UUID.
     */

    public String getNewUserUUID() {

        // inits

        String uuid;
        Random rng = new Random();
        int len = 6;
        boolean nonUnique;

        //continue looping until we get an unique ID

        do {

            //generate the number

            uuid = "";

            for (int i = 0; i < len; i++) {

                uuid += ((Integer)rng.nextInt(10)).toString();

            }

            //check to make sure it is unique.

            nonUnique = false;
            for (User u : this.users) {

                if (uuid.compareTo(u.getUuid()) == 0) {
                    nonUnique = true;
                    break;
                }

            }

        } while (nonUnique);

        return uuid;

    }

    /**
     * Generate a new universally unique ID for an account.
     * @return the UUID.
     */

    public String getNewAccountUUID() {

        // inits

        String uuid;

        Random rng = new Random();
        int len = 6;
        boolean nonUnique;

        //continue looping until we get a unique ID

        do {

            //generate the number

            uuid = "";

            for (int i = 0; i < len; i++) {

                uuid += ((Integer)rng.nextInt(10)).toString();

            }

            //check to make sure it is unique.

            nonUnique = false;
            for (Account a : this.accounts) {

                if (uuid.compareTo(a.getUuid()) == 0) {
                    nonUnique = true;
                    break;
                }

            }

        } while (nonUnique);

        return uuid;

    }

    /**
     * Add an account.
     * @param anAcct    the account to add.
     */

    public void addAccount(Account anAcct) {

        this.accounts.add(anAcct);

    }

    public User addUser(String firstName, String lastName, String pin) throws NoSuchAlgorithmException {

        // create a new User object and add it to our list

        User newUser = new User(firstName, lastName, pin, this);
        this.users.add(newUser);

        // create a savings account for the user

        Account newAccount = new Account("Savings", newUser, this);
        newUser.addAccount(newAccount);
        this.accounts.add(newAccount);

        return newUser;

    }

    public User userLogin(String userID, String pin) throws NoSuchAlgorithmException {

        // search through list of users
        for(User u : this.users) {

            //check user ID is correct
            if (u.getUuid().compareTo(userID) == 0 && u.validatePin(pin)) {

                return u;
            }
        }

        //if we haven't found the user or have an incorrect pin
        return null;
    }

    public String getName() {
        return this.name;
    }
}
