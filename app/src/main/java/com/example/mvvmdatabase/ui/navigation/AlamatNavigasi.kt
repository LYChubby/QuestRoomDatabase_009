package com.example.mvvmdatabase.ui.navigation

interface AlamatNavigasi {
    val route : String
}

object DestinasiHome : AlamatNavigasi {
    override val route = "home"
}

object DestinasiDetail : AlamatNavigasi {
    override val route = "detail"
    const val NIM = "nim"
    val routeWithArgs = "$route/{$NIM}"
}

object DestinasiUpdate : AlamatNavigasi {
    override val route = "update"
    const val NIM = "nim"
    val routeWithArgs = "$route/{$NIM}"
}

object DestinasiEdit : AlamatNavigasi {
    override val route = "edit"
    const val NIM = "nim"
    val routeWithArgs = "$route/{$NIM}"
}