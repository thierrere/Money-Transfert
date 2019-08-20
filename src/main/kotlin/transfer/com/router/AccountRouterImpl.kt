package transfer.com.router

import io.javalin.http.Handler

object AccountRouterImpl : AccountRouter {
    override fun routing() : Handler {
        return Handler{
            ctx ->
            println("Context Map : ${ctx.pathParamMap()}")
            ctx.status(501)
        }

    }
}