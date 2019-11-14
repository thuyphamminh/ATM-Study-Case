import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Main {

    public static Scanner scanner = new Scanner(System.in);
    public static ArrayList<Account> accountList = new ArrayList<Account>();
    public static int index = -1;
    public static boolean hasTran = false;

    public static void main(String[] args)  throws IOException{
        if (readFile() != null){
            welcome();
        }
        String content="";
        if (hasTran) {
            BufferedWriter bw = new BufferedWriter(new FileWriter("accounts.csv"));
            for (Account acc : accountList) {
                content += acc.getName() + "," + acc.getPin() + "," + acc.getBalance() + "," + acc.getAccountNumber() + "\n";
            }
            bw.write(content);
            bw.close();
        }
    }

    public static ArrayList<Account> readFile() throws IOException {
        BufferedReader csvReader = new BufferedReader(new FileReader("accounts.csv"));
        String row = "";
        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(",");
            try {
                int balance = Integer.parseInt(data[2]);
                if (checkValidAccount(data[3]) == -1) {
                    Account atm = new Account(data[0], data[1], balance, data[3]);
                    accountList.add(atm);
                } else {
                    System.out.println("Record duplicated");
                    return null;
                }
            } catch(Exception e){
            }
        }
        csvReader.close();
        return accountList;
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
        if (validate(accountNumber) == 1){
            System.out.println(code + " should only contains numbers");
            checkFormat(code);
        } else if (validate(accountNumber) == 2){
            System.out.println(code + " should have 6 digits length");
            checkFormat(code);
        }
        return accountNumber;
    }

    public static int validate(String format){
        if (format.length() == 6) {
            try {
                Long.parseLong(format);
            } catch (Exception e) {
                return 1;
            }
        } else {
            return 2;
        }
        return 0;
    }

    public static int checkValid(String accountNumber, String pin) {
        for (Account a : accountList) {
            if (a.accountNumber.equals(accountNumber) && a.pin.equals(pin)) {
                return accountList.indexOf(a);
            }
        }
        return -1;
    }

    public static int checkValidAccount(String accountNumber) {
        for (Account a : accountList) {
            if (a.accountNumber.equals(accountNumber)) {
                return accountList.indexOf(a);
            }
        }
        return -1;
    }


    public static void menu() {
        scanner = new Scanner(System.in);
        int option = 0;
        try {
            System.out.print("0. Query Transaction\n1. Withdraw\n2. Fund Transfer\n3. Exit\nPlease choose option[3]:");
            String opt = scanner.nextLine();
            if (opt.isEmpty()) {
                welcome();
            } else {
                option = Integer.parseInt(opt);
                if (option < 0 || option > 3) {
                    menu();
                } else if (option == 1) {
                    withdraw();
                } else if (option == 2) {
                    fundTransfer();
                } else if (option == 0){
                    queryTran();
                }
            }
        } catch (Exception e) {
            menu();
        }

    }

    public static void queryTran(){
        scanner = new Scanner(System.in);
        int option = 0;
        try {
            System.out.print("1. Query Last 10 Transactions\n2. Exit.\nChoose option:");
            String opt = scanner.nextLine();
            if (opt.isEmpty()){
                menu();
            } else {
                option = Integer.parseInt(opt);
                if (option != 2 && option != 1){
                    System.out.println("Option invalid. Please choose again.");
                    queryTran();
                } else if (option == 1){
                    readTran();
                    menu();
                } else {
                    menu();
                }
            }
        } catch(Exception e){
            System.out.println(e);
            System.out.println("Option invalid. Please choose again.");
            queryTran();
        }
    }

    public static void readTran() throws IOException {
        BufferedReader csvReader = new BufferedReader(new FileReader("transaction.csv"));
        String row = "";
        int i = 1;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm aa");
        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(",");
                if (i==11) break;
                if (data[0].equals(accountList.get(index).accountNumber)) {
                    if (data[1].isEmpty()) {
                        System.out.println(i + ". Time:" + data[3] + ". Withdraw amount: " + data[2]);
                    } else {
                        System.out.println(i + ". Time:" + data[3] + ". Transfer to: " + data[1] + ". Amount: " + data[2]);
                    }
                    i++;
                }
        }
        csvReader.close();
        if (i==1) System.out.println("Transaction list is empty.");
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
                    System.out.println("Option invalid. Please choose again.");
                    withdraw();
                } else {
                    int amount = 0;
                    if (option == 1){
                        amount = 10;
                    } else if (option == 2){
                        amount = 50;
                    } else if (option == 3){
                        amount = 100;
                    } else if (option == 4){
                        otherWithdraw();
                    } else if (option == 5){
                        menu();
                    }
                    if (accountList.get(index).getBalance() > amount && amount != 0) {
                        accountList.get(index).setBalance(accountList.get(index).getBalance() - amount);
                        summary(amount);
                    } else {
                        System.out.println("Insufficient balance $"+amount);
                        withdraw();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Option invalid. Please choose again.");
            withdraw();
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
            } else if (amount > accountList.get(index).getBalance()) {
                System.out.println("Insufficient balance $" + amount);
                withdraw();
            } else {
                accountList.get(index).setBalance(accountList.get(index).getBalance() - amount);
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
                                "Destination Account : " + account + "\n" +
                                "Transfer Amount     : $" + amount + "\n" +
                                "Reference Number    : " + ref + "\n" +
                                "\n" +
                                "1. Confirm Trx\n" +
                                "2. Cancel Trx\n" +
                                "Choose option[2]:");
                        try {
                            int opt = scanner.nextInt();
                            if (opt == 1) {
                                accountList.get(tranAcc).setBalance(accountList.get(tranAcc).getBalance() + amount);
                                accountList.get(index).setBalance(accountList.get(index).getBalance() - amount);
                                transferSum(accountList.get(tranAcc).getAccountNumber(), amount, ref);
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

    public static void transferSum(String acc, int amount, long ref) throws IOException {
        scanner = new Scanner(System.in);
        Date date = new Date();
        Transfer tran = new Transfer(accountList.get(index).accountNumber,acc,amount,date.toString());
        writeFile(tran);
        hasTran = true;
        System.out.print("Fund Transfer Summary\n" +
                "Destination Account : " + acc + "\n" +
                "Transfer Amount     : $" + amount + "\n" +
                "Reference Number    : " + ref + "\n" +
                "Balance             : $" + accountList.get(index).getBalance() + "\n" +
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
                } else if (amount > accountList.get(index).getBalance()) {
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

    public static void writeFile(Withdraw with) throws IOException {
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("transaction.csv", true)));
        out.println(with.account+","+""+","+String.valueOf(with.amount)+","+with.createdDate);
        out.close();
    }

    public static void writeFile(Transfer tran) throws IOException {
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("transaction.csv", true)));
        out.println(tran.fromAccount+","+tran.toAccount+","+String.valueOf(tran.amount)+","+tran.createdDate);
        out.close();
    }

    public static void summary(int withdraw) throws IOException{
        scanner = new Scanner(System.in);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm aa");
        Date date = new Date();
        Withdraw with = new Withdraw(accountList.get(index).getAccountNumber(),withdraw,date.toString());
        writeFile(with);
        hasTran=true;
        System.out.print("Summary\n" + formatter.format(date)
                + "\n" +
                "Withdraw : $" + withdraw + "\n" +
                "Balance : $" + accountList.get(index).getBalance() + "\n" +
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
