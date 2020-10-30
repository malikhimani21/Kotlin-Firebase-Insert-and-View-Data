package com.example.firebasecrudkotlin

class Model(
    val id: String?,
    val name: String,
    val email: String,
    val contact: String,
    val rating: Int
) {
    constructor() : this("", "", "", "", 0)
}
