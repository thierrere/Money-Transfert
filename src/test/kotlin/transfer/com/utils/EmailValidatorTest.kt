package transfer.com.utils

import org.assertj.core.api.Assertions
import org.junit.Test

class EmailValidatorTest {
    @Test
    fun isEmailValid(){
        Assertions.assertThat(EmailValidator.isEmailValid("@test01mail.com")).isFalse()
        Assertions.assertThat(EmailValidator.isEmailValid("test@mail.com")).isTrue()
        Assertions.assertThat(EmailValidator.isEmailValid("test@mailcom")).isFalse()
    }
}