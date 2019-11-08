/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */

/**
 * @author AD
 * @version $Id: ATM.java, v 0.1 2019‐11‐06 9:23 AM AD Exp $$
 */
public class ATM {

    String name;
    String pin;
    int balance;
    String accountNumber;


    /**
     * Getter method for property name.
     *
     * @return property value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method for property name.
     *
     * @param name value to be assigned to property name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter method for property pin.
     *
     * @return property value of pin
     */
    public String getPin() {
        return pin;
    }

    /**
     * Setter method for property pin.
     *
     * @param pin value to be assigned to property pin
     */
    public void setPin(String pin) {
        this.pin = pin;
    }

    /**
     * Getter method for property balance.
     *
     * @return property value of balance
     */
    public int getBalance() {
        return balance;
    }

    /**
     * Setter method for property balance.
     *
     * @param balance value to be assigned to property balance
     */
    public void setBalance(int balance) {
        this.balance = balance;
    }

    /**
     * Getter method for property accountNumber.
     *
     * @return property value of accountNumber
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * Setter method for property accountNumber.
     *
     * @param accountNumber value to be assigned to property accountNumber
     */
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public ATM(String name, String pin, int balance, String accountNumber) {
        this.name = name;
        this.pin = pin;
        this.balance = balance;
        this.accountNumber = accountNumber;
    }

}
