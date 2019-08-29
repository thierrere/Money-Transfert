package transfer.com.model

import java.math.BigDecimal

data class Account (private val email : String, private var balance : BigDecimal) {

    fun isEmpty():Boolean {
        return (this.balance <= BigDecimal.ZERO)
    }

    fun getEmail() : String{
        return this.email
    }

    fun getBalance () : BigDecimal{
        return this.balance
    }

    fun setBalance (newBalance : BigDecimal) {
        this.balance=newBalance;
    }
}