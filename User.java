package ATMBankManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class User implements iuser {
    private String firstName;
    private String lastName;
    private String uuid;
    private byte[] pinHash;
    private ArrayList<Account> accounts;

    public User(String firstName, String lastName, String pin, Bank theBank) {
        this.firstName = firstName;
        this.lastName = lastName;

        // Extracted pin hashing to a method for better organization and reusability.
        this.pinHash = hashPin(pin);

        this.uuid = theBank.getNewUserUUID();
        this.accounts = new ArrayList<>();

        // Logging can be extracted to a separate logging service for better separation of concerns.
        System.out.printf("New user %s, %s with ID %s created.\n", lastName, firstName, this.uuid);
    }

    // Extracted pin hashing logic to a private method.
    private byte[] hashPin(String pin) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return md.digest(pin.getBytes());
        } catch (NoSuchAlgorithmException e) {
            handleNoSuchAlgorithmException(e);
        }
        return new byte[0];
    }

    private void handleNoSuchAlgorithmException(NoSuchAlgorithmException e) {
        System.err.println("error: caught NoSuchAlgorithmException");
        e.printStackTrace();
        System.exit(1);
    }

    public void addAccount(Account anAcct) {
        this.accounts.add(anAcct);
    }

    public String getUUID() {
        return this.uuid;
    }

    public boolean validatePin(String aPin) {
        byte[] inputPinHash = hashPin(aPin);
        return MessageDigest.isEqual(inputPinHash, this.pinHash);
    }

    public String getFirstName() {
        return this.firstName;
    }

    // Consider using a StringBuilder for building the summary for better performance.
    public void printAccountsSummary() {
        StringBuilder summary = new StringBuilder("\n\n" + this.firstName + "'s accounts summary\n");
        for (int i = 0; i < this.accounts.size(); i++) {
            summary.append(String.format(" %d) %s\n", i + 1, this.accounts.get(i).getSummaryLine()));
        }
        System.out.println(summary.toString());
    }

    public int numAccounts() {
        return this.accounts.size();
    }

    public void printAcctTransHistory(int acctIdx) {
        this.accounts.get(acctIdx).printAcctTransHistory();
    }

    public double getAcctBalance(int acctIdx) {
        return this.accounts.get(acctIdx).getBalance();
    }

    public String getAcctUUID(int acctIdx) {
        return this.accounts.get(acctIdx).getUUID();
    }

    public void addAcctTransaction(int acctIdx, double amount, String memo) {
        this.accounts.get(acctIdx).addTransaction(amount, memo);
    }
}
