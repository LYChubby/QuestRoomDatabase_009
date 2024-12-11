package com.example.mvvmdatabase.dependeciesinjection

import android.content.Context
import com.example.mvvmdatabase.data.database.KrsDatabase
import com.example.mvvmdatabase.repository.LocalRepositoryMhs
import com.example.mvvmdatabase.repository.RepositoryMhs

interface InterfaceContainerApp {
    val repositoryMhs: RepositoryMhs
}

class ContainerApp (private val context: Context): InterfaceContainerApp {
    override val repositoryMhs: RepositoryMhs by lazy {
        LocalRepositoryMhs(KrsDatabase.getDatabase(context).mahasiswaDao())
    }
}