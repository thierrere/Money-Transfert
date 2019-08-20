package transfer.com.service

import io.javalin.http.Handler
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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAllAccounts(): HashMap<String, Account> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun makeDeposit(email : String , depositAmount : Double): HashMap<Boolean, String> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createAccount(email : String , initialBalance :  Double): HashMap<Boolean, String> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteAccount(email : String): HashMap<Boolean, String> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun moneyTransaction(emailSender :  String , emailReceiver : String , transferAmount :  Double): HashMap<Boolean, String> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun checkAvailabilityOfAmount(email: String, amount: Double): Boolean {
        if (getAccountDao().findByEmail(email)!!.getBalance() >= amount) {
            //if (AccountDaoImpl.findByEmail(email)!!.getBalance() >= amount) {
            return true
        }
        return false
    }
}