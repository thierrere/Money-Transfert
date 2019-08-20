package transfer.com.model

data class Account (private val email : String, private var balance : Double) {

    fun isEmpty():Boolean {
        return (this.balance <= 0.0)
    }

    fun getEmail() : String{
        return this.email
    }

    fun getBalance () : Double{
        return this.balance
    }

    fun setBalance (newBalance : Double) {
        this.balance=newBalance;
    }
}