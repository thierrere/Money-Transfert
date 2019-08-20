package transfer.com.router

import io.javalin.http.Handler
import transfer.com.service.AccountService
import transfer.com.service.AccountServiceImpl

object AccountRouterImpl : AccountRouter {
    private val accountService : AccountService = AccountServiceImpl()
    override fun routing() : Handler {
        return Handler{
            ctx ->
            val pathParamMapSize = ctx.pathParamMap().size
            println("Context Map : ${ctx.pathParamMap()}")
            println("Context Map : $pathParamMapSize")
            println("Context Map : ${ctx.method()}")
            if(pathParamMapSize==0 && ctx.method() == "GET"){
                /**
                 * Get all accounts
                 */
                println("Get all accounts")
                accountService.getAllAccounts().let { ctx.json(it) }
                ctx.status(200)
            }
            if(pathParamMapSize == 1 && ctx.method() == "GET"){
                /**
                 * Find an account using email
                 */
                println("Find an account using email")
                accountService.getAccount(ctx.pathParam("email"))?.let { ctx.json(it) }
                    ?: ctx.status(404)
            }
            if(pathParamMapSize == 1 && ctx.method() == "DELETE"){
                /**
                 * Delete an Account
                 */
                println("Delete an Account")
                ctx.status(501)
            }
            if(pathParamMapSize == 2 && ctx.method() == "PUT"){
                /**
                 * Create new Account
                 */
                //val amount = ctx.pathParam("amount")
                println("Create new Account")
                accountService.createAccount(ctx.pathParam("email"),ctx.pathParam("amount").toDouble()).let{ctx.json{it}}
                ctx.status(200)
            }
            if(pathParamMapSize == 2 && ctx.method() == "POST"){
                /**
                 * Make a deposit
                 */
                println("Make a deposit")
                accountService.makeDeposit(ctx.pathParam("email"),ctx.pathParam("amount").toDouble()).let{ctx.json{it}}
                ctx.status(200)
            }
            if(pathParamMapSize == 3 && ctx.method() == "POST"){
                /**
                 * Transfer fund between account
                 */
                println("Transfer fund between account")
                accountService.moneyTransaction(ctx.pathParam("email"), ctx.pathParam("otherEmail") ,ctx.pathParam("amount").toDouble()).let{ctx.json{it}}
                ctx.status(200)
            }
            //ctx.status(404)
        }

    }
}