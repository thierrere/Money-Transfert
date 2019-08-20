package transfer.com.router

import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder
import org.slf4j.LoggerFactory
import transfer.com.handler.AccountHandler
import transfer.com.handler.AccountHandlerImpl

class Router {
    //Logger for the class
    private val LOGGER = LoggerFactory.getLogger(Router.javaClass)
    private val accountHandler: AccountHandler = AccountHandlerImpl

    companion object {
        private lateinit var app: Javalin
    }

    fun getApp(): Javalin? {
        if (app != null) {
            return app
        }
        return null
    }
    /**
     * Method uses to create endpoints
     */
    fun createService(port: Int){
        app = Javalin.create { config ->
            config.requestLogger { ctx, ms ->
                //log things here
                //println("$ctx ${ctx.method()} ${ctx.contentType()}  ${ctx.path()}  ${ctx.status()} ($ms ms)")
                LOGGER.info("$ctx ${ctx.method()} ${ctx.contentType()}  ${ctx.path()}  ${ctx.status()} ($ms ms)")
            }
        }.apply {
            exception(Exception::class.java) { e, _ -> e.printStackTrace() }
            error(404) { ctx -> ctx.json("not found") }
        }.start(port)

        app.routes {
            ApiBuilder.path("accounts") {
                ApiBuilder.get(accountHandler.getAll())
                ApiBuilder.path(":email") {
                    ApiBuilder.get(accountHandler.consult())
                    ApiBuilder.delete(accountHandler.delete())
                    ApiBuilder.path(":amount"){
                        ApiBuilder.put(accountHandler.create())
                        ApiBuilder.post(accountHandler.deposit())
                        ApiBuilder.path(":otherEmail"){
                            ApiBuilder.post(accountHandler.transfer())
                        }
                    }
                }
            }
        }
    }
}
