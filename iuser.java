package ATMBankManager;

public interface iuser {
    String getUUID();
    boolean validatePin(String pin);
    String getFirstName();
    // Add other user-related methods
}

