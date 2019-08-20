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
        return accounts[email]
    }

    override fun balance(email: String): Double {
        val account = findByEmail(email)
        if(account !=null){
            return account.getBalance()
        }
        return -1.0
    }

    override fun deposit(email: String, amount: Double): Double {
        val account = findByEmail(email)
        if(account !=null && amount>0.0){
            account.setBalance(amount+account.getBalance())
            //println("deposit 1 - Accounts = $accounts")
            accounts[email] = account
            //println("deposit 2 - Accounts = $accounts")
            //println("Deposit successful :  $account")
            return account.getBalance()
        }
        return -1.0
    }

    override fun withdraw(email: String, amount: Double): Double {
        val account = findByEmail(email)
        if(account !=null&& amount>0.0){
            account.setBalance(account.getBalance()-amount)
            //println("withdraw 1 - Accounts = $accounts")
            accounts[email] = account
            //println("withdraw 2 - Accounts = $accounts")
            //println("Withdraw successful :  $account")
            return account.getBalance()
        }
        return -1.0
    }

    override fun create(email: String, amount: Double): Account? {
        if(findByEmail(email)==null && amount >0.0){
            accounts[email] = Account (email = email, balance = amount)
            return accounts[email]
       }
        return null
    }

    override fun delete(email: String): Account? {
        if(findByEmail(email) != null){
            return accounts.remove(email)
        }
        return null
    }
}