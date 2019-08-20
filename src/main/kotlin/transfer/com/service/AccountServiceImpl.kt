package transfer.com.service

import io.javalin.http.Handler
import transfer.com.dao.AccountDao
import transfer.com.dao.AccountDaoImpl

class AccountServiceImpl : AccountService {

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
    fun setAccounDao(accountDaoToSet: AccountDaoImpl) {
        if (accountDao != null) {
            accountDao = accountDaoToSet
        }
    }
    override fun getAccount(): Handler {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAllAccounts(): Handler {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun makedeposit(): Handler {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createAccount(): Handler {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteAcount(): Handler {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun moneyTransaction(): Handler {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}