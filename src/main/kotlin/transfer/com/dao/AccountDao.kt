package transfer.com.dao

import transfer.com.model.Account

interface AccountDao {

    fun findByEmail(email: String): Account?
    fun deposit(email: String, amount: Double): Double
    fun withdraw (email : String, amount: Double): Double
    fun balance (email : String): Double
}