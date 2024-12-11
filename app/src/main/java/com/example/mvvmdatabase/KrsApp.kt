package com.example.mvvmdatabase

import android.app.Application
import com.example.mvvmdatabase.dependeciesinjection.ContainerApp

class KrsApp : Application() {
    lateinit var containerApp: ContainerApp // Fungsinya Untuk Menyimpan Instance

    override fun onCreate() {
        super.onCreate()
        containerApp = ContainerApp(this)
        // Instance Adalah Object Yang Dibuat Dari Class Yang Di Deklarasikan
    }
}