// BankAccount class
class BankAccount {
    private String accountHolderName;
    private double balance;

    public BankAccount(String accountHolderName, double initialBalance) {
        this.accountHolderName = accountHolderName;
        this.balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Successfully deposited: $" + amount);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Successfully withdrew: $" + amount);
        } else if (amount > balance) {
            System.out.println("Insufficient balance!");
        } else {
            System.out.println("Invalid withdrawal amount.");
        }
    }
}

class ATM {
    private BankAccount bankAccount;

    public ATM(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public void displayMenu() {
        System.out.println("Welcome to the ATM");
        System.out.println("1. Check Balance");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Exit");
    }

    public void handleUserChoice(int choice) {
        switch (choice) {
            case 1:
                System.out.println("Your balance is: $" + bankAccount.getBalance());
                break;
            case 2:
                System.out.print("Enter deposit amount: ");
                double depositAmount = new java.util.Scanner(System.in).nextDouble();
                bankAccount.deposit(depositAmount);
                break;
            case 3:
                System.out.print("Enter withdrawal amount: ");
                double withdrawAmount = new java.util.Scanner(System.in).nextDouble();
                bankAccount.withdraw(withdrawAmount);
                break;
            case 4:
                System.out.println("Thank you for using the ATM. Goodbye!");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
}

public class ATMInterface {
    public static void main(String[] args) {
        BankAccount account = new BankAccount("John Doe", 1000.00);
        ATM atm = new ATM(account);
        java.util.Scanner scanner = new java.util.Scanner(System.in);

        while (true) {
            atm.displayMenu();
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            if (choice == 4) break;
            atm.handleUserChoice(choice);
        }
        scanner.close();
    }
}
