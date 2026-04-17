import java.util.Scanner;

// Customer class
class Customer {
    String name;
    String customerId;
    String email;

    Customer(String name, String customerId, String email) {
        this.name = name;
        this.customerId = customerId;
        this.email = email;
    }

    void showCustomerDetails() {
        System.out.println("Customer Name: " + name);
        System.out.println("Customer ID: " + customerId);
        System.out.println("Email: " + email);
    }
}

// Base Account class
class Account {
    int accountNumber;
    double balance;
    Customer customer; // Composition

    Account(int accountNumber, double balance, Customer customer) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.customer = customer;
    }

    void deposit(double amount) {
        balance += amount;
        System.out.println(amount + " deposited. New balance: " + balance);
    }

    void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            System.out.println(amount + " withdrawn. New balance: " + balance);
        } else {
            System.out.println("Insufficient balance!");
        }
    }

    void calculateInterest() {
        System.out.println("Interest calculation not defined for base Account.");
    }

    void showAccountDetails() {
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Balance: " + balance);
        customer.showCustomerDetails();
    }
}

// SavingsAccount class
class SavingsAccount extends Account {
    double interestRate;

    SavingsAccount(int accountNumber, double balance, Customer customer, double interestRate) {
        super(accountNumber, balance, customer);
        this.interestRate = interestRate;
    }

    @Override
    void calculateInterest() {
        double interest = balance * interestRate / 100;
        System.out.println("Savings Account Interest: " + interest);
    }

    @Override
    void showAccountDetails() {
        super.showAccountDetails();
        System.out.println("Account Type: Savings Account");
        System.out.println("Interest Rate: " + interestRate + "%");
    }
}

// CurrentAccount class
class CurrentAccount extends Account {
    double overdraftLimit;

    CurrentAccount(int accountNumber, double balance, Customer customer, double overdraftLimit) {
        super(accountNumber, balance, customer);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    void calculateInterest() {
        System.out.println("Current accounts usually do not earn interest.");
    }

    @Override
    void withdraw(double amount) {
        if (balance + overdraftLimit >= amount) {
            balance -= amount;
            System.out.println(amount + " withdrawn. New balance: " + balance);
        } else {
            System.out.println("Exceeds overdraft limit!");
        }
    }

    @Override
    void showAccountDetails() {
        super.showAccountDetails();
        System.out.println("Account Type: Current Account");
        System.out.println("Overdraft Limit: " + overdraftLimit);
    }
}

// Main class with main method
public class BankManagementSystem {
    public static void main(String[] args) {
        Scanner S = new Scanner(System.in);

        // Create Customers
        Customer c1 = new Customer("Alice", "C101", "alice@mail.com");
        Customer c2 = new Customer("Bob", "C102", "bob@mail.com");

        // Create Accounts
        Account a1 = new SavingsAccount(201, 5000, c1, 5.0);
        Account a2 = new CurrentAccount(202, 10000, c2, 2000);

        // Array of accounts for polymorphism
        Account[] accounts = {a1, a2};

        // Menu-driven console program
        while (true) {
            System.out.println("\n--- Bank Management System ---");
            System.out.println("1. Show Account Details");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Calculate Interest");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = S.nextInt();

            if (choice == 5) {
                System.out.println("Exiting... Thank you!");
                break;
            }

            System.out.print("Select account (0 for Alice, 1 for Bob): ");
            int accIndex = S.nextInt();
            if (accIndex < 0 || accIndex >= accounts.length) {
                System.out.println("Invalid account!");
                continue;
            }

            Account selectedAcc = accounts[accIndex];

            switch (choice) {
                case 1:
                    selectedAcc.showAccountDetails();
                    break;
                case 2:
                    System.out.print("Enter deposit amount: ");
                    double dep = S.nextDouble();
                    selectedAcc.deposit(dep);
                    break;
                case 3:
                    System.out.print("Enter withdrawal amount: ");
                    double wd = S.nextDouble();
                    selectedAcc.withdraw(wd);
                    break;
                case 4:
                    selectedAcc.calculateInterest();
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
        S.close();
    }
}