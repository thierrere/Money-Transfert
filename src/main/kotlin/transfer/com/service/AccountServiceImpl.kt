package transfer.com.service

import kotlinx.coroutines.runBlocking
import transfer.com.model.Account
import transfer.com.utils.ServiceResponse

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

    override fun makeDeposit(email : String , depositAmount : Double): ServiceResponse {
        var depositStatus = ServiceResponse(false , this.mSG_TRANSACTION_ERROR)
        //Verify the amount to
        if (depositAmount <= 0.0){
            return ServiceResponse(false , this.mSG_DEPOSIT_NEGATIVE_AMOUNT)
        }
        if(getAccountDao().findByEmail(email) == null){
            return ServiceResponse(false , this.mSG_DEPOSIT_UNKNOWN_ACCOUNT)
        }
        runBlocking {
            //Deposit of the amount on the receiverAccount
            getAccountDao().deposit(email, depositAmount)
            depositStatus = ServiceResponse(true , "Deposit Successful to account $email of amount : $depositAmount")
        }
        return depositStatus
    }

    override fun createAccount(email : String , initialBalance :  Double): ServiceResponse {
        var createAccountStatus = ServiceResponse(false , this.mSG_CREATE_ACCOUNT_ERROR)
        if (initialBalance < 0.0){
            return ServiceResponse(false , this.mSG_CREATE_ACCOUNT_NEGATIVE_AMOUNT)
        }
        if(getAccountDao().findByEmail(email) != null){
            return ServiceResponse(false , this.mSG_CREATE_ACCOUNT_ALREADY_EXIST)
        }
        runBlocking {
            val newAccount = getAccountDao().create(email , initialBalance)
            if(newAccount!=null) {
                createAccountStatus = ServiceResponse(true , "Create Account Successful : $newAccount")
            }
        }

        return createAccountStatus;
    }

    override fun deleteAccount(email : String): ServiceResponse {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun moneyTransaction(emailSender :  String , emailReceiver : String , transferAmount :  Double): ServiceResponse {
        var transactionStatus = ServiceResponse(false , this.mSG_TRANSACTION_ERROR)
        //Verify parameters
        if (transferAmount <= 0.0){
            return ServiceResponse(false , this.mSG_TRANSACTION_NEGATIVE_AMOUNT)
        }
        //Verify email of the sender
        if(getAccountDao().findByEmail(emailSender) == null){
            return ServiceResponse(false , this.mSG_TRANSACTION_UNKNOWN_SENDER)
        }
        //Verify email of the receiver
        if (getAccountDao().findByEmail(emailReceiver) == null) {
            return ServiceResponse(false , this.mSG_TRANSACTION_UNKNOWN_RECEIVER)
        }
        //verify the balance of the sender
        if (!checkAvailabilityOfAmount(email = emailSender, amount = transferAmount)) {
            return ServiceResponse(false , this.mSG_TRANSACTION_INSUFICIENT_FUND)
        }
        runBlocking {
            //Withdraw of the amount from the senderAccount
            getAccountDao().withdraw(emailSender, transferAmount)
            //Deposit of the amount on the receiverAccount
            getAccountDao().deposit(emailReceiver, transferAmount)
            transactionStatus = ServiceResponse(true , "Transfer Successful from account $emailSender to account $emailReceiver of amount : $transferAmount")
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