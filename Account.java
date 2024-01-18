package ATMBankManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Account implements iAccount {
    private String name;
    private String uuid;
    private User holder;
    private List<Transaction> transactions;

    public Account(String name, User holder, Bank theBank) {
        this.name = name;
        this.holder = holder;
        this.uuid = theBank.getNewAccountUUID();
        this.transactions = new ArrayList<>();
    }

    public String getUUID() {
        return uuid;
    }

    public String getSummaryLine() {
        double balance = getBalance();
        return String.format("%s : £%.02f : %s", uuid, balance, name);
    }

    public double getBalance() {
        return transactions.stream().mapToDouble(Transaction::getAmount).sum();
    }

    public void printAcctTransHistory() {
        System.out.printf("\nTransaction history for account %s\n", uuid);
        for (Transaction transaction : transactions) {
            System.out.println(transaction.getSummaryLine());
        }
        System.out.println();
    }

    public void addTransaction(double amount, String memo) {
        Transaction newTrans = new Transaction(amount, memo, this);
        transactions.add(newTrans);
    }
    
    public List<Transaction> getTransactions() {
        List<Transaction> copy = new ArrayList<>(transactions);
        Collections.reverse(copy);
        return copy;
    }
}
//refactored