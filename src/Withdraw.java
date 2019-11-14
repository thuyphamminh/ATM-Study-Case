/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */

/**
 * @author AD
 * @version $Id: Withdraw.java, v 0.1 2019‐11‐13 9:04 AM AD Exp $$
 */
public class Withdraw {

    String account;
    int amount;
    String createdDate;

    public Withdraw(String account, int amount, String createdDate) {
        this.account = account;
        this.amount = amount;
        this.createdDate = createdDate;
    }

    /**
     * Getter method for property account.
     *
     * @return property value of account
     */
    public String getAccount() {
        return account;
    }

    /**
     * Setter method for property account.
     *
     * @param account value to be assigned to property account
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * Getter method for property amount.
     *
     * @return property value of amount
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Setter method for property amount.
     *
     * @param amount value to be assigned to property amount
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * Getter method for property createdDate.
     *
     * @return property value of createdDate
     */
    public String getCreatedDate() {
        return createdDate;
    }

    /**
     * Setter method for property createdDate.
     *
     * @param createdDate value to be assigned to property createdDate
     */
    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

}
