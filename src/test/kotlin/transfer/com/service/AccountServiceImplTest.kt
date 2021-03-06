package transfer.com.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import transfer.com.dao.AccountDaoImpl
import transfer.com.model.Account
//import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.assertNotNull
import org.hamcrest.Matchers.equalTo
import transfer.com.utils.ServiceResponse
import java.math.BigDecimal

class AccountServiceImplTest {

    private val accountService: AccountService = AccountServiceImpl()
    @BeforeTest
    fun prepareTest() {
        val accounts = hashMapOf(
            "test01@mail.com" to Account(email = "test01@mail.com", balance = BigDecimal(4000)),
            "test02@mail.com" to Account(email = "test02@mail.com", balance = BigDecimal(5000)),
            "test03@mail.com" to Account(email = "test03@amail.com", balance = BigDecimal(6000)),
            "test04@mail.com" to Account(email = "test04@mail.com", balance = BigDecimal(8000))
        )
        val accountDao = AccountDaoImpl
        accountDao.setAccounts(accounts)
        accountService.setAccountDao(accountDao)
    }

    @Test
    fun getAccountTest() {
        val account = accountService.getAccountDao().getAccounts()["test01@mail.com"]
        assertThat(account).isEqualTo(accountService.getAccount(account!!.getEmail()))

    }

    @Test
    fun getAllAccountsTest() {
        assertThat(accountService.getAccountDao().getAccounts()).isEqualTo(accountService.getAllAccounts())
    }

    @Test
    fun checkAvailabilityOfAmountTest() {
        val account = accountService.getAccountDao().getAccounts()["test02@mail.com"]
        //Amount not available on the current account
        assertThat(
            accountService.checkAvailabilityOfAmount(
                account!!.getEmail(),
                account.getBalance() + BigDecimal(1000)
            )
        ).isFalse()
        //Amount available on the current account
        assertThat(accountService.checkAvailabilityOfAmount(account!!.getEmail(), BigDecimal(1000))).isTrue()
    }

    /**
     * Test deposit case 01 : Unknown account
     */
    @Test
    fun makeDepositTestUnknownAccount() {
        val accountUnknown = Account("account10@mail.com", BigDecimal(20000))
        val result = ServiceResponse(false , accountService.mSG_DEPOSIT_UNKNOWN_ACCOUNT)
        assertThat(result).isEqualTo(accountService.makeDeposit(accountUnknown!!.getEmail(), BigDecimal(200)))
    }

    /**
     * Test deposit case 02 : Negative amount
     */
    @Test
    fun makeDepositTestNegativeAmount() {
        val account = accountService.getAccountDao().getAccounts()["test02@mail.com"]
        val negativeAmount = BigDecimal(-1000)
        val result = ServiceResponse(false , accountService.mSG_DEPOSIT_NEGATIVE_AMOUNT
        )
        assertThat(result).isEqualTo(accountService.makeDeposit(account!!.getEmail(), negativeAmount))
    }

    /**
     * Test deposit with correct parameters
     */
    @Test
    fun makeDepositTest() {
        val account = accountService.getAccountDao().getAccounts()["test02@mail.com"]
        val balance = account!!.getBalance()
        val amountToDeposit = BigDecimal(500)
        val result = ServiceResponse(true , "Deposit Successful to account ${account.getEmail()} of amount : $amountToDeposit"
        )
        assertThat(result).isEqualTo(accountService.makeDeposit(account.getEmail(), amountToDeposit))
        assertThat(Account(account.getEmail(),balance+amountToDeposit)).isEqualTo(accountService.getAccountDao().getAccounts()["test02@mail.com"])
    }

    /**
     * Test createAccount case 01 : Negative amount
     */
    @Test
    fun createAccountTestWithNegativeAmount() {
        val accountToCreate = Account ("test20@mail.com", BigDecimal(-2000))
        val result = ServiceResponse(false , accountService.mSG_CREATE_ACCOUNT_NEGATIVE_AMOUNT)
        assertThat(result).isEqualTo(accountService.createAccount(accountToCreate.getEmail(), accountToCreate.getBalance()))
    }

    /**
     * Test createAccount case 02 : Already existing account
     */
    @Test
    fun createAccountTestWithExistingAccount() {
        val accountToCreate = Account ("test02@mail.com", BigDecimal(2000))
        val result = ServiceResponse(false , accountService.mSG_CREATE_ACCOUNT_ALREADY_EXIST)
        assertThat(accountService.getAccount(accountToCreate.getEmail())).isNotNull()
        assertThat(result).isEqualTo(accountService.createAccount(accountToCreate.getEmail(), accountToCreate.getBalance()))
    }

