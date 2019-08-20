package transfer.com.dao

import transfer.com.model.Account

object AccountDaoImpl : AccountDao {

    /**
     * In-Memory datastore
     */
    private var accounts = hashMapOf(
        "account01@mail.com" to Account (email = "account01@mail.com", balance  = 1000.0),
        "account02@mail.com" to Account (email = "account02@mail.com", balance  = 2000.0),
        "account03@amail.com" to Account (email = "account03@amail.com", balance  = 3000.0),
        "account04@mail.com" to Account (email = "account04@mail.com", balance  = 4000.0)
    )

    /**
     * Method to return all the accounts
     */
    fun getAccounts() : HashMap<String, Account>{
        return accounts
    }

    fun setAccounts(accountToSet : HashMap<String, Account>) {
        if (accountToSet.size > 0){
            accounts=accountToSet
        }
    }

    override fun findByEmail(email: String): Account? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun withdraw(email: String, amount: Double): Double {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun balance(email: String): Double {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deposit(email: String, amount: Double): Double {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}