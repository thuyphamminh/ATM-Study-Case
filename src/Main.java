import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.PropertyPermission;
import java.util.Scanner;

public class Main {

    public static Scanner scanner = new Scanner(System.in);
    public static ArrayList<ATM> atmList = new ArrayList<ATM>();
    public static int index = -1;

    public static void main(String[] args) {
        ATM atm1 = new ATM("John Doe", "012108", 100, "112233");
        ATM atm2 = new ATM("Jane Doe", "932012", 30, "112244");
        atmList.add(atm1);
        atmList.add(atm2);
        welcome();
    }

    public static void welcome() {
        scanner = new Scanner(System.in);
        String accountNumber = checkFormat("Account Number");
        String pin = checkFormat("PIN");
        index = checkValid(accountNumber, pin);
        if (index == -1) {
            System.out.println("Invalid Account Number/PIN");
            welcome();
        } else {
            menu();
        }
    }

    public static String checkFormat(String code) {
        scanner = new Scanner(System.in);
        String accountNumber;
        System.out.print("Enter " + code + ": ");
        accountNumber = scanner.nextLine();
        if (accountNumber.length() == 6) {
            try {
                Long.parseLong(accountNumber);
            } catch (Exception e) {
                System.out.println(code + " should only contains numbers");
                checkFormat(code);
            }
        } else {
            System.out.println(code + " should have 6 digits length");
            checkFormat(code);
        }
        return accountNumber;
    }

    public static int checkValid(String accountNumber, String pin) {
        for (ATM a : atmList) {
            if (a.accountNumber.equals(accountNumber) && a.pin.equals(pin)) {
                return atmList.indexOf(a);
            }
        }
        return -1;
    }

    public static int checkValidAccount(String accountNumber) {
        for (ATM a : atmList) {
            if (a.accountNumber.equals(accountNumber)) {
                return atmList.indexOf(a);
            }
        }
        return -1;
    }


    public static void menu() {
        scanner = new Scanner(System.in);
        int option = 0;
        try {
            System.out.print("1. Withdraw\n2. Fund Transfer\n3. Exit\nPlease choose option[3]:");
            String opt = scanner.nextLine();
            if (opt.isEmpty()) {
                welcome();
            } else {
                option = Integer.parseInt(opt);
                if (option < 1 || option > 3) {
                    menu();
                } else if (option == 1) {
                    withdraw();
                } else if (option == 2) {
                    fundTransfer();
                }
            }
        } catch (Exception e) {
            menu();
        }

    }

    public static void withdraw() {
        scanner = new Scanner(System.in);
        int option = 0;
        try {
            System.out.print("1. $10\n2. $50\n3. $100\n4. Other\n5. Back\nPlease choose option[5]:");
            String opt = scanner.nextLine();
            if (opt.isEmpty()) {
                menu();
            } else {
                option = Integer.parseInt(opt);
                if (option < 1 || option > 5) {
                    withdraw();
                } else {
                    switch (option) {
                        case 1:
                            if (atmList.get(index).getBalance() > 10) {
                                atmList.get(index).setBalance(atmList.get(index).getBalance() - 10);
                                summary(10);
                            } else {
                                System.out.println("Insufficient balance $10");
                                withdraw();
                            }
                        case 2:
                            if (atmList.get(index).getBalance() > 50) {
                                atmList.get(index).setBalance(atmList.get(index).getBalance() - 50);
                                summary(50);
                            } else {
                                System.out.println("Insufficient balance $50");
                                withdraw();
                            }
                        case 3:
                            if (atmList.get(index).getBalance() > 100) {
                                atmList.get(index).setBalance(atmList.get(index).getBalance() - 100);
                                summary(100);
                            } else {
                                System.out.println("Insufficient balance $100");
                                withdraw();
                            }
                        case 4:
                            otherWithdraw();
                        case 5:
                            atmList.get(index).setBalance(atmList.get(index).getBalance() - 10);
                            menu();
                    }
                }
            }
        } catch (Exception e) {
        }

    }

    public static void otherWithdraw() {
        System.out.println("Other Withdraw");
        int amount = 0;
        try {
            System.out.print("Enter amount to withdraw:");
            amount = scanner.nextInt();
            if (amount > 1000) {
                System.out.println("Maximum amount to withdraw is $1000");
                otherWithdraw();
            } else if (amount == 0 || amount % 10 != 0) {
                System.out.println("Invalid ammount");
                otherWithdraw();
            } else if (amount > atmList.get(index).getBalance()) {
                System.out.println("Insufficient balance $" + amount);
                withdraw();
            } else {
                atmList.get(index).setBalance(atmList.get(index).getBalance() - amount);
                summary(amount);
            }
        } catch (Exception e) {
            System.out.println("Invalid ammount");
            withdraw();
        }
    }

