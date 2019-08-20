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

class AccountHandlerImplTest {
    private val router : Router = Router()
    //private val accountHandler : AccountHandler = AccountHandlerImpl
    private val accountService : AccountService = AccountServiceImpl ()
    private val p = 7000
    init {
        router.createService(p)
    }

    @BeforeTest
    fun prepareTest(){
        val accounts = hashMapOf(
            "test01@mail.com" to Account(email = "test01@mail.com", balance = 4000.0),
            "test02@mail.com" to Account(email = "test02@mail.com", balance = 5000.0),
            "test03@mail.com" to Account(email = "test03@amail.com", balance = 6000.0),
            "test04@mail.com" to Account(email = "test04@mail.com", balance = 8000.0)
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
        given().port(p).`when`().get("accounts/${account!!.getEmail()}").then().statusCode(200).body("email", equalTo("test01@mail.com")).body("balance", equalTo(4000.0f))
    }

    @Test
    fun depositTest (){
        val account = accountService.getAccountDao().getAccounts()["test01@mail.com"]
        val amountToAdd = 1000.0
        given().port(p).`when`().get("accounts/").then().statusCode(200)
        given().port(p).`when`().get("accounts/${account!!.getEmail()}").then().statusCode(200)
        given().port(p).`when`().post("accounts/${account!!.getEmail()}/$amountToAdd").then().statusCode(200).body("status", equalTo(true))
    }

    @Test
    fun createTest () {
        val newAccount = Account("test10@mail.com", 20000.0)
        given().port(p).`when`().get("accounts/").then().statusCode(200)
        given().port(p).`when`().put("accounts/${newAccount!!.getEmail()}/${newAccount.getBalance()}").then().statusCode(200).body("status", equalTo(true))
    }

    @Test
    fun transferTest () {
        val accountSending = accountService.getAccountDao().getAccounts()["test01@mail.com"]
        val accountReceiving = accountService.getAccountDao().getAccounts()["test04@mail.com"]
        val amountToTransfer = 500.0
        given().port(p).`when`().get("accounts/").then().statusCode(200)
        given().port(p).`when`().post("accounts/${accountSending!!.getEmail()}/$amountToTransfer/${accountReceiving!!.getEmail()}").then().statusCode(200).body("status", equalTo(true))

    }

    @AfterTest
    fun endTest(){
        router.getApp()!!.stop()
    }

}