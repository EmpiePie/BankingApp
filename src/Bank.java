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

    public String getNewAccountUUID() {

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

        User newUser = new User(firstName, lastName, pin, this);
        this.users.add(newUser);

        Account newAccount = new Account("Savings", newUser, this);
        newUser.addAccount(newAccount);
        this.accounts.add(newAccount);

        return newUser;

    }

}
