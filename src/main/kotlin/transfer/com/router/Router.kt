package transfer.com.router

import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder
import org.slf4j.LoggerFactory

class Router {
    //Logger for the class
    private val LOGGER = LoggerFactory.getLogger(Router.javaClass)
    private val accountRouter: AccountRouter = AccountRouterImpl

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
                ApiBuilder.get(accountRouter.routing())
                ApiBuilder.path(":email") {
                    ApiBuilder.get(accountRouter.routing())
                    ApiBuilder.delete(accountRouter.routing())
                    ApiBuilder.path(":amount"){
                        ApiBuilder.put(accountRouter.routing())
                        ApiBuilder.post(accountRouter.routing())
                        ApiBuilder.path(":otherEmail"){
                            ApiBuilder.post(accountRouter.routing())
                        }
                    }
                }
            }
        }
    }
}
