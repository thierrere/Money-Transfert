package transfer.com.dao

import transfer.com.model.Account
import org.slf4j.LoggerFactory
import java.math.BigDecimal


object AccountDaoImpl : AccountDao {

    //Logger for the class
    private val LOGGER = LoggerFactory.getLogger(AccountDaoImpl.javaClass)
     /**
     * In-Memory datastore
     */
    private var accounts = hashMapOf(
        "account01@mail.com" to Account (email = "account01@mail.com", balance  = BigDecimal(1000)),
        "account02@mail.com" to Account (email = "account02@mail.com", balance  = BigDecimal(2000)),
        "account03@mail.com" to Account (email = "account03@mail.com", balance  = BigDecimal(3000)),
        "account04@mail.com" to Account (email = "account04@mail.com", balance  = BigDecimal(4000))
    )

    /**
     * Method to return all the accounts
     */
    fun getAccounts() : HashMap<String, Account>{
        LOGGER.info("List of all Accounts")
        return accounts
    }

    fun setAccounts(accountToSet : HashMap<String, Account>) {
        if (accountToSet.size > 0){
            accounts=accountToSet
        }
    }

    override fun findByEmail(email: String): Account? {
        LOGGER.info("findByEmail call : email = $email")
        return accounts[email]
    }

    override fun balance(email: String): BigDecimal {
        LOGGER.info("Checking balance for : email = $email")
        val account = findByEmail(email)
        if(account !=null){
            LOGGER.info("Checking balance for email = $email : email found in datastore (balance return)")
            return account.getBalance()
        }
        LOGGER.info("Checking balance for email = $email : email not found")
        return BigDecimal(-1)
    }

    override fun deposit(email: String, amount: BigDecimal): BigDecimal {
        LOGGER.info("Start - deposit on account email = $email for the amount = $amount")
        val account = findByEmail(email)
        if(account !=null && amount>BigDecimal(0)){
            account.setBalance(account.getBalance().plus(amount))
            accounts[email] = account
            LOGGER.info("End - deposit successful on account email = $email")
            return account.getBalance()
        }
        LOGGER.info("End - deposit failed on account email = $email")
        return BigDecimal(-1)
    }

    override fun withdraw(email: String, amount: BigDecimal): BigDecimal {
        LOGGER.info("Start - withdraw on account email = $email for the amount = $amount")
        val account = findByEmail(email)
        if(account !=null&& amount>BigDecimal(0)){
            account.setBalance(account.getBalance().minus(amount))
            accounts[email] = account
            LOGGER.info("End - withdraw successful on account email = $email")
            return account.getBalance()
        }
        LOGGER.info("End - withdraw failed on account email = $email")
        return BigDecimal(-1)
    }

    override fun create(email: String, amount: BigDecimal): Account? {
        LOGGER.info("Start - create an account email = $email for the amount = $amount")
        if(findByEmail(email)==null && amount >BigDecimal(0)){
            accounts[email] = Account (email = email, balance = amount)
            LOGGER.info("End - create successful an account email = $email")
            return accounts[email]
       }
        LOGGER.info("End - create failed an account email = $email")
        return null
    }

    override fun delete(email: String): Account? {
        LOGGER.info("Start - delete an account email = $email")
        if(findByEmail(email) != null){
            LOGGER.info("End - delete successful an account email = $email")
            return accounts.remove(email)
        }
        LOGGER.info("End - delete failed an account email = $email")
        return null
    }
}