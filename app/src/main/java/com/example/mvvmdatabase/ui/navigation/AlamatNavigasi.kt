package com.example.mvvmdatabase.ui.navigation

interface AlamatNavigasi {
    val route : String
}

object DestinasiHome : AlamatNavigasi {
    override val route = "home"
}