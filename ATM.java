package ATMBankManager;

import java.util.Scanner;

public class ATM {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Bank bank = new Bank("Bank of Tom");
        User user = initializeUser(bank);

        while (true) {
            User authenticatedUser = login(bank, scanner);
            if (authenticatedUser != null) {
                displayUserMenu(authenticatedUser, scanner);
            }
        }
    }

    private static User initializeUser(Bank bank) {
        User user = bank.addUser("John", "Doe", "1234");
        Account checkingAccount = new Account("Checking", user, bank);
        user.addAccount(checkingAccount);
        bank.addAccount(checkingAccount);
        return user;
    }

    private static User login(Bank bank, Scanner scanner) {
        String userID;
        String pin;
        User authUser;

        do {
            System.out.printf("\n\nWelcome to %s\n\n", bank.getName());
            System.out.print("Enter user ID: ");
            userID = scanner.nextLine();
            System.out.print("Enter pin: ");
            pin = scanner.nextLine();

            authUser = bank.userLogin(userID, pin);
            if (authUser == null) {
                System.out.println("Incorrect user ID and/or Pin entered. Try Again.");
            }

        } while(authUser == null);

        return authUser;
    }

    private static void displayUserMenu(User user, Scanner scanner) {
        int choice;

        do {
            System.out.printf("Welcome %s, what action would you like to take?\n", user.getFirstName());
            System.out.println(" 1) Show Account Transaction history.");
            System.out.println(" 2) Make a Withdrawal.");
            System.out.println(" 3) Make a Deposit.");
            System.out.println(" 4) Transfer.");
            System.out.println(" 5) QUIT.");
            System.out.println();
            System.out.print("Enter your choice (1-5): ");
            choice = scanner.nextInt();

            if (choice < 1 || choice > 5) {
                System.out.println("Invalid input. Please input 1-5.");
            }
        } while(choice < 1 || choice > 5);

        switch (choice) {
            case 1 -> showTransactionHistory(user, scanner);
            case 2 -> withdrawFunds(user, scanner);
            case 3 -> depositFunds(user, scanner);
            case 4 -> transferFunds(user, scanner);
            case 5 -> scanner.nextLine(); // Clear the buffer
            default -> {
                System.out.println("Invalid input. Please input 1-5.");
                displayUserMenu(user, scanner);
            }
        }

        if (choice != 5) {
            displayUserMenu(user, scanner);
        }
    }

    private static void showTransactionHistory(User user, Scanner scanner) {
        int accountIndex;
        do {
            System.out.printf("Enter the number (1-%d) of the account for which you would like to view transactions: ",
                    user.numAccounts());
            accountIndex = scanner.nextInt() - 1;
            if (accountIndex < 0 || accountIndex >= user.numAccounts()) {
                System.out.println("Invalid Account. Please try again.");
            }
        } while (accountIndex < 0 || accountIndex >= user.numAccounts());

        user.printAcctTransHistory(accountIndex);
    }

    private static void transferFunds(User user, Scanner scanner) {
        int fromAccountIndex, toAccountIndex;
        double amount, fromAccountBalance;

        do {
            System.out.printf("Enter the number (1-%d) of the account to transfer from: ", user.numAccounts());
            fromAccountIndex = scanner.nextInt() - 1;
            if (fromAccountIndex < 0 || fromAccountIndex >= user.numAccounts()) {
                System.out.println("Invalid Account. Please try again.");
            }
        } while (fromAccountIndex < 0 || fromAccountIndex >= user.numAccounts());
        fromAccountBalance = user.getAcctBalance(fromAccountIndex);

        do {
            System.out.printf("Enter the number (1-%d) of the account to transfer to: ", user.numAccounts());
            toAccountIndex = scanner.nextInt() - 1;
            if (toAccountIndex < 0 || toAccountIndex >= user.numAccounts()) {
                System.out.println("Invalid Account. Please try again.");
            }
        } while (toAccountIndex < 0 || toAccountIndex >= user.numAccounts());

        do {
            System.out.printf("Enter the amount to Transfer (max. £%.02f): £", fromAccountBalance);
            amount = scanner.nextDouble();
            if (amount < 0) {
                System.out.println("Amount must be greater than 0.");
            } else if (amount > fromAccountBalance) {
                System.out.printf("Amount must not be greater than balance of £%.02f.\n", fromAccountBalance);
            }
        } while (amount < 0 || amount > fromAccountBalance);

        user.addAcctTransaction(fromAccountIndex, -1 * amount,
                String.format("Transfer to Account %s", user.getAcctUUID(toAccountIndex)));
        user.addAcctTransaction(toAccountIndex, amount,
                String.format("Transfer from Account %s", user.getAcctUUID(fromAccountIndex)));
    }

    private static void withdrawFunds(User user, Scanner scanner) {
        int fromAccountIndex;
        double amount, fromAccountBalance;
        String memo;

        do {
            System.out.printf("Enter the number (1-%d) of the account to Withdraw from: ", user.numAccounts());
            fromAccountIndex = scanner.nextInt() - 1;
            if (fromAccountIndex < 0 || fromAccountIndex >= user.numAccounts()) {
                System.out.println("Invalid Account. Please try again.");
            }
        } while (fromAccountIndex < 0 || fromAccountIndex >= user.numAccounts());
        fromAccountBalance = user.getAcctBalance(fromAccountIndex);

        do {
            System.out.printf("Enter the amount to Withdraw (max. £%.02f): £", fromAccountBalance);
            amount = scanner.nextDouble();
            if (amount < 0) {
                System.out.println("Amount must be greater than 0.");
            } else if (amount > fromAccountBalance) {
                System.out.printf("Amount must not be greater than balance of £%.02f.\n", fromAccountBalance);
            }
        } while (amount < 0 || amount > fromAccountBalance);

        scanner.nextLine(); // Clear the buffer
        System.out.print("Enter a memo: ");
        memo = scanner.nextLine();

        user.addAcctTransaction(fromAccountIndex, -1 * amount, memo);
    }

    private static void depositFunds(User user, Scanner scanner) {
        int toAccountIndex;
        double amount;
        String memo;

        do {
            System.out.printf("Enter the number (1-%d) of the account to deposit into: ", user.numAccounts());
            toAccountIndex = scanner.nextInt() - 1;
            if (toAccountIndex < 0 || toAccountIndex >= user.numAccounts()) {
                System.out.println("Invalid Account. Please try again.");
            }
        } while (toAccountIndex < 0 || toAccountIndex >= user.numAccounts());

        do {
            System.out.print("Enter the amount to Deposit: £");
            amount = scanner.nextDouble();
            if (amount < 0) {
                System.out.println("Amount must be greater than 0.");
            }
        } while (amount < 0);

        scanner.nextLine(); // Clear the buffer
        System.out.print("Enter a memo: ");
        memo = scanner.nextLine();

        user.addAcctTransaction(toAccountIndex, amount, memo);
    }
}
