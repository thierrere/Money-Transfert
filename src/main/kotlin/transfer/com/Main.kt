package transfer.com

import transfer.com.router.Router

fun main(args: Array<String>) {
    val router = Router()
    val p = System.getenv("PORT")?.toIntOrNull() ?: 7000
    router.createService(p)
    /**
     * Taking all the account
     */
    var process = ProcessBuilder("curl", "http://localhost:$p/accounts").start()
    process.inputStream.reader(Charsets.UTF_8).use {
        println(it.readText())
    }
    /**
     * Searching for an account
     */
    process = ProcessBuilder("curl", "http://localhost:$p/accounts/account01@mail.com").start()
    process.inputStream.reader(Charsets.UTF_8).use {
        println(it.readText())
    }

    /**
     *Transfer fund between two account
     */
    process = ProcessBuilder("curl", "http://localhost:$p/accounts/account01@mail.com/2000.0/account02mail.com").start()
    process.inputStream.reader(Charsets.UTF_8).use {
        println(it.readText())
    }



}

