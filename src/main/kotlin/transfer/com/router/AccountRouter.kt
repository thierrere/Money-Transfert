package transfer.com.router

import io.javalin.http.Handler

interface AccountRouter {
    /**
     *
     */
    fun routing() : Handler
    fun transfer() : Handler
    fun deposit () : Handler
    fun consult () : Handler
    fun delete () : Handler
    fun getAll () : Handler
    fun create () : Handler
}