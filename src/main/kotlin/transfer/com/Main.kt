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
     * Small Uses when the service is started
     */
    try {

        val client = Client.create()
        var webResource = client
            .resource("http://localhost:$p/accounts")
        printGetResponse(webResource)
        webResource = client
            .resource("http://localhost:$p/accounts/account01@mail.com")
        printGetResponse(webResource)
        webResource = client
            .resource("http://localhost:$p/accounts/account01@mail.com/500.0/account02@mail.com")
        printPostResponse(webResource)
        webResource = client
            .resource("http://localhost:$p/accounts/account01@mail.com")
        printGetResponse(webResource)
        webResource = client
            .resource("http://localhost:$p/accounts/account01@mail.com/500.0/account02@mail.com")
        printPostResponse(webResource)
        webResource = client
            .resource("http://localhost:$p/accounts/account01@mail.com/500.0/account02@mail.com")
        printPostResponse(webResource)
        webResource = client
            .resource("http://localhost:$p/accounts/account020@mail.com/500.0/account02@mail.com")
        printPostResponse(webResource)
        webResource = client
            .resource("http://localhost:$p/accounts/account04@mail.com/500.0/account010@mail.com")
        printPostResponse(webResource)
        webResource = client
            .resource("http://localhost:$p/accounts/account03@mail.com/-500.0/account01@mail.com")
        printPostResponse(webResource)
        webResource = client
            .resource("http://localhost:$p/accounts/account03@mail.com")
        printGetResponse(webResource)
        webResource = client
            .resource("http://localhost:$p/accounts/account03@mail.com/50000.0/account01@mail.com")
        printPostResponse(webResource)
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

