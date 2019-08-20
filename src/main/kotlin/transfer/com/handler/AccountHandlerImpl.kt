package transfer.com.handler

import io.javalin.http.Handler
import transfer.com.service.AccountService
import transfer.com.service.AccountServiceImpl

object AccountHandlerImpl : AccountHandler {

    private val accountService : AccountService = AccountServiceImpl()
    /**
     * Create new Account
     */
    override fun create(): Handler {
        return Handler{
                ctx ->
            var amount = 0.0
            try {
                amount = ctx.pathParam("amount").toDouble()
            }catch (e : Exception){
                ctx.status(200)
                ctx.result("Please enter a correct amount to create an Account")
            }
            //val amount = ctx.pathParam("amount")
            accountService.createAccount(ctx.pathParam("email"),ctx.pathParam("amount").toDouble()).let{ ctx.json(it)}
            ctx.status(200)
        }
    }

    /**
     * Transfer fund between account
     */
    override fun transfer(): Handler {
        return Handler{
                ctx ->
            var amount = 0.0
            try {
                amount = ctx.pathParam("amount").toDouble()
            }catch (e : Exception){
                ctx.status(200)
                ctx.result("Please enter a correct amount for the transfer")
            }
            println("Transfer fund between account")
            accountService.moneyTransaction(ctx.pathParam("email"), ctx.pathParam("otherEmail") , amount).let {ctx.json(it)}
            ctx.status(200)
        }
    }

    /**
     * Make a deposit
     */
    override fun deposit(): Handler {
        return Handler{
                ctx ->
            var amount = 0.0
            try {
                amount = ctx.pathParam("amount").toDouble()
            }catch (e : Exception){
                ctx.status(200)
                ctx.result("Please enter a correct amount for the deposit")
            }
            accountService.makeDeposit(ctx.pathParam("email"), amount).let{ ctx.json(it) }
            ctx.status(200)
        }
    }

    /**
     * Find an account using email
     */
    override fun consult(): Handler {
        return Handler{
                ctx ->
            println("Find an account using email")
            accountService.getAccount(ctx.pathParam("email"))?.let { ctx.json(it) }
                ?: ctx.status(404)
        }
    }

    override fun delete(): Handler {
        return Handler{
                ctx ->
            ctx.status(501)
        }
    }

    /**
     * Get all accounts
     */
    override fun getAll(): Handler {
        return Handler{
            ctx ->
            println("Get all accounts")
            accountService.getAllAccounts().let { ctx.json(it) }
            ctx.status(200)
        }
    }

    /**
     * Uses to validate the POC - Not in use for the moment
     */
    override fun routing() : Handler {
        return Handler{
            ctx ->
            val pathParamMapSize = ctx.pathParamMap().size
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
                accountService.createAccount(ctx.pathParam("email"),ctx.pathParam("amount").toDouble()).let{ ctx.json(it)}
                ctx.status(200)
            }
            if(pathParamMapSize == 2 && ctx.method() == "POST"){
                /**
                 * Make a deposit
                 */
                accountService.makeDeposit(ctx.pathParam("email"),ctx.pathParam("amount").toDouble()).let{ ctx.json(it) }
                ctx.status(200)
            }
            if(pathParamMapSize == 3 && ctx.method() == "POST"){
                /**
                 * Transfer fund between account
                 */
                println("Transfer fund between account")
                accountService.moneyTransaction(ctx.pathParam("email"), ctx.pathParam("otherEmail") ,ctx.pathParam("amount").toDouble()).let {ctx.json(it)}
                ctx.status(200)
            }
            //ctx.status(404)
        }

    }
}