    public static void fundTransfer() {
        scanner = new Scanner(System.in);
        System.out.print("Please enter destination account and press enter to continue or \n" +
                "press enter to go back to Transaction: ");
        String account = scanner.nextLine();
        if (account.isEmpty()) {
            menu();
        } else {
            try {
                long acc = Long.parseLong(account);
                int tranAcc = checkValidAccount(account);
                if (tranAcc == -1) {
                    System.out.println("Invalid account");
                    fundTransfer();
                } else {
                    int amount = transferAmount();
                    long ref = transferRef();
                    if (ref != 0) {
                        System.out.print("Transfer Confirmation\n" +
                                "Destination Account : " + atmList.get(tranAcc).getAccountNumber() + "\n" +
                                "Transfer Amount     : $" + amount + "\n" +
                                "Reference Number    : " + ref + "\n" +
                                "\n" +
                                "1. Confirm Trx\n" +
                                "2. Cancel Trx\n" +
                                "Choose option[2]:");
                        try {
                            int opt = scanner.nextInt();
                            if (opt == 1) {
                                atmList.get(tranAcc).setBalance(atmList.get(tranAcc).getBalance() + amount);
                                atmList.get(index).setBalance(atmList.get(index).getBalance() - amount);
                                transferSum(atmList.get(tranAcc).getAccountNumber(), amount, ref);
                            } else {
                                welcome();
                            }
                        } catch (Exception e) {
                        }
                        ;
                    }
                }

            } catch (Exception e) {
                System.out.println("Invalid account");
                fundTransfer();
            }

        }
    }

    public static void transferSum(String acc, int amount, long ref) {
        scanner = new Scanner(System.in);
        System.out.print("Fund Transfer Summary\n" +
                "Destination Account : " + acc + "\n" +
                "Transfer Amount     : $" + amount + "\n" +
                "Reference Number    : " + ref + "\n" +
                "Balance             : $" + atmList.get(index).getBalance() + "\n" +
                "\n" +
                "1. Transaction\n" +
                "2. Exit\n" +
                "Choose option[2]:");
        String opt = scanner.nextLine();
        if (opt.isEmpty()) {
            welcome();
        } else {
            try {
                int option = Integer.parseInt(opt);
                if (option == 1) {
                    fundTransfer();
                }
            } catch (Exception e) {}
        }
    }

    public static long transferRef() {
        scanner = new Scanner(System.in);
        System.out.print("Reference Number: (This is an autogenerated random 6 digits number)\n" +
                "press enter to continue");
        String opt = scanner.nextLine();
        long ref = 0;
        if (!opt.isEmpty()) {
            transferRef();
        }
        ref = getRandomIntegerBetweenRange(100000, 999999);
        return ref;
    }


    public static int getRandomIntegerBetweenRange(int min, int max) {
        int x = (int) (Math.random() * ((max - min) + 1)) + min;
        return x;
    }

    public static int transferAmount() {
        scanner = new Scanner(System.in);
        System.out.print("Please enter transfer amount and \n" +
                "press enter to continue or \n" +
                "press enter to go back to Transaction: ");
        String amo = scanner.nextLine();
        if (!amo.isEmpty()) {
            try {
                int amount = Integer.parseInt(amo);
                if (amount < 1) {
                    System.out.println("Minimum amount to withdraw is $1");
                    transferAmount();
                } else if (amount > 1000) {
                    System.out.println("Maximum amount to withdraw is $1000");
                    transferAmount();
                } else if (amount > atmList.get(index).getBalance()) {
                    System.out.println("Insufficient balance $" + amount);
                    transferAmount();
                } else {
                    return amount;
                }
            } catch (Exception e) {
                System.out.println("Invalid amount");
                transferAmount();
            }
        } else {
            fundTransfer();
        }
        return 0;
    }

    public static void summary(int withdraw) {
        scanner = new Scanner(System.in);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm aa");
        Date date = new Date();
        System.out.print("Summary\n" + formatter.format(date)
                + "\n" +
                "Withdraw : $" + withdraw + "\n" +
                "Balance : $" + atmList.get(index).getBalance() + "\n" +
                "\n" +
                "1. Transaction \n" +
                "2. Exit\n" +
                "Choose option[2]:");
        try {
            int option = scanner.nextInt();
            if (option == 1) {
                menu();
            } else {
                welcome();
            }
        } catch (Exception e) {
        }
    }

}