    /**
     * Test createAccount with correct parameters
     */
    @Test
    fun createAccountTest() {
        val accountToCreate = Account ("test20@mail.com", BigDecimal(20000))
        val result = ServiceResponse(true , "Create Account Successful : $accountToCreate")
        assertThat(result).isEqualTo(accountService.createAccount(accountToCreate.getEmail(), accountToCreate.getBalance()))
        assertThat(accountToCreate).isEqualTo(accountService.getAccountDao().getAccounts()[accountToCreate.getEmail()])
    }

    @Test
    fun deleteAccountTest() {

    }

    /**
     * Test Transfer case 01 : sending money with an unknown email
     */
    @Test
    fun moneyTransactionUnknownSendingEmail() {
        val accountSending = Account("account01@mail.com", BigDecimal(20000))
        val result = ServiceResponse(false , accountService.mSG_TRANSACTION_UNKNOWN_SENDER)
        assertThat(
            accountService.moneyTransaction(
                accountSending.getEmail(),
                accountService.getAccountDao().getAccounts()["test02@mail.com"]!!.getEmail(),
                transferAmount =  BigDecimal(500)
            )
        ).isEqualTo(result)
        assertThat(result).isEqualTo(accountService.moneyTransaction(accountSending.getEmail(),accountService.getAccountDao().getAccounts()["test02@mail.com"]!!.getEmail(),transferAmount =  BigDecimal(500)))
    }

    /**
     * Test Transfer case 02 : sending money to an unknown email
     */
    @Test
    fun moneyTransactionUnknownReceiverEmail() {
        val accountReceiving = Account("account02@mail.com", BigDecimal(30000))
        val result = ServiceResponse(false , accountService.mSG_TRANSACTION_UNKNOWN_RECEIVER)
        assertThat(
            result
        ).isEqualTo(accountService.moneyTransaction(
            accountService.getAccountDao().getAccounts()["test02@mail.com"]!!.getEmail(),
            accountReceiving.getEmail(),
            transferAmount = BigDecimal(500)
        ))
    }

    /**
     * Test Transfer case 03 : sending money over the balance of the actual account
     */
    @Test
    fun moneyTransactionWithInsufficientBalance() {
        val accountSending = accountService.getAccountDao().getAccounts()["test02@mail.com"]
        val accountReceiving = accountService.getAccountDao().getAccounts()["test04@mail.com"]
        val amountToTransfer = accountSending!!.getBalance() + BigDecimal(1000)
        val result = ServiceResponse(false , accountService.mSG_TRANSACTION_INSUFICIENT_FUND)
        assertThat(
            result
        ).isEqualTo(accountService.moneyTransaction(
            accountSending.getEmail(),
            accountReceiving!!.getEmail(),
            transferAmount= amountToTransfer
        ))
    }

    /**
     * Test Transfer case 04 : sending money with a negative amount
     */
    @Test
    fun moneyTransactionWithNegativeAmount() {
        val accountSending = accountService.getAccountDao().getAccounts()["test02@mail.com"]
        val accountReceiving = accountService.getAccountDao().getAccounts()["test04@mail.com"]
        val result = ServiceResponse(false , accountService.mSG_TRANSACTION_NEGATIVE_AMOUNT)
        assertThat(
            result
        ).isEqualTo(accountService.moneyTransaction(
            accountSending!!.getEmail(),
            accountReceiving!!.getEmail(),
            transferAmount = BigDecimal(-1000)
        ))
    }

    /**
     * Test transfer with correct parameters
     */
    @Test
    fun moneyTransactionTest() {
        val accountSending = accountService.getAccountDao().getAccounts()["test01@mail.com"]
        val accountReceiving = accountService.getAccountDao().getAccounts()["test04@mail.com"]
        val amountToTransfer = BigDecimal(500)
        val balanceAccountSending = accountSending!!.getBalance()
        val balanceAccountReceiving = accountReceiving!!.getBalance()
        val result = ServiceResponse(
            true , "Transfer Successful from account ${accountSending.getEmail()} to account ${accountReceiving.getEmail()} of amount : $amountToTransfer"
        )
        //compare the result balance on each account after the transfers, on each object and in the account Map
        assertThat(
            result
        ).isEqualTo(accountService.moneyTransaction(
            accountSending.getEmail(),
            accountReceiving.getEmail(),
            transferAmount = amountToTransfer
        ))
        //Verifying the balance of each account in the provided database (HashMap)
        assertThat(
            Account(
                email = accountSending.getEmail(),
                balance = balanceAccountSending.minus(amountToTransfer)
            )
        ).isEqualTo(accountService.getAccountDao().getAccounts()["test01@mail.com"])
        assertThat(
            Account(
                email = accountReceiving.getEmail(),
                balance = balanceAccountReceiving.plus(amountToTransfer)
            )
        ).isEqualTo(accountService.getAccountDao().getAccounts()["test04@mail.com"])
    }
}