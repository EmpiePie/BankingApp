import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class ATM {

    public static void main(String[] args) throws NoSuchAlgorithmException {

        //init Scanner
        Scanner sc = new Scanner(System.in);

        //init Bank
        Bank theBank = new Bank("Bank of Rathal");

        // add a user, which also creates a savings account
        User aUser = theBank.addUser("John", "Burr", "1234");

        // add a checking account for this user.
        Account newAccount = new Account("Checking", aUser, theBank);
        aUser.addAccount(newAccount);
        theBank.addAccount(newAccount);

        User curUser;

        while (true) {

            // stay in the login prompt until successful login

            curUser = ATM.mainMenuPrompt(theBank, sc);


            // stay in main menu until user quits
            ATM.printUserMenu(curUser, sc);
        }
    }

    /**
     * Display the main menu and prompt for log-in details.
     * @param theBank   the Bank holding the user accounts
     * @param sc        the Scanner used for user input
     * @return authUser. Successfully logged-in user.
     * @throws NoSuchAlgorithmException
     */

    public static User mainMenuPrompt(Bank theBank, Scanner sc) throws NoSuchAlgorithmException {

        // inits

        String userID;
        String pin;
        User authUser;

        // prompt the user for user ID/pin combo until a correct one is reached

        do {

            System.out.printf("\n\nWelcome to %s\n\n", theBank.getName());
            System.out.print("Enter user ID: ");
            userID = sc.nextLine();
            System.out.print("Enter pin: ");
            pin = sc.nextLine();

            // try to get the user object corresponding to the ID and pin combo
            authUser = theBank.userLogin(userID, pin);
            if (authUser == null) {
                System.out.println("Incorrect user ID/pin combination. " + "Please try again.");
            }

        }while(authUser == null); // continue looping until success

        return authUser;
    }

    /**
     * list of options for logged-in user
     * @param theUser   the logged-in user
     * @param sc        the Scanner used for user input.
     */

    public static void printUserMenu(User theUser, Scanner sc) {

        // print a summary of the user's accounts

        theUser.printAccountsSummary();

        // init
        int choice;

        //user menu
        do {
            System.out.printf("Welcome %s. Please choose what you would like to do:\n", theUser.getFirstName());

            System.out.println("  1) Show account transaction history");
            System.out.println("  2) Withdrawl");
            System.out.println("  3) Deposit");
            System.out.println("  4) Transfer");
            System.out.println("  5) Quit");
            System.out.println();
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            if(choice < 1 || choice > 5) {
                System.out.println("Invalid choice. Please try again");
            }

        }while (choice < 1 || choice > 5);

        // process the choice
        switch (choice) {

            case 1:
                ATM.showTransHistory(theUser, sc);
                break;

            case 2:
                ATM.withdrawFunds(theUser, sc);
                break;

            case 3:
                ATM.depositFunds(theUser, sc);
                break;

            case 4:
                ATM.transferFunds(theUser, sc);
                break;

            case 5:

        }

        //show menu again if user doesn't quit.
        if (choice != 5) {
            ATM.printUserMenu(theUser, sc);
        }
    }

    /**
     * Show the transaction history for an account
     * @param theUser   the logged-in User
     * @param sc        the Scanner used for user input
     */

    public static void showTransHistory(User theUser, Scanner sc) {

        int theAcct;

        //get account whose transaction history to look at

        do {
            System.out.printf("Enter the account number (1 - %d) whose transactions you want to see: ", theUser.numAccounts());

            theAcct = sc.nextInt() - 1;

            if (theAcct < 0 || theAcct >= theUser.numAccounts()) {
                System.out.println("Invalid account. Please try again.");
            }

        }while (theAcct < 0 || theAcct >= theUser.numAccounts());

        // Print the transaction History
        theUser.printAcctTransHistory(theAcct);
    }

    /**
     * Transfer amount between 2 accounts owned by the same user.
     * @param theUser   the logged-in user.
     * @param sc        the Scanner used for user input.
     */

    public static void transferFunds(User theUser, Scanner sc) {

        // inits
        int fromAcct;
        int toAcct;
        double amount;
        double acctBal;

        // get the account to transfer FROM
        do {
            System.out.printf("Enter the account number (1 - %d) you want to transfer FROM: ", theUser.numAccounts());

            fromAcct = sc.nextInt() -1;

            if (fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
                System.out.println("Invalid account. Please try again.");
            }

        }while (fromAcct < 0 || fromAcct >= theUser.numAccounts());

        acctBal = theUser.getAcctBalance(fromAcct);

        // get the account to transfer TO
        do {
            System.out.printf("Enter the account number (1 - %d) you want to transfer TO: ", theUser.numAccounts());

            toAcct = sc.nextInt() -1;

            if (toAcct < 0 || toAcct >= theUser.numAccounts()) {
                System.out.println("Invalid account. Please try again.");
            }

        }while (toAcct < 0 || toAcct >= theUser.numAccounts());

        //get amount to transfer

        do {
            System.out.printf("Enter the amount to transfer (max R%.02f): R", acctBal);
            amount = sc.nextDouble();
            if(amount < 0) {
                System.out.println("Amount not valid. Must be greater than 0.");
            }

            else if(amount > acctBal) {
                System.out.println("Amount not valid. Needs to be less or equal to available funds.");
            }

        }while (amount < 0 || amount > acctBal);

        // do the Transfer
        theUser.addAcctTransaction(fromAcct, -1 * amount, String.format("Transfer to account %s", theUser.getAcctUUID(toAcct)));
        theUser.addAcctTransaction(toAcct, amount, String.format("Transfer from account %s", theUser.getAcctUUID(fromAcct)));
    }

    /**
     * Withdraw funds from an account
     * @param theUser   the Logged-in user
     * @param sc        the Scanner used for user input
     */

    public static void withdrawFunds(User theUser, Scanner sc) {

        // inits
        int fromAcct;
        double amount;
        double acctBal;
        String memo;

        // get the account to withdraw FROM
        do {
            System.out.printf("Enter the account number (1 - %d) you want to withdraw FROM: ", theUser.numAccounts());

            fromAcct = sc.nextInt() - 1;

            if (fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
                System.out.println("Invalid account. Please try again.");
            }

        } while (fromAcct < 0 || fromAcct >= theUser.numAccounts());

        acctBal = theUser.getAcctBalance(fromAcct);
        //get amount to withdraw

        do {
            System.out.printf("Enter the amount to withdraw (max R%.02f): R", acctBal);
            amount = sc.nextDouble();
            if (amount < 0) {
                System.out.println("Amount not valid. Must be greater than 0.");
            } else if (amount > acctBal) {
                System.out.println("Amount not valid. Needs to be less or equal to available funds.");
            }

        } while (amount < 0 || amount > acctBal);

        // gobble up rest of previous input
        sc.nextLine();

        // get a memo
        System.out.println(" Enter a memo: ");
        memo = sc.nextLine();

        //do the withdrawal
        theUser.addAcctTransaction(fromAcct, -1 * amount, memo);
    }

    /**
     * Deposit funds to an account
     * @param theUser   the Logged-in user
     * @param sc        the Scanner used for user input
     */

    public static void depositFunds(User theUser, Scanner sc) {

        // inits
        int toAcct;
        double amount;
        double acctBal;
        String memo;

        // get the account to Deposit TO
        do {
            System.out.printf("Enter the account number (1 - %d) you want to Deposit TO: ", theUser.numAccounts());

            toAcct = sc.nextInt() - 1;

            if (toAcct < 0 || toAcct >= theUser.numAccounts()) {
                System.out.println("Invalid account. Please try again.");
            }

        } while (toAcct < 0 || toAcct >= theUser.numAccounts());

        acctBal = theUser.getAcctBalance(toAcct);
        //get amount to deposit

        do {
            System.out.printf("Enter the amount to deposit (max R%.02f): R", acctBal);
            amount = sc.nextDouble();
            if (amount < 0) {
                System.out.println("Amount not valid. Must be greater than 0.");
            }

        } while (amount < 0);

        // gobble up rest of previous input
        sc.nextLine();

        // get a memo
        System.out.println(" Enter a memo: ");
        memo = sc.nextLine();

        //do the deposit
        theUser.addAcctTransaction(toAcct, amount, memo);
    }
}
