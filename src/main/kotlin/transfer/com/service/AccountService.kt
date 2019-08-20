package transfer.com.service

import io.javalin.http.Handler

/**
 * Interface representing actions available for business purposes
 */
interface AccountService {

    /**
     * Method uses to find an account
     * Parameter : take directly the http context
     * Result : if successful return a json representing the Account
     */
    fun getAccount(): Handler
    /**
     * Method uses to list all accounts available
     * Parameter : take directly the http context
     * Result : return a json representing the Account list
     */
    fun getAllAccounts (): Handler

    /**
     * Method uses to make a deposit on an accounts available
     * Parameter : take directly the http context
     * Result : return a json representing the deposit status
     */
    fun makedeposit () : Handler

    /**
    * Method uses to create an accounts
    * Parameter : take directly the http context
    * Result : return a json representing the creation status
    */
    fun createAccount() : Handler

    /**
     * Method uses to delete an accounts
     * Parameter : take directly the http context
     * Result : return a json representing the deletion status
     */
    fun deleteAcount (): Handler

    /**
     * Method uses to transfer fund between two accounts
     * Parameter : take directly the http context
     * Result : return a json representing the transfer status
     */
    fun moneyTransaction (): Handler


}