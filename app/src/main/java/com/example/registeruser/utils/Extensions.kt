package com.example.registeruser.utils

import android.view.View
import android.widget.EditText
import java.util.regex.Pattern

/**
 * @author jakhongirjalilov
 * @data 4/30/21
 * @project RegisterUser
 */
fun EditText.isValidForEmail(): Boolean {
    val s = text.toString()
    val matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(s)
    return s.isNotEmpty() && matcher.find()
}

private val VALID_EMAIL_ADDRESS_REGEX =
    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE)

fun View.gone(): View {
    visibility = View.GONE
    return this
}

fun View.visible(): View {
    visibility = View.VISIBLE
    return this
}