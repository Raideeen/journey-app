package com.example.journey.model

data class User(
    val Id : Int,
    var Username : String,
    var Password : String,
    )
{
    var newUsername: String
        get() = "$Username"
        set(value) {
            if (value.isNotBlank()) {
                Username = value
            } else {
                println("Le nom d'utilisateur ne peut pas être vide.")
            }
        }

    // Custom setter for the 'password' property
    var newPassword: String
        get() ="$Password"
        set(value) {
            if (value.length >= 6) {
                Password = value
            } else {
                println("Le mot de passe doit contenir au moins 6 caractères.")
            }
        }
}


