package com.example.jasangovor.utils

fun checkUserInputs(
    name: String,
    surname: String,
    email: String,
    password: String,
    passwordRepeated: String
): Boolean {
    return checkName(name) && checkSurname(surname) &&
            checkEmail(email) && checkPassword(password, passwordRepeated)
}

private fun checkName(name: String): Boolean {
    return name.isNotEmpty()
}

private fun checkSurname(surname: String): Boolean {
    return surname.isNotEmpty()
}

private fun checkEmail(email: String): Boolean {
    return email.isNotEmpty()
}

private fun checkPassword(password: String, passwordRepeated: String): Boolean {
    return password.isNotEmpty() && password == passwordRepeated
}