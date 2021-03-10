package com.mrz.apikotlin.api

data class RegisterGson(
    val email: String,
    val firstName: String,
    val lastName: String,
    val password: String
)