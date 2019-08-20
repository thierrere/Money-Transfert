package transfer.com.dao

import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import transfer.com.model.Account
import kotlin.test.BeforeTest
import kotlin.test.assertNotNull

class AccountDaoImplTest {

    private val accountDao : AccountDaoImpl = AccountDaoImpl
    @BeforeTest
    fun prepareTest (){
        val accounts = hashMapOf(
            "test01@mail.com" to Account (email = "test01@mail.com", balance  = 1000.0),
            "test02@mail.com" to Account (email = "test02@mail.com", balance  = 2000.0),
            "test03@mail.com" to Account (email = "test03@mail.com", balance  = 3000.0),
            "test04@mail.com" to Account (email = "test04@mail.com", balance  = 4000.0)
        )
        accountDao.setAccounts (accountToSet = accounts)
    }

    @Test
    fun findByEmailTest(){
        runBlocking {
            assertNotNull(accountDao.findByEmail("test01@mail.com"))
            assertThat(accountDao.getAccounts()["test01@mail.com"]).isEqualTo(accountDao.findByEmail(accountDao.getAccounts()["test01@mail.com"]!!.getEmail()))
        }
    }

    @Test
    fun depositTest() {
        runBlocking {
            val account = accountDao.getAccounts()["test01@mail.com"]
            assertNotNull(accountDao.findByEmail(account!!.getEmail()))
            val depositValue = 200.0
            val afterDepositValue = depositValue+account.getBalance()
            accountDao.deposit(account.getEmail(), depositValue)
            assertThat(Account(email = account.getEmail(), balance = afterDepositValue)).isEqualTo(accountDao.getAccounts()["test01@mail.com"])
        }
    }

    @Test
    fun withdrawTest () {
        runBlocking {
            val account = accountDao.getAccounts()["test02@mail.com"]
            assertNotNull(accountDao.findByEmail(account!!.getEmail()))
            val withdrawValue = 200.0
            val afterWithdrawValue = account.getBalance() - withdrawValue
            accountDao.withdraw(account.getEmail(), withdrawValue)
            assertThat(Account(email = account.getEmail(), balance = afterWithdrawValue)).isEqualTo(accountDao.getAccounts()["test02@mail.com"])
        }
    }

    @Test
    fun balanceTest () {
        runBlocking {
            val account = accountDao.getAccounts()["test03@mail.com"]
            assertNotNull(accountDao.findByEmail(account!!.getEmail()))
            assertThat(account).isEqualTo(Account(email = account.getEmail(), balance = accountDao.balance(account.getEmail())))
        }
    }

    @Test
    fun createTest () {
        runBlocking {
            val accountToCreate = Account (email ="test10@mail.com", balance = 10000.0)
            assertThat(accountDao.findByEmail(accountToCreate.getEmail())).isNull()
            assertThat(accountToCreate).isEqualTo(accountDao.create(accountToCreate.getEmail(), accountToCreate.getBalance()))
            assertThat(accountDao.findByEmail(accountToCreate.getEmail())).isNotNull()
            assertThat(accountToCreate).isEqualTo(accountDao.findByEmail(accountToCreate.getEmail()))
        }
    }

    @Test
    fun deleteTest (){
        runBlocking {
            val accountToDelete = Account (email ="test03@mail.com", balance = 3000.0)
            assertThat(accountDao.findByEmail(accountToDelete.getEmail())).isNotNull()
            assertThat(accountToDelete).isEqualTo(accountDao.delete(accountToDelete.getEmail()))
            assertThat(accountDao.findByEmail(accountToDelete.getEmail())).isNull()
        }
    }

}