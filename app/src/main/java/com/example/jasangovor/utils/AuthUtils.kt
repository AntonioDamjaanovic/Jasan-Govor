package com.example.jasangovor.utils

fun checkUserInputs(
    name: String,
    surname: String,
    password: String,
    passwordRepeated: String
): Boolean {
    return checkName(name) && checkSurname(surname) && checkPassword(password, passwordRepeated)
}

private fun checkName(name: String): Boolean {
    return name.isNotEmpty()
}

private fun checkSurname(surname: String): Boolean {
    return surname.isNotEmpty()
}

private fun checkPassword(password: String, passwordRepeated: String): Boolean {
    return password == passwordRepeated
}