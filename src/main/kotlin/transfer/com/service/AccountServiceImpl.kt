package transfer.com.service

import io.javalin.http.Handler
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import transfer.com.dao.AccountDao
import transfer.com.dao.AccountDaoImpl
import transfer.com.model.Account

class AccountServiceImpl : AccountService {

    //Logger for the class
    //private val LOGGER = LoggerFactory.getLogger(AccountServiceImpl.javaClass)

    /*/**
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
    fun setAccounDao(accountDaoToSet: AccountDaoImpl) {
        if (accountDao != null) {
            accountDao = accountDaoToSet
        }
    }*/

    override fun getAccount(email : String): Account? {
        return getAccountDao().findByEmail(email)!!
    }

    override fun getAllAccounts(): HashMap<String, Account> {
        return getAccountDao().getAccounts()
    }

    override fun makeDeposit(email : String , depositAmount : Double): HashMap<Boolean, String> {
        var depositStatus = hashMapOf(false to this.MSG_TRANSACTION_ERROR)
        //Verify the amount to
        if (depositAmount <= 0.0){
            return hashMapOf(false to this.MSG_DEPOSIT_NEGATIVE_AMOUNT)
        }
        if(getAccountDao().findByEmail(email) == null){
            return hashMapOf(false to this.MSG_DEPOSIT_UNKNOWN_ACCOUNT)
        }
        runBlocking {
            //Deposit of the amount on the receiverAccount
            getAccountDao().deposit(email, depositAmount)
            depositStatus = hashMapOf(true to "Deposit Successful to account $email of amount : $depositAmount")
        }
        return depositStatus
    }

    override fun createAccount(email : String , initialBalance :  Double): HashMap<Boolean, String> {
        var createAccountStatus = hashMapOf(false to this.MSG_CREATE_ACCOUNT_ERROR)
        if (initialBalance < 0.0){
            return hashMapOf(false to this.MSG_CREATE_ACCOUNT_NEGATIVE_AMOUNT)
        }
        if(getAccountDao().findByEmail(email) != null){
            return hashMapOf(false to this.MSG_CREATE_ACCOUNT_ALREADY_EXIST)
        }
        runBlocking {
            val newAccount = getAccountDao().create(email , initialBalance)
            if(newAccount!=null) {
                createAccountStatus = hashMapOf(true to "Create Account Successful : $newAccount")
            }
        }

        return createAccountStatus;
    }

    override fun deleteAccount(email : String): HashMap<Boolean, String> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun moneyTransaction(emailSender :  String , emailReceiver : String , transferAmount :  Double): HashMap<Boolean, String> {
        var transactionStatus = hashMapOf(false to this.MSG_TRANSACTION_ERROR)
        //Verify parameters
        if (transferAmount <= 0.0){
            return hashMapOf(false to this.MSG_TRANSACTION_NEGATIVE_AMOUNT)
        }
        //Verify email of the sender
        if(getAccountDao().findByEmail(emailSender) == null){
            return hashMapOf(false to this.MSG_TRANSACTION_UNKNOWN_SENDER)
        }
        //Verify email of the receiver
        if (getAccountDao().findByEmail(emailReceiver) == null) {
            return hashMapOf(false to this.MSG_TRANSACTION_UNKNOWN_RECEIVER)
        }
        //verify the balance of the sender
        if (!checkAvailabilityOfAmount(email = emailSender, amount = transferAmount)) {
            return hashMapOf(false to this.MSG_TRANSACTION_INSUFICIENT_FUND)
        }
        runBlocking {
            //Withdraw of the amount from the senderAccount
            getAccountDao().withdraw(emailSender, transferAmount)
            //Deposit of the amount on the receiverAccount
            getAccountDao().deposit(emailReceiver, transferAmount)
            transactionStatus = hashMapOf(true to "Transfer Successful from account $emailSender to account $emailReceiver of amount : $transferAmount")
        }
        return transactionStatus
    }

    override fun checkAvailabilityOfAmount(email: String, amount: Double): Boolean {
        if (getAccountDao().findByEmail(email)!!.getBalance() >= amount) {
            return true
        }
        return false
    }
}