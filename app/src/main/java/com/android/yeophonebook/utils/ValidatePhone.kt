package com.android.yeophonebook.utils


fun String.isValid() : Boolean {
    val allowedChar = listOf(
        '-',
        '+',
        '(',
        ')',
        '.'
    )
    return this.length in 9..15 && this.filter { it in '0'..'9' || it in allowedChar }.length == this.length
}