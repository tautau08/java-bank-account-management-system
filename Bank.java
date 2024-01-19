package ATMBankManager;

import java.util.ArrayList;
import java.util.Random;

public class Bank implements iBank{
    // Bank's details.
    private String name;
    private ArrayList<User> users; // Each element of type 'User', in the ArrayList 'users'.
    private ArrayList<Account> accounts; // Each element of type 'Account', in the ArrayList 'accounts'.

    public Bank(String name) {
        this.name = name;
        this.users = new ArrayList<User>();
        this.accounts = new ArrayList<Account>();
    }


    // "public" = scope of accessibility of this func/method. "String" = returned data type from this function/method.
    // Generate new uuid for user, and check to ensure it doesn't exist in the users list. @return uuid.
    public String getNewUserUUID() {
        
        // Inits.
        String uuid;
        Random rng = new Random();
        int len = 6; // Characters length.
        boolean nonUnique; // A flag.

        // Loop until nonUnique flag is true.
        do {
            // Generate number.
            uuid = "";
            for (int i = 0; i < len; i++) {

                // Generate a number between 0 and <10, cast as integer object (primitive int to non-primitive Integer), to allow access to number methods, such as 'toString()', (since uuid is expecting a String, and we're generating a number to append to uuid).
                uuid += ((Integer)rng.nextInt(10)).toString();
            }

            // Check uuid is unique.
            nonUnique = false;
            // Iterate through this class' 'users' ArrayList, through all the elements of type 'User', storing the current interated element in 'u'.
            for (User u: this.users) {
                if (uuid.compareTo(u.getUUID()) == 0) {
                    nonUnique = true;
                    break;
                }
            }
        } while (nonUnique); // loop to do once, check condition, and then decide if to iterate again.

        return uuid;
    }


    public String getNewAccountUUID() {
                
        // Inits.
        String uuid;
        Random rng = new Random();
        int len = 10; // Characters length.
        boolean nonUnique; // A flag.

        // Loop until nonUnique flag is true.
        do {
            // Generate number.
            uuid = "";
            for (int i = 0; i < len; i++) {
                // Generate a number between 0 and <10, cast as integer object (primitive int to non-primitive Integer), to allow access to number methods, such as 'toString()', (since uuid is expecting a String, and we're generating a number to append to uuid).
                uuid += ((Integer)rng.nextInt(10)).toString();
            }

            // Check uuid is unique.
            nonUnique = false;
            // Iterate through this class' 'accounts' ArrayList, through all the elements of type 'Account', storing the current interated element into 'a'.
            for (Account a: this.accounts) {
                if (uuid.compareTo(a.getUUID()) == 0) {
                    nonUnique = true;
                    break;
                }
            }
        } while (nonUnique); // loop to do once, check condition, and then decide if to iterate again.

        return uuid;
    }

    @Override
    public void addAccount(iAccount anAcct) {

    }


    public void addAccount(Account anAcct) {
        this.accounts.add(anAcct);
    }


    public User addUser(String firstName, String lastName, String pin) {
        User newUser = new User(firstName, lastName, pin, this); // 'this' is the Bank object.
        this.users.add(newUser);

        // Create a default savings account for the new user.
        Account newAccount = new Account("Savings", newUser, this); // 'this' is the Bank object.

        // Add to holder/user list and bank list. Add the same Account (not copies of Account) we're currently making, to accounts lists for Bank and User classes.
        newUser.addAccount(newAccount);
        this.addAccount(newAccount);

        return newUser;
    }


    public User userLogin(String userID, String pin) {
        
        // Search users list for userID.
        for (User u: this.users) {
            // Check userID is correct.
            if (u.getUUID().compareTo(userID) == 0 && u.validatePin(pin)) {
                return u; // return user with correct id and pin provided.
            }
        }
        return null; // return null is userID or Pin is incorrect.
    }


    public String getName() {
        return this.name;
    }
}