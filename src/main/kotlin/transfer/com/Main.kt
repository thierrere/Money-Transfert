package transfer.com

import transfer.com.router.Router

fun main(args: Array<String>) {
    println("Hello, World")
    val router = Router()
    router.createService(7000)
}

