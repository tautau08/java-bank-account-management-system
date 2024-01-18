package ATMBankManager;

public interface iAccount {
    String getUUID();
    String getSummaryLine();
    double getBalance();
    void printAcctTransHistory();
    void addTransaction(double amount, String memo);
}
