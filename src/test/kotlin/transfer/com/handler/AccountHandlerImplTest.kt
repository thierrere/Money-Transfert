package transfer.com.handler

import transfer.com.dao.AccountDaoImpl
import transfer.com.model.Account
import transfer.com.service.AccountService
import transfer.com.service.AccountServiceImpl
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import com.jayway.restassured.RestAssured.given
import org.hamcrest.Matchers.equalTo
import transfer.com.router.Router
import java.math.BigDecimal

class AccountHandlerImplTest {
    private val router : Router = Router()
    private val accountService : AccountService = AccountServiceImpl ()
    private val p = 7000
    init {
        router.createService(p)
    }

    @BeforeTest
    fun prepareTest(){
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
    fun pingTest(){
        given().port(p).`when`().get("/").then().statusCode(404)
    }

    @Test
    fun getAllTest(){
        given().port(p).`when`().get("accounts/").then().statusCode(200)
    }

    @Test
    fun consulTest (){
        val account = accountService.getAccountDao().getAccounts()["test01@mail.com"]
        given().port(p).`when`().get("accounts/").then().statusCode(200)
        given().port(p).`when`().get("accounts/${account!!.getEmail()}").then().statusCode(200).body("email", equalTo(account.getEmail())).body("balance", equalTo(account.getBalance()))
    }

    @Test
    fun depositTest (){
        val account = accountService.getAccountDao().getAccounts()["test01@mail.com"]
        val amountBeforeDeposit = account!!.getBalance()
        val amountToAdd = BigDecimal(1000)
        given().port(p).`when`().get("accounts/").then().statusCode(200)
        given().port(p).`when`().get("accounts/${account.getEmail()}").then().statusCode(200)
        given().port(p).`when`().post("accounts/${account.getEmail()}/$amountToAdd").then().statusCode(200).body("status", equalTo(true))
        given().port(p).`when`().get("accounts/${account.getEmail()}").then().statusCode(200).body("email", equalTo(account.getEmail())).body("balance", equalTo((amountBeforeDeposit.plus(amountToAdd))))
    }

    @Test
    fun createTest () {
        val newAccount = Account("test10@mail.com", BigDecimal(20000))
        given().port(p).`when`().get("accounts/").then().statusCode(200)
        given().port(p).`when`().put("accounts/${newAccount.getEmail()}/${newAccount.getBalance()}").then().statusCode(200).body("status", equalTo(true))
        given().port(p).`when`().get("accounts/${newAccount.getEmail()}").then().statusCode(200).body("email", equalTo(newAccount.getEmail())).body("balance", equalTo(newAccount.getBalance()))
    }

    @Test
    fun transferTest () {
        val accountSending = accountService.getAccountDao().getAccounts()["test01@mail.com"]
        val amountBeforeSending = accountSending!!.getBalance()
        val accountReceiving = accountService.getAccountDao().getAccounts()["test04@mail.com"]
        val amountBeforeReceiving = accountReceiving!!.getBalance()
        val amountToTransfer = BigDecimal(500)
        given().port(p).`when`().get("accounts/").then().statusCode(200)
        given().port(p).`when`().post("accounts/${accountSending.getEmail()}/$amountToTransfer/${accountReceiving.getEmail()}").then().statusCode(200).body("status", equalTo(true))
        given().port(p).`when`().get("accounts/${accountSending.getEmail()}").then().statusCode(200).body("email", equalTo(accountSending.getEmail())).body("balance", equalTo((amountBeforeSending.minus(amountToTransfer))))
        given().port(p).`when`().get("accounts/${accountReceiving.getEmail()}").then().statusCode(200).body("email", equalTo(accountReceiving.getEmail())).body("balance", equalTo((amountBeforeReceiving.plus(amountToTransfer))))
    }

    @AfterTest
    fun endTest(){
        router.getApp()!!.stop()
    }

}