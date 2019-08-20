package transfer.com.router

import transfer.com.dao.AccountDaoImpl
import transfer.com.model.Account
import transfer.com.service.AccountService
import transfer.com.service.AccountServiceImpl
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import com.jayway.restassured.RestAssured.given
import org.hamcrest.Matchers.equalTo

class AccountRouterImplTest {
    val router : Router = Router()
    val accountRouter : AccountRouter = AccountRouterImpl
    val accountService : AccountService = AccountServiceImpl ()
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
        given().port(p).`when`().get("accounts/${account!!.getEmail()}").then().statusCode(200)
        given().port(p).`when`().get("accounts/${account!!.getEmail()}").then().statusCode(200).body("email", equalTo("test01@mail.com")).body("balance", equalTo((account.getBalance()).toString()))
    }


    @AfterTest
    fun endTest(){
        router.getApp()!!.stop()
    }

}