package ATMBankManager;

import java.util.Date;

public class Transaction implements iTransaction {
    private double amount;
    private Date timestamp;
    private String memo;
    private Account inAccount;

    public Transaction(double amount, Account inAccount) {
        initializeTransaction(amount, inAccount);
    }

    public Transaction(double amount, String memo, Account inAccount) {
        initializeTransaction(amount, inAccount);
        this.memo = memo;
    }

    private void initializeTransaction(double amount, Account inAccount) {
        this.amount = amount;
        this.inAccount = inAccount;
        this.timestamp = new Date();
        this.memo = "";
    }

    public double getAmount() {
        return this.amount;
    }

    public String getSummaryLine() {
        String transactionType = (this.amount >= 0) ? "Deposit" : "Withdrawal";
        double absoluteAmount = Math.abs(this.amount);
        return String.format("%s %s: £%.02f : %s", transactionType, this.timestamp.toString(), absoluteAmount, this.memo);
    }
}
