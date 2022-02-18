package com.android.yeophonebook.utils

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized


@RunWith(Parameterized::class)
class ValidatePhoneKtTest(
    private val number: String,
    private val expectedResult: Boolean
) {

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{index}: {0} -> \"{1}\"")
        fun data(): List<Array<Any>> = listOf(
            arrayOf("3666''''3333", false),
            arrayOf("3663333", false),
            arrayOf("+366655629*", false),
            arrayOf("3666(9000,)3333", false),
            arrayOf("+(366)333-6788-98876", false),
            arrayOf("+1(445)566-123", true),
            arrayOf("3666883333", true),
            arrayOf("+32336668765", true),
            arrayOf("+123.87657.889", true),
            arrayOf("(234)-7654-987", true),
        )
    }
    @Test
    fun `when given a string, verify it validates correctly`() {
        Assert.assertEquals(expectedResult, number.isValid())
    }
}