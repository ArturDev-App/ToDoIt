package com.example.todoit.presentacion.navegation

sealed class ScreenNavegation(val name : String){
    object  LoginScreen : ScreenNavegation(name = "Login")
    object Home : ScreenNavegation("home")

}