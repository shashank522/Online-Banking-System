import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Account {
    private String username;
    private String password;
    private double balance;

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
        this.balance = 0.0;
    }

    public String getUsername() {
        return username;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposit successful. New balance: " + balance);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawal successful. New balance: " + balance);
        } else {
            System.out.println("Invalid withdrawal amount or insufficient funds.");
        }
    }

    public void transfer(Account toAccount, double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            toAccount.balance += amount;
            System.out.println("Transfer successful. New balance: " + balance);
        } else {
            System.out.println("Invalid transfer amount or insufficient funds.");
        }
    }
}

class BankSystem {
    private Map<String, Account> accounts = new HashMap<>();
    private Account loggedInAccount;

    public void register(String username, String password) {
        if (!accounts.containsKey(username)) {
            accounts.put(username, new Account(username, password));
            System.out.println("Registration successful.");
        } else {
            System.out.println("Username already exists.");
        }
    }

    public boolean login(String username, String password) {
        Account account = accounts.get(username);
        if (account != null && account.checkPassword(password)) {
            loggedInAccount = account;
            System.out.println("Login successful.");
            return true;
        } else {
            System.out.println("Invalid username or password.");
            return false;
        }
    }

    public void deposit(double amount) {
        if (loggedInAccount != null) {
            loggedInAccount.deposit(amount);
        } else {
            System.out.println("Please login first.");
        }
    }

    public void withdraw(double amount) {
        if (loggedInAccount != null) {
            loggedInAccount.withdraw(amount);
        } else {
            System.out.println("Please login first.");
        }
    }

    public void transfer(String toUsername, double amount) {
        if (loggedInAccount != null) {
            Account toAccount = accounts.get(toUsername);
            if (toAccount != null) {
                loggedInAccount.transfer(toAccount, amount);
            } else {
                System.out.println("Recipient account not found.");
            }
        } else {
            System.out.println("Please login first.");
        }
    }

    public void logout() {
        loggedInAccount = null;
        System.out.println("Logged out.");
    }
}

public class OnlineBankingSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BankSystem bankSystem = new BankSystem();
        boolean exit = false;

        while (!exit) {
            System.out.println("\nOnline Banking System");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Deposit");
            System.out.println("4. Withdraw");
            System.out.println("5. Transfer");
            System.out.println("6. Logout");
            System.out.println("7. Exit");

            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();
                    bankSystem.register(username, password);
                    break;
                case 2:
                    System.out.print("Enter username: ");
                    username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    password = scanner.nextLine();
                    bankSystem.login(username, password);
                    break;
                case 3:
                    System.out.print("Enter amount to deposit: ");
                    double depositAmount = scanner.nextDouble();
                    bankSystem.deposit(depositAmount);
                    break;
                case 4:
                    System.out.print("Enter amount to withdraw: ");
                    double withdrawAmount = scanner.nextDouble();
                    bankSystem.withdraw(withdrawAmount);
                    break;
                case 5:
                    System.out.print("Enter recipient username: ");
                    String toUsername = scanner.nextLine();
                    System.out.print("Enter amount to transfer: ");
                    double transferAmount = scanner.nextDouble();
                    bankSystem.transfer(toUsername, transferAmount);
                    break;
                case 6:
                    bankSystem.logout();
                    break;
                case 7:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }

        scanner.close();
    }
}