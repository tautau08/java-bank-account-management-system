package ATMBankManager;

public interface iBank {
    String getNewUserUUID();
    String getNewAccountUUID();
    void addAccount(iAccount anAcct);
    User addUser(String firstName, String lastName, String pin);
    User userLogin(String userID, String pin);
    String getName();
     void addAccount(Account anAcct) ;
}
