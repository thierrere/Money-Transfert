package transfer.com.utils

class EmailValidator {
    companion object {
        //@JvmStatic val EMAIL_REGEX = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
        @JvmStatic val EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$"
        fun isEmailValid(email: String): Boolean {
            return EMAIL_REGEX.toRegex().matches(email)
        }
    }
}