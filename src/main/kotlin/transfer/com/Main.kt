package transfer.com

import com.sun.jersey.api.client.Client
import com.sun.jersey.api.client.ClientResponse
import com.sun.jersey.api.client.WebResource
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

    try {

        val client = Client.create()
        /**
         * Taking all the account
         */
        var webResource = client
            .resource("http://localhost:$p/accounts")
        printGetResponse(webResource)
        /**
         * Searching for an account : account01@mail.com
         */
        webResource = client
            .resource("http://localhost:$p/accounts/account01@mail.com")
        printGetResponse(webResource)
        /**
         *Transfer fund between two account : from account01@mail.com to account02@mail.com
         */
        webResource = client
            .resource("http://localhost:$p/accounts/account01@mail.com/500.0/account02@mail.com")
        printPostResponse(webResource)
        /**
         * Searching for an account : account01@mail.com
         */
        webResource = client
            .resource("http://localhost:$p/accounts/account01@mail.com")
        printGetResponse(webResource)
        /**
         *Transfer fund between two account : from account01@mail.com to account02@mail.com
         */
        webResource = client
            .resource("http://localhost:$p/accounts/account01@mail.com/500.0/account02@mail.com")
        printPostResponse(webResource)
        /**
         *Transfer fund between two account : from account01@mail.com to account02@mail.com
         */
        webResource = client
            .resource("http://localhost:$p/accounts/account01@mail.com/500.0/account02@mail.com")
        printPostResponse(webResource)
        /**
         *Transfer fund between two account : from account020@mail.com to account02@mail.com
         */
        webResource = client
            .resource("http://localhost:$p/accounts/account020@mail.com/500.0/account02@mail.com")
        printPostResponse(webResource)
        /**
         *Transfer fund between two account : from account04@mail.com to account010@mail.com
         */
        webResource = client
            .resource("http://localhost:$p/accounts/account04@mail.com/500.0/account010@mail.com")
        printPostResponse(webResource)
        /**
         *Transfer fund between two account : from account03@mail.com to account01@mail.com
         */
        webResource = client
            .resource("http://localhost:$p/accounts/account03@mail.com/-500.0/account01@mail.com")
        printPostResponse(webResource)
        /**
         * Searching for an account : account03@mail.com
         */
        webResource = client
            .resource("http://localhost:$p/accounts/account03@mail.com")
        printGetResponse(webResource)
        /**
         *Transfer fund between two account : from account03@mail.com to account01@mail.com
         */
        webResource = client
            .resource("http://localhost:$p/accounts/account03@mail.com/50000.0/account01@mail.com")
        printPostResponse(webResource)
        /**
         * Taking all the account
         */
        webResource = client
            .resource("http://localhost:$p/accounts")
        printGetResponse(webResource)

    } catch (e: Exception) {

        e.printStackTrace()

    }

}

fun printGetResponse(webResource : WebResource){
    val response = webResource.accept("application/json")
        .get(ClientResponse::class.java)
    val output = response.getEntity(String::class.java)
    println(output)
}

fun printPostResponse(webResource : WebResource){
    val response = webResource.accept("application/json")
        .post(ClientResponse::class.java)
    val output = response.getEntity(String::class.java)
    println(output)
}

fun printPutResponse(webResource : WebResource){
    val response = webResource.accept("application/json")
        .put(ClientResponse::class.java)
    val output = response.getEntity(String::class.java)
    println(output)
}

