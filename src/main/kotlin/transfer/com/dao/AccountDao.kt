package transfer.com.dao

import transfer.com.model.Account
import java.math.BigDecimal

/**
 * Interface representing all actions possible to be done on an account
 */
interface AccountDao {

    /**
     * Method uses to find an account
     * Parameter : email (String)
     * Result : if successful an Account else null
     */
    fun findByEmail(email: String): Account?


    /**
     * Method uses to add amount on an account balance
     * Parameters : email (String), amount (Double)
     * Result : the new account balance
     */
    fun deposit(email: String, amount: BigDecimal): BigDecimal

    /**
     * Method uses to substract amount on an account balance
     * Parameters : email (String), amount (Double)
     * Result : the new account balance
     */
    fun withdraw (email : String, amount: BigDecimal): BigDecimal

    /**
     * Method uses to obtain balance from an account
     * Parameter : email (String)
     * Result : account balance
     */
    fun balance (email : String): BigDecimal

    /**
     * Method uses to create an account
     * Parameter : email (String), amount (Double)
     * Result : if successful an Account else null
     */
    fun create (email : String, amount : BigDecimal) : Account?

    /**
     * Method uses to delete an account
     * Parameter : email (String)
     * Result : if successful an Account else null
     */
    fun delete (email : String) : Account?
}