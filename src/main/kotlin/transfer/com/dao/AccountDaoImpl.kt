package transfer.com.dao

import transfer.com.model.Account

object AccountDaoImpl : AccountDao {
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