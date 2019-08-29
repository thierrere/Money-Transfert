package transfer.com.service

import io.javalin.http.Handler
import transfer.com.dao.AccountDao
import transfer.com.dao.AccountDaoImpl
import transfer.com.model.Account
import transfer.com.utils.ServiceResponse
import java.math.BigDecimal

/**
 * Interface representing actions available for business purposes
 */
interface AccountService {

    val mSG_TRANSACTION_UNKNOWN_SENDER: String
        get() = "Account Sending unknown"
    val mSG_TRANSACTION_UNKNOWN_RECEIVER: String
        get() = "Account Receiving unknown"
    val mSG_TRANSACTION_INSUFICIENT_FUND: String
        get() = "Transfer failed insufficient balance on Sender Account"
    val mSG_TRANSACTION_NEGATIVE_AMOUNT: String
        get() = "Transfer failed - please use a positive amount "
    val mSG_TRANSACTION_ERROR : String
        get() = "Transfer failed - Unexpected Error"
    val MSG_DEPOSIT_ERROR : String
        get() = "Deposit failed - Unexpected Error"
    val mSG_DEPOSIT_NEGATIVE_AMOUNT : String
        get() = "Deposit failed - please use a positive amount bigger than zero"
    val mSG_DEPOSIT_UNKNOWN_ACCOUNT : String
        get() = "Withdraw failed - Account unknown"
    val mSG_WITHDRAW_ERROR : String
        get() = "Withdraw failed - Unexpected Error"
    val mSG_WITHDRAW_NEGATIVE_AMOUNT : String
        get() = "Withdraw failed - please use a positive amount"
    val mSG_WITHDRAW_UNKNOWN_ACCOUNT : String
        get() = "Withdraw failed - Account unknown"
    val mSG_CREATE_ACCOUNT_ERROR : String
        get() = "Create Account failed - Unexpected Error"
    val mSG_CREATE_ACCOUNT_NEGATIVE_AMOUNT : String
        get() = "Create Account failed - please use a positive amount bigger than zero"
    val mSG_CREATE_ACCOUNT_ALREADY_EXIST : String
        get() = "Create Account failed - Account already exist please use another email address"
    val mSG_DELETE_ACCOUNT_ERROR : String
        get() = "Delete Account failed - Unexpected Error"
    val mSG_ACCOUNT_DOES_NOT_EXITS : String
        get() = "Delete Account failed - Account does not exits!"

    /**
     * Static reference to the DAO (containing datastore in-memory)
     */
    companion object {
        var accountDao : AccountDao = AccountDaoImpl
    }

    /**
     * Getter for the property accountDao
     */
    fun getAccountDao(): AccountDaoImpl {
        return accountDao as AccountDaoImpl
    }

    /**
     * Setter for the property accountDao
     */
    fun setAccountDao(accountDaoToSet: AccountDaoImpl) {
        if (accountDao != null) {
            accountDao = accountDaoToSet
        }
    }

    /**
     * Method uses to find an account
     * Parameter : take directly the http context
     * Result : if successful return the Account else null
     */
    fun  getAccount(email : String): Account?
    /**
     * Method uses to list all accounts available
     * Result : return a Map representing all Account
     */
    fun getAllAccounts (): HashMap<String, Account>

    /**
     * Method uses to make a deposit on an accounts available
     * Parameter : take directly the http context
     * Return true if the transaction is successful else return false, with a message
     */
    fun makeDeposit(email : String, depositAmount : BigDecimal) : ServiceResponse

    /**
    * Method uses to create an accounts
    * Parameter : take directly the http context
    * Return true if the transaction is successful else return false, with a message
    */
    fun createAccount(email : String , initialBalance :  BigDecimal) : ServiceResponse

    /**
     * Method uses to delete an accounts
     * Parameter : take directly the http context
     * Return true if the transaction is successful else return false, with a message
     */
    fun deleteAccount(email : String) : ServiceResponse

    /**
     * Function to transfer fund from a sender to a receiver
     * Parameters : emails of the sender and the receiver, the amount to send
     * Return true if the transaction is successful else return false, with a message
     */
    fun moneyTransaction(emailSender :  String , emailReceiver : String , transferAmount :  BigDecimal) : ServiceResponse

    /**
     * Function to Verify the availability of amount in the current Account
     * Parameters : emails (String) and the amount (Double)
     * return true if the current account balance is Bigger or equals to the amount else return false
     */
    fun checkAvailabilityOfAmount(email: String, amount: BigDecimal): Boolean


}