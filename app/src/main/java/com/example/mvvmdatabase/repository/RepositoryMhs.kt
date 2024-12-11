package com.example.mvvmdatabase.repository

import com.example.mvvmdatabase.data.entity.Mahasiswa

interface RepositoryMhs {
    suspend fun insertMhs(mahasiswa: Mahasiswa)
}