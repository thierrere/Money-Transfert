package transfer.com.router

import io.javalin.http.Handler

interface AccountRouter {
    /**
     *
     */
    fun routing() : Handler
}