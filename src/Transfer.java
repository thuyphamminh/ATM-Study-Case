/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2019 All Rights Reserved.
 */

/**
 * @author AD
 * @version $Id: Transfer.java, v 0.1 2019‐11‐13 9:04 AM AD Exp $$
 */
public class Transfer {

    String fromAccount;
    String toAccount;
    int amount;
    String createdDate;

    public Transfer(String fromAccount, String toAccount, int amount, String createdDate) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.createdDate = createdDate;
    }

    /**
     * Getter method for property fromAccount.
     *
     * @return property value of fromAccount
     */
    public String getFromAccount() {
        return fromAccount;
    }

    /**
     * Setter method for property fromAccount.
     *
     * @param fromAccount value to be assigned to property fromAccount
     */
    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount;
    }

    /**
     * Getter method for property toAccount.
     *
     * @return property value of toAccount
     */
    public String getToAccount() {
        return toAccount;
    }

    /**
     * Setter method for property toAccount.
     *
     * @param toAccount value to be assigned to property toAccount
     */
    public void setToAccount(String toAccount) {
        this.toAccount = toAccount;
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
