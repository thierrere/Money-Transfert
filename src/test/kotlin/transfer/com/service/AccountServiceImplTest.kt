package transfer.com.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import transfer.com.dao.AccountDaoImpl
import transfer.com.model.Account
//import transfer.com.router.Router
//import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.assertNotNull
//import com.jayway.restassured.RestAssured.given
import org.hamcrest.Matchers.equalTo

class AccountServiceImplTest {

    private val accountService: AccountService = AccountServiceImpl()
    //private var router : Router = Router ()
    //val p = System.getenv("PORT")?.toIntOrNull() ?: 7000
    @BeforeTest
    fun prepareTest() {
        val accounts = hashMapOf(
            "test01@mail.com" to Account(email = "test01@mail.com", balance = 4000.0),
            "test02@mail.com" to Account(email = "test02@mail.com", balance = 5000.0),
            "test03@mail.com" to Account(email = "test03@amail.com", balance = 6000.0),
            "test04@mail.com" to Account(email = "test04@mail.com", balance = 8000.0)
        )
        val accountDao = AccountDaoImpl
        accountDao.setAccounts(accounts)
        accountService.setAccountDao(accountDao)
        //router.createService(p)
    }

    @Test
    fun getAccountTest() {
        val account = accountService.getAccountDao().getAccounts()["test01@mail.com"]

    }

    @Test
    fun getAllAccountsTest() {
        assertThat(accountService.getAllAccounts()).isEqualTo(accountService.getAccountDao().getAccounts())
    }

    @Test
    fun checkAvailabilityOfAmountTest() {
        val account = accountService.getAccountDao().getAccounts()["test02@mail.com"]
        //val account = AccountDaoImpl.getAccounts()["test02@mail.com"]
        //Amount not available on the current account
        assertThat(
            accountService.checkAvailabilityOfAmount(
                account!!.getEmail(),
                account.getBalance() + 1000.00
            )
        ).isFalse()
        //Amount available on the current account
        assertThat(accountService.checkAvailabilityOfAmount(account!!.getEmail(), 1000.00)).isTrue()
    }

    @Test
    fun makeDepositTest() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    @Test
    fun createAccountTest() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    @Test
    fun deleteAccountTest() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * Test Transfer case 01 : sending money with an unknown email
     */
    @Test
    fun moneyTransactionRobustTest01() {
        val accountSending = Account("account01@mail.com", 20000.0)
        val result = hashMapOf(
            false to "Account Sending unknown"
        )
        assertThat(
            accountService.moneyTransaction(
                accountSending.getEmail(),
                accountService.getAccountDao().getAccounts()["test02@mail.com"]!!.getEmail(),
                transferAmount =  500.0
            )
        ).isEqualTo(result)
    }

    /**
     * Test Transfer case 02 : sending money to an unknown email
     */
    @Test
    fun moneyTransactionRobustTest02() {
        val accountReceiving = Account("account02@mail.com", 30000.0)
        val result = hashMapOf(
            false to "Account Receiving unknown"
        )
        assertThat(
            accountService.moneyTransaction(
                accountService.getAccountDao().getAccounts()["test02@mail.com"]!!.getEmail(),
                accountReceiving.getEmail(),
                transferAmount = 500.0
            )
        ).isEqualTo(result)
    }

    /**
     * Test Transfer case 03 : sending money over the balance of the actual account
     */
    @Test
    fun moneyTransactionRobustTest03() {
        val accountSending = accountService.getAccountDao().getAccounts()["test02@mail.com"]
        //val accountSending = AccountDaoImpl.getAccounts()["test02@mail.com"]
        val accountReceiving = accountService.getAccountDao().getAccounts()["test04@mail.com"]
        //val accountReceiving = AccountDaoImpl.getAccounts()["test04@mail.com"]
        val amountToTransfer = accountSending!!.getBalance() + 1000.0
        val result = hashMapOf(
            false to "Transfer failed insufficient balance on Sender Account"
        )
        assertThat(
            accountService.moneyTransaction(
                accountSending.getEmail(),
                accountReceiving!!.getEmail(),
                transferAmount= amountToTransfer
            )
        ).isEqualTo(result)
    }

    /**
     * Test Transfer case 04 : sending money with a negative amount
     */
    @Test
    fun moneyTransactionRobustTest04() {
        val accountSending = accountService.getAccountDao().getAccounts()["test02@mail.com"]
        //val accountSending = AccountDaoImpl.getAccounts()["test02@mail.com"]
        val accountReceiving = accountService.getAccountDao().getAccounts()["test04@mail.com"]
        //val accountReceiving = AccountDaoImpl.getAccounts()["test04@mail.com"]
        val result = hashMapOf(
            false to "Transfer failed - please use a positive amount"
        )
        assertThat(
            accountService.moneyTransaction(
                accountSending!!.getEmail(),
                accountReceiving!!.getEmail(),
                transferAmount = -1000.0
            )
        ).isEqualTo(result)
    }

    /**
     * Test transfer with correct parameters
     */
    @Test
    fun moneyTransactionTest() {
        val accountSending = accountService.getAccountDao().getAccounts()["test01@mail.com"]
        //val accountSending = AccountDaoImpl.getAccounts()["test01@mail.com"]
        val accountReceiving = accountService.getAccountDao().getAccounts()["test04@mail.com"]
        //val accountReceiving = AccountDaoImpl.getAccounts()["test04@mail.com"]
        val amountToTransfer = 500.0
        val balanceAccountSending = accountSending!!.getBalance()
        val balanceAccountReceiving = accountReceiving!!.getBalance()
        val result = hashMapOf(
            false to "Transfer Successful from ${accountSending.getEmail()} to ${accountReceiving.getEmail()} of amount : $amountToTransfer"
        )
        //compare the result balance on each account after the transfers, on each object and in the account Map
        assertThat(
            accountService.moneyTransaction(
                accountSending.getEmail(),
                accountReceiving.getEmail(),
                transferAmount = amountToTransfer
            )
        ).isEqualTo(result)
        //Verifying the balance of each account
        //Verifying the balance of each account in the provided database (HashMap)
        assertThat(
            Account(
                email = accountSending.getEmail(),
                balance = balanceAccountSending - amountToTransfer
            )
        ).isEqualTo(accountService.getAccountDao().getAccounts()["test01@mail.com"])
        //assertThat(Account(email = accountSending.getEmail(), balance = balanceAccountSending-amountToTranfert)).isEqualTo(AccountDaoImpl.getAccounts()["test01@mail.com"])
        assertThat(
            Account(
                email = accountReceiving.getEmail(),
                balance = balanceAccountReceiving + amountToTransfer
            )
        ).isEqualTo(accountService.getAccountDao().getAccounts()["test04@mail.com"])
    }

    /*@AfterTest
    fun endTest() {
        router.getApp()!!.stop()
    }*/
}