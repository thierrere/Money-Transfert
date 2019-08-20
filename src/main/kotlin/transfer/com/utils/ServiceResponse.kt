package transfer.com.utils

import java.io.Serializable

data class ServiceResponse(private val status : Boolean , private val message :  String) : Serializable{
    fun getStatus() : Boolean{
        return status
    }

    fun getMessage () : String {
        return message
    }
}